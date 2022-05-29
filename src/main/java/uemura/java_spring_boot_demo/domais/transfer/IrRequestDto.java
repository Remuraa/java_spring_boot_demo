package uemura.java_spring_boot_demo.domais.transfer;

import java.io.Serializable;

public class IrRequestDto implements Serializable {

    private String urlPath;

    public String getUrlPath() {
        return urlPath;
    }

    public IrRequestDto setUrlPath(String urlPath) {
        this.urlPath = urlPath;
        return this;
    }
}
