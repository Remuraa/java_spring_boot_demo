package uemura.java_spring_boot_demo.domais;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sales")
public class SalesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    public Long getId() {
        return id;
    }

    public SalesEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SalesEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
