package uemura.java_spring_boot_demo.domais.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "property")
@Getter
@Setter
public class PropertyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column
    private String product;
    @Column
    private BigDecimal quantity;
    @Column
    private BigDecimal averagePrice;
    @Column
    private BigDecimal totalPrice;


}
