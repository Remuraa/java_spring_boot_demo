package uemura.java_spring_boot_demo.domais.transfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class ProfitCalculationDto implements Serializable {

    private String product;
    private Month month;
    private BigDecimal value;

    public String getProduct() {
        return product;
    }

    public ProfitCalculationDto setProduct(String product) {
        this.product = product;
        return this;
    }

    public Month getMonth() {
        return month;
    }

    public ProfitCalculationDto setMonth(Month month) {
        this.month = month;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public ProfitCalculationDto setValue(BigDecimal value) {
        this.value = value;
        return this;
    }
}
