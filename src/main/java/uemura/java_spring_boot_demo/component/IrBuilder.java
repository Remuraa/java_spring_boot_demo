package uemura.java_spring_boot_demo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uemura.java_spring_boot_demo.domais.pojo.StockPortfolioAnalyticalVo;
import uemura.java_spring_boot_demo.domais.transfer.*;
import uemura.java_spring_boot_demo.enums.IrMovementTypeEnum;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;
import uemura.java_spring_boot_demo.enums.ProductEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class IrBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(IrBuilder.class);

    public IrResponseDto getResponse(IrRequestDto irDto, List<IrExceltDto> irExceltDtos) {

        Map<Month, List<StockPortfolioAnalyticalVo>> maps = calculatePortfolioAnalytical(irExceltDtos, irDto);
        List<ProfitCalculationDto> profitCalculation = getProfitCalculation(maps);

        return IrResponseDto.builder()
                .propertys(getProperty(maps))
                .profitCalculation(profitCalculation)
                .lostCalculation(getLostCalculation(profitCalculation))
                .annualIncome(getAnnualIncome(profitCalculation))
                .earningsAndDividendsReceived(getEarningsAndDividendsReceived(irExceltDtos))
                .interestOnEquity(getInterestOnEquity(irExceltDtos))
                .build();
    }


    private List<InterestOnEquityDto> getInterestOnEquity(List<IrExceltDto> irExceltDtos) {
        return irExceltDtos
                .stream()
                .filter(ir -> IrMovimentEnum.INTEREST_ON_EQUITY.getValue().equals(ir.getMoviment()))
                .map(ir -> InterestOnEquityDto
                        .builder()
                        .product(ProductEnum.convertNameProduct(ir.getProduct()))
                        .value(new BigDecimal(ir.getTotalPrice().replace("R$", "").trim()))
                        .build()
                ).collect(Collectors.groupingBy(InterestOnEquityDto::getProduct,
                        Collectors.reducing(BigDecimal.ZERO, InterestOnEquityDto::getValue, BigDecimal::add)))
                .entrySet()
                .stream()
                .map(entry -> InterestOnEquityDto
                        .builder()
                        .product(entry.getKey())
                        .value(entry.getValue())
                        .build())
                .sorted(Comparator.comparing(InterestOnEquityDto::getProduct))
                .collect(Collectors.toList());
    }

    private List<EarningsAndDividendsDto> getEarningsAndDividendsReceived(List<IrExceltDto> irExceltDtos) {
        return irExceltDtos
                .stream()
                .filter(ir -> IrMovimentEnum.DIVIDEND.getValue().equals(ir.getMoviment()))
                .map(ir -> EarningsAndDividendsDto
                        .builder()
                        .product(ProductEnum.convertNameProduct(ir.getProduct()))
                        .value(new BigDecimal(ir.getTotalPrice().replace("R$", "").trim()))
                        .build()
                ).collect(Collectors.groupingBy(EarningsAndDividendsDto::getProduct,
                        Collectors.reducing(BigDecimal.ZERO, EarningsAndDividendsDto::getValue, BigDecimal::add)))
                .entrySet()
                .stream()
                .map(entry -> EarningsAndDividendsDto
                        .builder()
                        .product(entry.getKey())
                        .value(entry.getValue())
                        .build())
                .sorted(Comparator.comparing(EarningsAndDividendsDto::getProduct))
                .collect(Collectors.toList());

    }

    private BigDecimal getAnnualIncome(List<ProfitCalculationDto> profitCalculation) {
        return profitCalculation
                .stream()
                .map(ProfitCalculationDto::getValue)
                .filter(value -> BigDecimal.ZERO.compareTo(value) < 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<ProfitCalculationDto> getLostCalculation(List<ProfitCalculationDto> profitCalculation) {
        return null; //todo
    }

    private List<ProfitCalculationDto> getProfitCalculation(Map<Month, List<StockPortfolioAnalyticalVo>> maps) {
        return maps
                .entrySet()
                .stream()
                .map(entry -> entry.getValue()
                        .stream()
                        .filter(stockPortfolio -> IrMovementTypeEnum.DEBIT.equals(stockPortfolio.getMovementType()))
                        .map(stockPortfolio -> ProfitCalculationDto.builder()
                                .product(stockPortfolio.getProduct())
                                .month(entry.getKey())
                                .value(stockPortfolio.getTotalPrice().subtract(stockPortfolio.getQuantity().multiply(stockPortfolio.getStockPortfolioAveragePrice())))
                                .build())
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(ProfitCalculationDto::getMonth))
                .collect(Collectors.toList());
    }

    private List<PropertyDto> getProperty(Map<Month, List<StockPortfolioAnalyticalVo>> maps) {
        List<PropertyDto> collect = maps
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
                .sorted(Comparator.comparing(PropertyDto::getProduct))
                .collect(Collectors.toList());
        collect.stream()
                .forEach(stock -> stock.setTotalPrice(stock.getQuantity().multiply(stock.getAveragePrice())));
        return collect;
    }

    private PropertyDto propertyConverter(StockPortfolioAnalyticalVo stocks) {
        return PropertyDto.builder()
                .product(stocks.getProduct())
                .quantity(stocks.getStockPortfolioQuantity())
                .averagePrice(stocks.getStockPortfolioAveragePrice())
                .build();
    }

    private Map<Month, List<StockPortfolioAnalyticalVo>> calculatePortfolioAnalytical(List<IrExceltDto> irExceltDtos, IrRequestDto irDto) {

        return Stream.concat(
                        irExceltDtos
                                .stream()
                                .filter(ir -> IrMovimentEnum.LIQUIDATION.getValue().equals(ir.getMoviment()))
                                .map(ir -> StockPortfolioAnalyticalVo
                                        .builder()
                                        .product(ProductEnum.convertNameProduct(ir.getProduct()))
                                        .date(ir.getDate())
                                        .movementType(IrMovementTypeEnum.toEnum(ir.getTypeMoviment()))
                                        .quantity(new BigDecimal(ir.getQuantity()))
                                        .totalPrice(new BigDecimal(ir.getTotalPrice().replace("R$", "").replace(",", "").trim()))
                                        .build()
                                ),
                        irDto.getPropertysLastYear()
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
