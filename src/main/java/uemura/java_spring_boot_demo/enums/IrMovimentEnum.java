package uemura.java_spring_boot_demo.enums;

public enum IrMovimentEnum {

    LIQUIDATION("Transferência - Liquidação"),
    DIVIDEND("Dividendo"),
    YIELD("Rendimento"),
    INTEREST_ON_EQUITY("Juros Sobre Capital Próprio"),
    ;

    private String value;

    IrMovimentEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
