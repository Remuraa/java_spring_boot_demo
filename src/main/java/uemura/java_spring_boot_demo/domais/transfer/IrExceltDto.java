package uemura.java_spring_boot_demo.domais.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poiji.annotation.ExcelCellName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IrExceltDto implements Serializable {

    @ExcelCellName("Entrada/Saída")
    private String typeMoviment;
    @ExcelCellName("Data")
    private LocalDate date;
    @ExcelCellName("Movimentação")
    private String moviment;
    @ExcelCellName("Produto")
    private String product;
    @ExcelCellName("Instituição")
    private String institution;
    @ExcelCellName("Quantidade")
    private String quantity;
    @ExcelCellName("Preço unitário")
    private String unityPrice;
    @ExcelCellName("Valor da Operação")
    private String totalPrice;

}
