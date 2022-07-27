package uemura.java_spring_boot_demo.domais.transfer;

import java.io.Serializable;
import java.util.List;

public class IrResponseDto implements Serializable {

    private List<PropertyDto> propertys;

    public List<PropertyDto> getPropertys() {
        return propertys;
    }

    public IrResponseDto setPropertys(List<PropertyDto> propertys) {
        this.propertys = propertys;
        return this;
    }
}
