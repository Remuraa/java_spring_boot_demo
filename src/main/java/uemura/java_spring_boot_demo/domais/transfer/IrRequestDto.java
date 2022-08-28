package uemura.java_spring_boot_demo.domais.transfer;

import java.io.Serializable;
import java.util.List;

public class IrRequestDto implements Serializable {

    private String urlPath;
    private List<PropertyDto> propertysLastYear;

    public String getUrlPath() {
        return urlPath;
    }

    public IrRequestDto setUrlPath(String urlPath) {
        this.urlPath = urlPath;
        return this;
    }

    public List<PropertyDto> getPropertysLastYear() {
        return propertysLastYear;
    }

    public void setPropertysLastYear(List<PropertyDto> propertysLastYear) {
        this.propertysLastYear = propertysLastYear;
    }
}
