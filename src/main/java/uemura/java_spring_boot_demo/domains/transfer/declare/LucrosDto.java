package uemura.java_spring_boot_demo.domains.transfer.declare;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LucrosDto implements Serializable {

    private String produto;
    private String cnpj;
    private String valor;
    private String descricao;

}
