package uemura.java_spring_boot_demo.domais.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyDto implements Serializable {

    private int year;
    private String product;
    private BigDecimal quantity;
    private BigDecimal averagePrice;
    private BigDecimal totalPrice;

}
