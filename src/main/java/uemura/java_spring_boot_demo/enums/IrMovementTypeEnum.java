package uemura.java_spring_boot_demo.enums;

import java.util.Arrays;

public enum IrMovementTypeEnum {

    CREDIT("Credito"),
    DEBIT("Debito"),
    ;

    private final String value;

    IrMovementTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static IrMovementTypeEnum toEnum(String typeMoviment) {
        return Arrays.stream(values())
                .filter(value -> value.getValue().equals(typeMoviment))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Type Moviment not found!"));
    }
}
