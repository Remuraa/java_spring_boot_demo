package uemura.java_spring_boot_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.ReadExcel;
import uemura.java_spring_boot_demo.domais.pojo.PropertyVo;
import uemura.java_spring_boot_demo.domais.transfer.IrExceltDto;
import uemura.java_spring_boot_demo.domais.transfer.IrRequestDto;
import uemura.java_spring_boot_demo.domais.transfer.IrResponseDto;
import uemura.java_spring_boot_demo.domais.transfer.PropertyDto;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;
import uemura.java_spring_boot_demo.enums.IrTypeMovimentEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IrService.class);

    public IrResponseDto calculateIr(IrRequestDto irDto) {
        try {
            List<IrExceltDto> irExceltDtos = ReadExcel.read(irDto.getUrlPath());
            return new IrResponseDto()
                    .setPropertys(calculate(irExceltDtos));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private List<PropertyDto> calculate(List<IrExceltDto> irExceltDtos) {
        return calculateProperty(irExceltDtos)
                .stream()
                .map(property -> new PropertyDto()
                        .setProduct(property.getProduct())
                        .setQuantity(property.getQuantity())
                        .setAveragePriceBuy(property.getAveragePriceBuy())
                        .setPrice(property.getTotalBuyPrice()))
                .collect(Collectors.toList());
    }

    private List<PropertyVo> calculateProperty(List<IrExceltDto> irExceltDtos) {
        return irExceltDtos
                .stream()
                .filter(ir -> IrMovimentEnum.LIQUIDATION.getValue().equals(ir.getMoviment()))
                .map(ir -> new PropertyVo()
                        .setProduct(ir.getProduct().trim())
                        .setQuantity(IrTypeMovimentEnum.DEBIT.getValue().equals(ir.getTypeMoviment()) ? new BigDecimal("-" + ir.getQuantity()) : new BigDecimal(ir.getQuantity()))
                        .addTotalPrice(IrTypeMovimentEnum.DEBIT.getValue().equals(ir.getTypeMoviment()) ? BigDecimal.ZERO : new BigDecimal(ir.getQuantity()))
                        .setTotalBuyPrice(IrTypeMovimentEnum.DEBIT.getValue().equals(ir.getTypeMoviment()) ? BigDecimal.ZERO : new BigDecimal(ir.getTotalPrice())))
                .collect(Collectors.groupingBy(PropertyVo::getProduct,
                        Collectors.reducing((propertyRight, propertyLeft) -> propertyRight
                                .addQuantity(propertyLeft.getQuantity())
                                .setQuantityBuy(propertyLeft.getQuantity())
                                .addTotalPrice(propertyLeft.getTotalBuyPrice()))))
                .values()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(property -> property.getQuantity().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
    }

}
