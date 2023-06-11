package uemura.java_spring_boot_demo.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uemura.java_spring_boot_demo.domais.entity.MovimentEntity;
import uemura.java_spring_boot_demo.domais.transfer.IrExceltDto;
import uemura.java_spring_boot_demo.enums.IrMovementTypeEnum;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;
import uemura.java_spring_boot_demo.enums.ProductEnum;

import java.math.BigDecimal;

public class MovimentConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovimentConverter.class);

    public static MovimentEntity converter(IrExceltDto irExceltDto) {
        LOGGER.info("Converter {} to MovimentEntity", irExceltDto );
        return MovimentEntity.builder()
                .typeMoviment(IrMovementTypeEnum.toEnum(irExceltDto.getTypeMoviment()))
                .date(irExceltDto.getDate())
                .moviment(IrMovimentEnum.toEnum(irExceltDto.getMoviment()))
                .product(ProductEnum.convertNameProduct(irExceltDto.getProduct()))
                .institution(irExceltDto.getInstitution())
                .quantity(new BigDecimal(irExceltDto.getQuantity()))
                .unityPrice(getBigDecimal(irExceltDto.getUnityPrice()))
                .totalPrice(getBigDecimal(irExceltDto.getTotalPrice()))
                .build();
    }

    private static BigDecimal getBigDecimal(String value) {
        String formatValue = value.replace("R$", "").replace(",", "").replace("-", "").trim();
        return formatValue.isBlank() ? BigDecimal.ZERO : new BigDecimal(formatValue);
    }

}
