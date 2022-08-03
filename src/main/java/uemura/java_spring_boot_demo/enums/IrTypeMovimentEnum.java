package uemura.java_spring_boot_demo.enums;

import java.util.Arrays;

public enum IrTypeMovimentEnum {

    CREDIT("Credito"),
    DEBIT("Debito"),
    ;

    private String value;

    IrTypeMovimentEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static IrTypeMovimentEnum toEnum(String typeMoviment) {
        return Arrays.stream(values())
                .filter(value -> value.getValue().equals(typeMoviment))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Type Moviment not found!"));
    }
}
