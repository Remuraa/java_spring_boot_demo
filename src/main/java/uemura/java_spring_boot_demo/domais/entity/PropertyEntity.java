package uemura.java_spring_boot_demo.domais.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
    private int product;

}
