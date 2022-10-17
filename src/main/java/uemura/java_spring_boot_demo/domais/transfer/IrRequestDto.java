package uemura.java_spring_boot_demo.domais.transfer;

import java.io.Serializable;
import java.util.List;

public class IrRequestDto implements Serializable {

    private String nameFile;
    private List<PropertyDto> propertysLastYear;

    public String getNameFile() {
        return nameFile;
    }

    public IrRequestDto setNameFile(String nameFile) {
        this.nameFile = nameFile;
        return this;
    }

    public List<PropertyDto> getPropertysLastYear() {
        return propertysLastYear;
    }

    public void setPropertysLastYear(List<PropertyDto> propertysLastYear) {
        this.propertysLastYear = propertysLastYear;
    }
}
