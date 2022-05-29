package uemura.java_spring_boot_demo.domais.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PropertyVo implements Serializable {

    private String product;
    private BigDecimal quantity;
    private BigDecimal averagePrice;

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

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public PropertyVo setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    @Override
    public String toString() {
        return "PropertyVo{" +
                "product='" + product + '\'' +
                ", quantity=" + quantity +
                ", averagePrice=" + averagePrice +
                '}';
    }
}
