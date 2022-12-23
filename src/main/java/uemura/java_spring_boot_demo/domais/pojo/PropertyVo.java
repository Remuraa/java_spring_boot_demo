package uemura.java_spring_boot_demo.domais.pojo;

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
public class PropertyVo implements Serializable {

    private String product;
    private BigDecimal quantity = BigDecimal.ZERO;
    private BigDecimal totalPrice = BigDecimal.ZERO;

}
