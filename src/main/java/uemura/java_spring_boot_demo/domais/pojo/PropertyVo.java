package uemura.java_spring_boot_demo.domais.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PropertyVo implements Serializable {

    private String product;
    private BigDecimal quantity;
    private BigDecimal totalPrice;

    public String getProduct() {
        return product;
    }

    public PropertyVo setProduct(String product) {
        this.product = product;
        return this;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public PropertyVo setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public PropertyVo addQuantity(BigDecimal quantity) {
        this.quantity = this.quantity.add(quantity);
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public PropertyVo setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public PropertyVo addTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = this.totalPrice.add(totalPrice);
        return this;
    }

    public BigDecimal getAveragePrice() {
        return totalPrice.divide(quantity).setScale(2, RoundingMode.UP);
    }

    @Override
    public String toString() {
        return "PropertyVo{" +
                "product='" + product + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
