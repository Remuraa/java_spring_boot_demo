package uemura.java_spring_boot_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.ReadExcel;
import uemura.java_spring_boot_demo.domais.pojo.PropertyVo;
import uemura.java_spring_boot_demo.domais.transfer.IrExceltDto;
import uemura.java_spring_boot_demo.domais.transfer.IrRequestDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IrService.class);

    public void calculateIr(IrRequestDto irDto) {
        try {
            List<IrExceltDto> irExceltDtos = ReadExcel.read(irDto.getUrlPath());
            Map<String, Optional<PropertyVo>> map = irExceltDtos
                    .stream()
                    .filter(ir -> "Transferência - Liquidação".equals(ir.getMoviment()))
                    .filter(ir -> "Credito".equals(ir.getTypeMoviment()))
                    .map(ir -> new PropertyVo()
                            .setProduct(ir.getProduct().trim())
                            .setQuantity(new BigDecimal(ir.getQuantity()))
                            .setTotalPrice(new BigDecimal(ir.getTotalPrice())))
                    .collect(Collectors.groupingBy(PropertyVo::getProduct,
                            Collectors.reducing((propertyRight, propertyLeft) -> propertyRight
                                    .addQuantity(propertyLeft.getQuantity())
                                    .addTotalPrice(propertyLeft.getTotalPrice()))));
            map.values()
                    .stream()
                    .map(Objects::toString)
                    .sorted()
                    .forEach(System.out::println);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
