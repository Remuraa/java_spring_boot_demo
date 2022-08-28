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
            List<Map<String, List<StockPortfolioAnalyticalVo>>> maps = calculatePortfolioAnalytical(irExceltDtos, irDto.getPropertysLastYear());
            return new IrResponseDto();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private List<Map<String, List<StockPortfolioAnalyticalVo>>> calculatePortfolioAnalytical(List<IrExceltDto> irExceltDtos, List<PropertyDto> propertysLastYear) {
        Map<Month, List<IrExceltDto>> mapIrByMonth = irExceltDtos
                .stream()
                .filter(ir -> IrMovimentEnum.LIQUIDATION.getValue().equals(ir.getMoviment()))
                .collect(Collectors.groupingBy(d -> d.getDate().getMonth()));

        Map<Month, List<IrExceltDto>> treeMapIrByMonth = new TreeMap<>(mapIrByMonth);

        return treeMapIrByMonth
                .entrySet()
                .stream()
                .map(entry -> {
                    LOGGER.info("Month: {}", entry.getKey());
                    List<StockPortfolioAnalyticalVo> stockPortifolioLastyear = new ArrayList<>();
                    if (Month.JANUARY.equals(entry.getKey())) {
                        stockPortifolioLastyear = propertysLastYear
                                .stream()
                                .map(property -> new StockPortfolioAnalyticalVo()
                                        .setProduct(property.getProduct())
                                        .setDate(LocalDate.of(2020, Month.DECEMBER, 31))
                                        .setQuantity(property.getQuantity())
                                        .setTotalPrice(property.getAveragePrice().multiply(property.getQuantity()))
                                        .setStockPortfolioQuantity(property.getQuantity())
                                        .setStockPortfolioAveragePrice(property.getAveragePrice()))
                                .collect(Collectors.toList());
                    }

                    Map<String, List<StockPortfolioAnalyticalVo>> maStockPortfolio = Stream.concat(
                                    entry.getValue()
                                            .stream()
                                            .map(ir -> new StockPortfolioAnalyticalVo()
                                                    .setProduct(ir.getProduct().trim())
                                                    .setDate(ir.getDate())
                                                    .setTypeMoviment(IrTypeMovimentEnum.toEnum(ir.getTypeMoviment()))
                                                    .setQuantity(new BigDecimal(ir.getQuantity()))
                                                    .setTotalPrice(new BigDecimal(ir.getTotalPrice()))
                                            ),
                                    stockPortifolioLastyear.stream())
                            .collect(Collectors.groupingBy(StockPortfolioAnalyticalVo::getProduct));

                    maStockPortfolio
                            .entrySet()
                            .stream()
                            .forEach(entryStock -> {
                                LOGGER.info(entryStock.getKey());
                                StockPortfolioAnalyticalVo lastStockPortfolio = new StockPortfolioAnalyticalVo();
                                entryStock.getValue()
                                        .stream()
                                        .sorted(Comparator.comparing(StockPortfolioAnalyticalVo::getDate))
                                        .forEach(stockPortfolio -> {
                                            stockPortfolio
                                                    .setStockPortfolioQuantity(
                                                            IrTypeMovimentEnum.DEBIT.equals(stockPortfolio.getTypeMoviment()) ?
                                                                    lastStockPortfolio.getStockPortfolioQuantity().subtract(stockPortfolio.getQuantity()) :
                                                                    lastStockPortfolio.getStockPortfolioQuantity().add(stockPortfolio.getQuantity()))
                                                    .setStockPortfolioAveragePrice(
                                                            stockPortfolio.getStockPortfolioQuantity().compareTo(BigDecimal.ZERO) == 0 ?
                                                                    BigDecimal.ZERO :
                                                                    IrTypeMovimentEnum.DEBIT.equals(stockPortfolio.getTypeMoviment()) ?
                                                                            lastStockPortfolio.getStockPortfolioAveragePrice() :
                                                                            lastStockPortfolio.getStockPortfolioAveragePrice().multiply(lastStockPortfolio.getStockPortfolioQuantity()).add(stockPortfolio.getTotalPrice())
                                                                                    .divide(stockPortfolio.getStockPortfolioQuantity(), 2, RoundingMode.UP));
                                            lastStockPortfolio.setStockPortfolioAveragePrice(stockPortfolio.getStockPortfolioAveragePrice());
                                            lastStockPortfolio.setStockPortfolioQuantity(stockPortfolio.getStockPortfolioQuantity());
                                            LOGGER.info(stockPortfolio.toString());
                                        });
                            });
                    return maStockPortfolio;
                }).collect(Collectors.toList());
    }

}
