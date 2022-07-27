package uemura.java_spring_boot_demo.domais.transfer;

import java.io.Serializable;
import java.math.BigDecimal;

public class PropertyDto implements Serializable {

    private String product;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal averagePriceBuy;

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

    public BigDecimal getPrice() {
        return price;
    }

    public PropertyDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getAveragePriceBuy() {
        return averagePriceBuy;
    }

    public PropertyDto setAveragePriceBuy(BigDecimal averagePriceBuy) {
        this.averagePriceBuy = averagePriceBuy;
        return this;
    }
}
