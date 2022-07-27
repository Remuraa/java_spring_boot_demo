package uemura.java_spring_boot_demo.enums;

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
}
