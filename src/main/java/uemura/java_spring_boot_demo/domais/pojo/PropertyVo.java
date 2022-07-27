package uemura.java_spring_boot_demo.domais.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PropertyVo implements Serializable {

    private String product;
    private BigDecimal quantity = BigDecimal.ZERO;
    private BigDecimal quantityBuy = BigDecimal.ZERO;
    private BigDecimal totalBuyPrice = BigDecimal.ZERO;

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

    public BigDecimal getQuantityBuy() {
        return quantityBuy;
    }

    public PropertyVo setQuantityBuy(BigDecimal quantityBuy) {
        this.quantityBuy = quantityBuy;
        return this;
    }

    public BigDecimal getTotalBuyPrice() {
        return totalBuyPrice;
    }

    public PropertyVo setTotalBuyPrice(BigDecimal totalBuyPrice) {
        this.totalBuyPrice = totalBuyPrice;
        return this;
    }

    public PropertyVo addTotalPrice(BigDecimal totalPrice) {
        this.totalBuyPrice = this.totalBuyPrice.add(totalPrice);
        return this;
    }

    public BigDecimal getAveragePriceBuy() {
        return totalBuyPrice.divide(quantity, 2, RoundingMode.UP);
    }

    @Override
    public String toString() {
        return "PropertyVo{" +
                "product='" + product + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalBuyPrice +
                '}';
    }
}
