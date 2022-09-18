package uemura.java_spring_boot_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.ReadExcel;
import uemura.java_spring_boot_demo.domais.pojo.StockPortfolioAnalyticalVo;
import uemura.java_spring_boot_demo.domais.transfer.IrExceltDto;
import uemura.java_spring_boot_demo.domais.transfer.IrRequestDto;
import uemura.java_spring_boot_demo.domais.transfer.IrResponseDto;
import uemura.java_spring_boot_demo.domais.transfer.PropertyDto;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;
import uemura.java_spring_boot_demo.enums.IrTypeMovimentEnum;
import uemura.java_spring_boot_demo.enums.ProductEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IrService.class);

    public IrResponseDto calculateIr(IrRequestDto irDto) {
        try {
            List<IrExceltDto> irExceltDtos = ReadExcel.read(irDto.getUrlPath());
            Map<Month, List<StockPortfolioAnalyticalVo>> maps = calculatePortfolioAnalytical(irExceltDtos, irDto.getPropertysLastYear());

            return new IrResponseDto()
                    .setPropertys(maps
                            .values()
                            .stream()
                            .flatMap(Collection::stream)
                            .collect(Collectors.groupingBy(StockPortfolioAnalyticalVo::getProduct,
                                    LinkedHashMap::new,
                                    Collectors.maxBy(Comparator.comparing(StockPortfolioAnalyticalVo::getDate))))
                            .values().stream()
                            .map(Optional::get)
                            .filter(stock -> stock.getStockPortfolioQuantity().compareTo(BigDecimal.ZERO) > 0)
                            .map(this::propertyConverter)
                            .collect(Collectors.toList()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private PropertyDto propertyConverter(StockPortfolioAnalyticalVo stocks) {
        return new PropertyDto()
                .setProduct(stocks.getProduct())
                .setQuantity(stocks.getStockPortfolioQuantity())
                .setAveragePrice(stocks.getStockPortfolioAveragePrice());
    }

    private Map<Month, List<StockPortfolioAnalyticalVo>> calculatePortfolioAnalytical(List<IrExceltDto> irExceltDtos, List<PropertyDto> propertysLastYear) {

        Map<String, List<StockPortfolioAnalyticalVo>> mapProductStock = Stream.concat(
                        irExceltDtos
                                .stream()
                                .filter(ir -> IrMovimentEnum.LIQUIDATION.getValue().equals(ir.getMoviment()))
                                .map(ir -> new StockPortfolioAnalyticalVo()
                                        .setProduct(ProductEnum.convertNameProduct(ir.getProduct()))
                                        .setDate(ir.getDate())
                                        .setTypeMoviment(IrTypeMovimentEnum.toEnum(ir.getTypeMoviment()))
                                        .setQuantity(new BigDecimal(ir.getQuantity()))
                                        .setTotalPrice(new BigDecimal(ir.getTotalPrice()))
                                ),
                        propertysLastYear
                                .stream()
                                .map(property -> new StockPortfolioAnalyticalVo()
                                        .setProduct(ProductEnum.convertNameProduct(property.getProduct()))
                                        .setDate(LocalDate.of(2020, Month.DECEMBER, 31))
                                        .setQuantity(property.getQuantity())
                                        .setTotalPrice(property.getAveragePrice().multiply(property.getQuantity()))
                                        .setStockPortfolioQuantity(property.getQuantity())
                                        .setStockPortfolioAveragePrice(property.getAveragePrice())))

                .sorted(Comparator.comparing(StockPortfolioAnalyticalVo::getDate))
                .collect(Collectors.groupingBy(StockPortfolioAnalyticalVo::getProduct));

        mapProductStock
                .entrySet()
                .stream()
                .forEach(entryStock -> {
                    LOGGER.info(entryStock.getKey());
                    StockPortfolioAnalyticalVo lastStockPortfolio = new StockPortfolioAnalyticalVo();
                    entryStock.getValue()
                            .forEach(stock -> {
                                stock
                                        .setStockPortfolioQuantity(
                                                IrTypeMovimentEnum.DEBIT.equals(stock.getTypeMoviment()) ?
                                                        lastStockPortfolio.getStockPortfolioQuantity().subtract(stock.getQuantity()) :
                                                        lastStockPortfolio.getStockPortfolioQuantity().add(stock.getQuantity()))
                                        .setStockPortfolioAveragePrice(
                                                stock.getStockPortfolioQuantity().compareTo(BigDecimal.ZERO) == 0 ?
                                                        BigDecimal.ZERO :
                                                        IrTypeMovimentEnum.DEBIT.equals(stock.getTypeMoviment()) ?
                                                                lastStockPortfolio.getStockPortfolioAveragePrice() :
                                                                lastStockPortfolio.getStockPortfolioAveragePrice().multiply(lastStockPortfolio.getStockPortfolioQuantity()).add(stock.getTotalPrice())
                                                                        .divide(stock.getStockPortfolioQuantity(), 2, RoundingMode.UP));
                                lastStockPortfolio.setStockPortfolioAveragePrice(stock.getStockPortfolioAveragePrice());
                                lastStockPortfolio.setStockPortfolioQuantity(stock.getStockPortfolioQuantity());
                                LOGGER.info(stock.toString());
                            });
                });

        return mapProductStock
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(d -> d.getDate().getMonth()));
    }
}
