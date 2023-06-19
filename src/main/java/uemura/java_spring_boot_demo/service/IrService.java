package uemura.java_spring_boot_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.IrBuilder;
import uemura.java_spring_boot_demo.component.ReadExcel;
import uemura.java_spring_boot_demo.converter.MovimentConverter;
import uemura.java_spring_boot_demo.domais.entity.MovimentEntity;
import uemura.java_spring_boot_demo.domais.pojo.StockPortfolioAnalyticalVo;
import uemura.java_spring_boot_demo.domais.transfer.*;
import uemura.java_spring_boot_demo.enums.IrMovementTypeEnum;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;
import uemura.java_spring_boot_demo.enums.ProductEnum;
import uemura.java_spring_boot_demo.repository.MovimentRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class IrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IrService.class);

    @Value("${config.url-files}")
    private String urlFiles;


    @Autowired
    private IrBuilder irBuilder;
    @Autowired
    private MovimentRepository movimentRepository;

    public IrResponseDto calculateIr(IrRequestDto irDto) {
        try {
            List<IrExceltDto> irExceltDtos = ReadExcel.read(urlFiles + irDto.getNameFile());

            return irBuilder.getResponse(irDto, irExceltDtos);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public void importIr(String pathFile) {
        List<MovimentEntity> movimentEntities = ReadExcel.read(urlFiles + pathFile)
                .stream()
                .map(MovimentConverter::converter)
                .filter(movimentEntity -> Objects.nonNull(movimentEntity.getMoviment()))
                .collect(Collectors.toList());
        movimentRepository.saveAll(movimentEntities);
    }

    public IrPropertiesResponseDto getProperties(PropetiesRequestDto requestDto) {
        LocalDate firstDayOfYear = LocalDate.of(requestDto.getYear(), Month.JANUARY, 1);
        LocalDate lasDayOfYear = firstDayOfYear.with(TemporalAdjusters.lastDayOfYear());
        List<MovimentEntity> moviments = movimentRepository.findAllByMovimentDateBetweenAndMoviment(firstDayOfYear, lasDayOfYear, IrMovimentEnum.LIQUIDATION);

        Map<Month, List<StockPortfolioAnalyticalVo>> maps = calculatePortfolioAnalytical(moviments, requestDto);

        return IrPropertiesResponseDto.builder()
                .properties(irBuilder.getProperty(maps))
                .build();
    }

    private Map<Month, List<StockPortfolioAnalyticalVo>> calculatePortfolioAnalytical(List<MovimentEntity> irExceltDtos, PropetiesRequestDto irDto) {

        return Stream.concat(
                        irExceltDtos
                                .stream()
                                .map(ir -> StockPortfolioAnalyticalVo
                                        .builder()
                                        .product(ir.getProduct())
                                        .date(ir.getMovimentDate())
                                        .movementType(ir.getTypeMoviment())
                                        .quantity(ir.getQuantity())
                                        .totalPrice(ir.getTotalPrice())
                                        .build()
                                ),
                        irDto.getPropertiesLastYear()
                                .stream()
                                .map(property -> StockPortfolioAnalyticalVo
                                        .builder()
                                        .product(ProductEnum.convertNameProduct(property.getProduct()))
                                        .date(LocalDate.of(irDto.getYear() - 1, Month.DECEMBER, 31))
                                        .quantity(property.getQuantity())
                                        .totalPrice(property.getAveragePrice().multiply(property.getQuantity()))
                                        .stockPortfolioQuantity(property.getQuantity())
                                        .stockPortfolioAveragePrice(property.getAveragePrice())
                                        .build()))
                .sorted(Comparator.comparing(StockPortfolioAnalyticalVo::getDate))
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(StockPortfolioAnalyticalVo::getProduct),
                        mapProductStock -> mapProductStock
                                .entrySet()
                                .stream()
                                .flatMap(entryStock -> {
                                    LOGGER.info(entryStock.getKey());
                                    return IntStream.range(0, entryStock.getValue().size())
                                            .mapToObj(index -> {
                                                StockPortfolioAnalyticalVo lastStock = index == 0 ? StockPortfolioAnalyticalVo.builder().build() : entryStock.getValue().get(index - 1);
                                                StockPortfolioAnalyticalVo currentStock = entryStock.getValue().get(index);

                                                currentStock.setStockPortfolioQuantity(
                                                        IrMovementTypeEnum.DEBIT.equals(currentStock.getMovementType()) ?
                                                                lastStock.getStockPortfolioQuantity().subtract(currentStock.getQuantity()) :
                                                                lastStock.getStockPortfolioQuantity().add(currentStock.getQuantity()));
                                                currentStock.setStockPortfolioAveragePrice(
                                                        currentStock.getStockPortfolioQuantity().compareTo(BigDecimal.ZERO) == 0 ?
                                                                BigDecimal.ZERO :
                                                                IrMovementTypeEnum.DEBIT.equals(currentStock.getMovementType()) ?
                                                                        lastStock.getStockPortfolioAveragePrice() :
                                                                        lastStock.getStockPortfolioAveragePrice().multiply(lastStock.getStockPortfolioQuantity()).add(currentStock.getTotalPrice())
                                                                                .divide(currentStock.getStockPortfolioQuantity(), 2, RoundingMode.UP));
                                                return currentStock;
                                            });
                                })
                                .collect(Collectors.groupingBy(d -> d.getDate().getMonth()))));
    }
}
