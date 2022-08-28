package uemura.java_spring_boot_demo.domais.transfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PropertyDto implements Serializable {

    private String product;
    private BigDecimal quantity;
    private BigDecimal averagePrice;

    public String getProduct() {
        return product;
    }

    public PropertyDto setProduct(String product) {
        this.product = product;
        return this;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public PropertyDto setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public PropertyDto addQuantity(BigDecimal quantity) {
        this.quantity = this.quantity.add(quantity);
        return this;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public PropertyDto setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

}
