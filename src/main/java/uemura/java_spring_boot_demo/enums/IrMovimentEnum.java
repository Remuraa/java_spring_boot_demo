package uemura.java_spring_boot_demo.enums;

import java.util.Arrays;

public enum IrMovimentEnum {

    LIQUIDATION("Transferência - Liquidação"),
    DIVIDEND("Dividendo"),
    YIELD("Rendimento"),
    INTEREST_ON_EQUITY("Juros Sobre Capital Próprio"),
    ;

    private final String value;

    IrMovimentEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static IrMovimentEnum toEnum(String valueIr) {
        return Arrays.stream(values())
                .filter(value -> value.getValue().equals(valueIr))
                .findFirst()
                .orElse(null);
    }
}
