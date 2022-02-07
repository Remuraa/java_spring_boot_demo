package uemura.java_spring_boot_demo.domais;

import java.io.Serializable;

public class SalesRequestDto implements Serializable {

    private String description;

    public String getDescription() {
        return description;
    }

    public SalesRequestDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
