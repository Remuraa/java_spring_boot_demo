package uemura.java_spring_boot_demo.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

//Todo Can be replace for regex?
public enum ProductEnum {
    ABEV3(false, null),
    BIDI4(false, null),
    PETR4(false, null),
    AZUL4(false, null),
    B3SA3(false, null),
    BBAS3(false, null),
    BBDC4(false, "60.746.948/0001-12"),
    CSAN3(false, null),
    CYRE3(false, null),
    GGBR4(false, null),
    IGTA3(false, null),
    ITSA4(false, "61.532.644/0001-15"),
    ITUB4(false, "60.872.504/0001-23"),
    LREN3(false, "92.754.738/0001-62"),
    MGLU3(false, null),
    PSSA3(false, "02.149.205/0001-69"),
    TAEE11(false, null),
    VIIA3(false, null),
    WEGE3(false, "84.429.695/0001-11"),
    NUBR33(false, null),
    CIEL3(false, null),
    VVAR3(false, null),
    YDUQ3(false, null),
    JBSS3(false, null),
    BRKM5(false, null),
    VALE3(false, "33.592.510/0001-54"),
    KLBN11(false, "89.637.490/0001-45"),
    BBSE3(false, null),
    CPLE3(false, "76.483.817/0001-20"),
    ENAT3(false, null),
    PMAM3(false, null),
    SAPR4(false, "76.484.013/0001-45"),
    SBSP3(false, null),
    TAEE4(false, null),
    TRPL4(false, "02.998.611/0001-04"),
    BHIA3(false, null),
    IGTI11(false, null),
    HCTR11(true, "30.248.180/0001-96"),
    KFOF11(true, null),
    IRDM11(true, null);

    private final boolean isFII;
    private final String cnpj;

    ProductEnum(boolean isFII, String cnpj) {
        this.isFII = isFII;
        this.cnpj = cnpj;
    }

    public boolean isFII() {
        return isFII;
    }

    public String getCnpj() {
        return cnpj;
    }

    public static String convertNameProduct(String productDescription) {
        return Arrays.stream(values())
                .filter(value -> productDescription.contains(value.toString()))
                .map(Enum::toString)
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiples Product!");
                })
                .orElse(productDescription);
    }

    public static Set<String> getAllNameFII() {
        return Arrays.stream(values())
                .filter(ProductEnum::isFII)
                .map(Objects::toString)
                .collect(Collectors.toSet());
    }
}
