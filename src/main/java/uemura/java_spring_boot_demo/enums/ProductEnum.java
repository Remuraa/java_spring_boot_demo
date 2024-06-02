package uemura.java_spring_boot_demo.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public enum ProductEnum {
    ABEV3(false, "07.526.557/0001-00"),
    BIDI4(false, "00.416.968/0001-01"),
    PETR4(false, "33.000.167/0001-01"),
    AZUL4(false, "09.305.994/0001-29"),
    B3SA3(false, "09.346.601/0001-25"),
    BBAS3(false, "00.000.000/0001-91"),
    BBDC4(false, "60.746.948/0001-12"),
    CSAN3(false, "50.746.577/0001-15"),
    CYRE3(false, "73.178.600/0001-18"),
    GGBR4(false, "33.611.500/0001-19"),
    IGTA3(false, "51.218.147/0001-93"),
    ITSA4(false, "61.532.644/0001-15"),
    ITUB4(false, "60.872.504/0001-23"),
    LREN3(false, "92.754.738/0001-62"),
    MGLU3(false, "47.960.950/0001-21"),
    PSSA3(false, "02.149.205/0001-69"),
    TAEE11(false, "07.859.971/0001-30"),
    @Deprecated
    VIIA3(false, ""),
    WEGE3(false, "84.429.695/0001-11"),
    NUBR33(false, "24.410.913/0001-44"),
    CIEL3(false, "01.027.058/0001-91"),
    VVAR3(false, "33.041.260/0652-90"),
    YDUQ3(false, "08.807.432/0001-10"),
    JBSS3(false, "02.916.265/0001-60"),
    BRKM5(false, "42.150.391/0001-70"),
    VALE3(false, "33.592.510/0001-54"),
    KLBN11(false, "89.637.490/0001-45"),
    BBSE3(false, "17.344.597/0001-94"),
    CPLE3(false, "76.483.817/0001-20"),
    ENAT3(false, "11.669.021/0001-10"),
    PMAM3(false, "60.398.369/0004-79"),
    SAPR4(false, "76.484.013/0001-45"),
    SBSP3(false, "43.776.517/0001-80"),
    TAEE4(false, "07.859.971/0001-30"),
    TRPL4(false, "02.998.611/0001-04"),
    BHIA3(false, "33.041.260/0652-90"),
    IGTI11(false, "60.543.816/0001-93"),
    HCTR11(true, "30.248.180/0001-96"),
    KFOF11(true, "30.091.444/0001-40"),
    IRDM11(true, "28.830.325/0001-10");

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

    public static String getCnpjByProduct(String product) {
        return Arrays.stream(values())
                .filter(value -> value.toString().equals(product))
                .map(ProductEnum::getCnpj)
                .findFirst()
                .orElse("");
    }

    public static Set<String> getAllNameFII() {
        return Arrays.stream(values())
                .filter(ProductEnum::isFII)
                .map(Objects::toString)
                .collect(Collectors.toSet());
    }
}
