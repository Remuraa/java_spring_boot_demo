package uemura.java_spring_boot_demo.enums;

import lombok.Getter;

@Getter
public enum EconomicSectorEnum {

    INFORMATION_TECHNOLOGY("Information Technology"),
    HEALTHCARE("Healthcare"),
    FINANCE("Finance"),
    CONSUMER_DISCRETIONARY("Consumer Discretionary"),
    CONSUMER_STAPLES("Consumer Staples"),
    COMMUNICATION_SERVICES("Communication Services"),
    INDUSTRIALS("Industrials"),
    CONSUMER_GOODS("Consumer Goods"),
    ENERGY("Energy"),
    UTILITIES("Utilities"),
    REAL_ESTATE("Real Estate"),
    MATERIALS("Materials"),
    PETROLEUM_GAS_BIOFUELS("Petroleum, Gas, and Biofuels");

    private final String description;

    EconomicSectorEnum(String description) {
        this.description = description;
    }
}
