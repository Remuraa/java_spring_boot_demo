package uemura.java_spring_boot_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.ReadExcel;
import uemura.java_spring_boot_demo.domais.pojo.StockPortfolioAnalyticalVo;
import uemura.java_spring_boot_demo.domais.transfer.IrExceltDto;
import uemura.java_spring_boot_demo.domais.transfer.IrRequestDto;
import uemura.java_spring_boot_demo.domais.transfer.IrResponseDto;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;
import uemura.java_spring_boot_demo.enums.IrTypeMovimentEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IrService.class);

    public IrResponseDto calculateIr(IrRequestDto irDto) {
        try {
            List<IrExceltDto> irExceltDtos = ReadExcel.read(irDto.getUrlPath());
            calculatePortfolioAnalytical(irExceltDtos);
            return new IrResponseDto();
//                    .setPropertys(calculate(irExceltDtos));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private void calculatePortfolioAnalytical(List<IrExceltDto> irExceltDtos) {
        Map<Month, List<IrExceltDto>> mapIrByMonth = irExceltDtos
                .stream()
                .filter(ir -> IrMovimentEnum.LIQUIDATION.getValue().equals(ir.getMoviment()))
                .collect(Collectors.groupingBy(d -> d.getDate().getMonth()));

        mapIrByMonth
                .entrySet()
                .stream()
                .map(entry -> {
                    Month month = entry.getKey();

                    Map<String, List<StockPortfolioAnalyticalVo>> maStockPortfolio = entry.getValue()
                            .stream()
                            .map(ir -> new StockPortfolioAnalyticalVo()
                                    .setProduct(ir.getProduct().trim())
                                    .setDate(ir.getDate())
                                    .setTypeMoviment(IrTypeMovimentEnum.toEnum(ir.getTypeMoviment()))
                                    .setQuantity(new BigDecimal(ir.getQuantity()))
                                    .setTotalPrice(new BigDecimal(ir.getTotalPrice()))
                            ).collect(Collectors.groupingBy(StockPortfolioAnalyticalVo::getProduct));


                    maStockPortfolio
                            .entrySet()
                            .stream()
                            .forEach(entryStock ->{
                                    StockPortfolioAnalyticalVo lastStockPortfolio = new StockPortfolioAnalyticalVo();

                                    entryStock.getValue()
                                            .stream()
                                            .sorted(Comparator.comparing(StockPortfolioAnalyticalVo::getDate))
                                            .forEach(stockPortfolio -> {
                                                stockPortfolio
                                                        .setStockPortfolioQuantity(IrTypeMovimentEnum.DEBIT.equals(stockPortfolio.getTypeMoviment()) ?
                                                                lastStockPortfolio.getStockPortfolioQuantity().subtract(stockPortfolio.getQuantity()) :
                                                                lastStockPortfolio.getStockPortfolioQuantity().add(stockPortfolio.getQuantity()))
                                                        .setStockPortfolioAveragePrice(
                                                                IrTypeMovimentEnum.DEBIT.equals(stockPortfolio.getTypeMoviment()) ?
                                                                        lastStockPortfolio.getStockPortfolioAveragePrice() :
                                                                        lastStockPortfolio.getStockPortfolioAveragePrice().add(stockPortfolio.getAverageBuy())
                                                                                .divide(stockPortfolio.getStockPortfolioQuantity(), 2, RoundingMode.UP));
                                                lastStockPortfolio.setStockPortfolioAveragePrice(stockPortfolio.getStockPortfolioAveragePrice());
                                                lastStockPortfolio.setStockPortfolioQuantity(stockPortfolio.getStockPortfolioQuantity());
                                            });
                            });
                    return maStockPortfolio;
                }).collect(Collectors.toList());
    }


//    private List<PropertyDto> calculate(List<IrExceltDto> irExceltDtos) {
//        return calculateProperty(irExceltDtos)
//                .stream()
//                .map(property -> new PropertyDto()
//                        .setProduct(property.getProduct())
//                        .setQuantity(property.getQuantity())
//                        .setAveragePriceBuy(property.getAveragePriceBuy())
//                        .setPrice(property.getTotalPrice()))
//                .collect(Collectors.toList());
//    }

//    private List<PropertyVo> calculateProperty(List<IrExceltDto> irExceltDtos) {
//        return irExceltDtos
//                .stream()
//                .filter(ir -> IrMovimentEnum.LIQUIDATION.getValue().equals(ir.getMoviment()))
//                .map(ir -> new PropertyVo()
//                        .setProduct(ir.getProduct().trim())
//                        .setQuantity(IrTypeMovimentEnum.DEBIT.getValue().equals(ir.getTypeMoviment()) ? new BigDecimal("-" + ir.getQuantity()) : new BigDecimal(ir.getQuantity()))
//                        .setTotalPrice(IrTypeMovimentEnum.DEBIT.getValue().equals(ir.getTypeMoviment()) ? BigDecimal.ZERO : new BigDecimal(ir.getTotalPrice())))
//                .collect(Collectors.groupingBy(PropertyVo::getProduct,
//                        Collectors.reducing((propertyRight, propertyLeft) -> propertyRight
//                                .addQuantity(propertyLeft.getQuantity())
//                                .addTotalPrice(propertyLeft.getTotalPrice()))))
//                .values()
//                .stream()
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .filter(property -> property.getQuantity().compareTo(BigDecimal.ZERO) > 0)
//                .collect(Collectors.toList());
//    }

}
