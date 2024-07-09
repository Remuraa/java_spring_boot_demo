package uemura.java_spring_boot_demo.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public enum ProductEnum {
    ABEV3(false, "07.526.557/0001-00", EconomicSectorEnum.CONSUMER_STAPLES),
    @Deprecated
    BIDI4(false, "00.416.968/0001-01", null),
    PETR4(false, "33.000.167/0001-01", EconomicSectorEnum.PETROLEUM_GAS_BIOFUELS),
    AZUL4(false, "09.305.994/0001-29", EconomicSectorEnum.INDUSTRIALS),
    B3SA3(false, "09.346.601/0001-25", EconomicSectorEnum.FINANCE),
    BBAS3(false, "00.000.000/0001-91", EconomicSectorEnum.FINANCE),
    BBDC4(false, "60.746.948/0001-12", EconomicSectorEnum.FINANCE),
    CSAN3(false, "50.746.577/0001-15", EconomicSectorEnum.ENERGY),
    CYRE3(false, "73.178.600/0001-18", EconomicSectorEnum.REAL_ESTATE),
    GGBR4(false, "33.611.500/0001-19", EconomicSectorEnum.MATERIALS),
    IGTA3(false, "51.218.147/0001-93", EconomicSectorEnum.REAL_ESTATE),
    ITSA4(false, "61.532.644/0001-15", EconomicSectorEnum.FINANCE),
    ITUB4(false, "60.872.504/0001-23", EconomicSectorEnum.FINANCE),
    LREN3(false, "92.754.738/0001-62", EconomicSectorEnum.CONSUMER_DISCRETIONARY),
    MGLU3(false, "47.960.950/0001-21", EconomicSectorEnum.CONSUMER_DISCRETIONARY),
    PSSA3(false, "02.149.205/0001-69", EconomicSectorEnum.FINANCE),
    TAEE11(false, "07.859.971/0001-30", EconomicSectorEnum.UTILITIES),
    @Deprecated
    VIIA3(false, "", null),
    WEGE3(false, "84.429.695/0001-11", EconomicSectorEnum.INDUSTRIALS),
    NUBR33(false, "24.410.913/0001-44", EconomicSectorEnum.FINANCE),
    CIEL3(false, "01.027.058/0001-91", EconomicSectorEnum.INFORMATION_TECHNOLOGY),
    VVAR3(false, "33.041.260/0652-90", EconomicSectorEnum.CONSUMER_DISCRETIONARY),
    YDUQ3(false, "08.807.432/0001-10", EconomicSectorEnum.COMMUNICATION_SERVICES),
    JBSS3(false, "02.916.265/0001-60", EconomicSectorEnum.CONSUMER_STAPLES),
    BRKM5(false, "42.150.391/0001-70", EconomicSectorEnum.MATERIALS),
    VALE3(false, "33.592.510/0001-54", EconomicSectorEnum.MATERIALS),
    KLBN11(false, "89.637.490/0001-45", EconomicSectorEnum.MATERIALS),
    BBSE3(false, "17.344.597/0001-94", EconomicSectorEnum.FINANCE),
    CPLE3(false, "76.483.817/0001-20", EconomicSectorEnum.UTILITIES),
    ENAT3(false, "11.669.021/0001-10", EconomicSectorEnum.PETROLEUM_GAS_BIOFUELS),
    PMAM3(false, "60.398.369/0004-79", EconomicSectorEnum.MATERIALS),
    SAPR4(false, "76.484.013/0001-45", EconomicSectorEnum.UTILITIES),
    SBSP3(false, "43.776.517/0001-80", EconomicSectorEnum.UTILITIES),
    TAEE4(false, "07.859.971/0001-30", EconomicSectorEnum.UTILITIES),
    TRPL4(false, "02.998.611/0001-04", EconomicSectorEnum.UTILITIES),
    BHIA3(false, "33.041.260/0652-90", EconomicSectorEnum.UTILITIES),
    IGTI11(false, "60.543.816/0001-93", EconomicSectorEnum.REAL_ESTATE),
    HCTR11(true, "30.248.180/0001-96", EconomicSectorEnum.REAL_ESTATE),
    KFOF11(true, "30.091.444/0001-40", EconomicSectorEnum.REAL_ESTATE),
    IRDM11(true, "28.830.325/0001-10", EconomicSectorEnum.REAL_ESTATE);

    private final boolean isFII;
    private final String cnpj;
    private final EconomicSectorEnum sector;

    ProductEnum(boolean isFII, String cnpj, EconomicSectorEnum sector) {
        this.isFII = isFII;
        this.cnpj = cnpj;
        this.sector = sector;
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
