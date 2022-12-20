package uemura.java_spring_boot_demo.domais.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uemura.java_spring_boot_demo.enums.IrMovementTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockPortfolioAnalyticalVo implements Serializable {

    private String product;
    private LocalDate date;
    private IrMovementTypeEnum movementType;
    private BigDecimal quantity = BigDecimal.ZERO;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal stockPortfolioQuantity = BigDecimal.ZERO;
    private BigDecimal stockPortfolioAveragePrice = BigDecimal.ZERO;

}
