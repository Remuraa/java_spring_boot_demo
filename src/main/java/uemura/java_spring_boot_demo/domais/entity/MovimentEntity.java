package uemura.java_spring_boot_demo.domais.entity;

import lombok.*;
import lombok.experimental.Accessors;
import uemura.java_spring_boot_demo.enums.IrMovementTypeEnum;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "moviment")
@Getter
@Setter
public class MovimentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column
    private IrMovementTypeEnum typeMoviment;
    @Column
    private LocalDate date;
    @Column
    private IrMovimentEnum moviment;
    @Column
    private String product;
    @Column
    private String institution;
    @Column
    private BigDecimal quantity;
    @Column
    private BigDecimal unityPrice;
    @Column
    private BigDecimal totalPrice;

}
