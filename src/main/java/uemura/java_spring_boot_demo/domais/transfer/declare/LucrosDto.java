package uemura.java_spring_boot_demo.domais.transfer.declare;

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
    private String valor;

}
