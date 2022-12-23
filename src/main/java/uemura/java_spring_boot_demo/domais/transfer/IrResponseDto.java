package uemura.java_spring_boot_demo.domais.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IrResponseDto implements Serializable {

    private List<PropertyDto> propertys;
    private List<ProfitCalculationDto> profitCalculation;
    private List<ProfitCalculationDto> lostCalculation;
    private BigDecimal annualIncome;
    private String moviments;

}
