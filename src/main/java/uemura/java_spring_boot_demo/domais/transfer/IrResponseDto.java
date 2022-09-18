package uemura.java_spring_boot_demo.domais.transfer;

import java.io.Serializable;
import java.util.List;

public class IrResponseDto implements Serializable {

    private List<PropertyDto> propertys;
    private String moviments;

    public List<PropertyDto> getPropertys() {
        return propertys;
    }

    public IrResponseDto setPropertys(List<PropertyDto> propertys) {
        this.propertys = propertys;
        return this;
    }

    public String getMoviments() {
        return moviments;
    }

    public IrResponseDto setMoviments(String moviments) {
        this.moviments = moviments;
        return this;
    }
}
