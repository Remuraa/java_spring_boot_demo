package uemura.java_spring_boot_demo.domais.transfer.declare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uemura.java_spring_boot_demo.domais.transfer.ProfitCalculationDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeclareIrResponseDto implements Serializable {

    private List<PropriedadeDto> propriedades;
    private List<ProfitCalculationDto> perdaVendaAcoesPorMes;
    private BigDecimal annualIncome;
    private String moviments;
    private String observacaoSobreLucros;
    private List<LucrosDto> lucrosDividendos;
    private List<LucrosDto> jurosSobreCapitalProprio;
    private List<LucrosDto> redimento;

}
