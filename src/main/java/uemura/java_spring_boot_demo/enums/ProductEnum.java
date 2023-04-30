package uemura.java_spring_boot_demo.enums;

import java.util.Arrays;

//Todo Can be replace for regex?
public enum ProductEnum {
    ABEV3,
    BIDI4,
    PETR4,
    AZUL4,
    B3SA3,
    BBAS3,
    BBDC4,
    CSAN3,
    CYRE3,
    GGBR4,
    IGTA3,
    ITSA4,
    ITUB4,
    LREN3,
    MGLU3,
    PSSA3,
    TAEE11,
    VIIA3,
    WEGE3,
    KFOF11,
    NUBR33,
    CIEL3,
    VVAR3,
    YDUQ3,
    JBSS3,
    IRDM11,
    BRKM5,
    VALE3,
    KLBN11
    ;

    public static String convertNameProduct(String productDescription){
        return Arrays.stream(values())
                .filter(value -> productDescription.contains(value.toString()))
                .map(Enum::toString)
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiples Product!");
                })
                .orElse(productDescription);
    }
}
