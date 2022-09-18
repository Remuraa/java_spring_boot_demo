package uemura.java_spring_boot_demo.domais.pojo;

import uemura.java_spring_boot_demo.enums.IrTypeMovimentEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class StockPortfolioAnalyticalVo implements Serializable {

    private String product;
    private LocalDate date;
    private IrTypeMovimentEnum typeMoviment;
    private BigDecimal quantity = BigDecimal.ZERO;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal stockPortfolioQuantity = BigDecimal.ZERO;
    private BigDecimal stockPortfolioAveragePrice = BigDecimal.ZERO;

    public String getProduct() {
        return product;
    }

    public StockPortfolioAnalyticalVo setProduct(String product) {
        this.product = product;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public StockPortfolioAnalyticalVo setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public IrTypeMovimentEnum getTypeMoviment() {
        return typeMoviment;
    }

    public StockPortfolioAnalyticalVo setTypeMoviment(IrTypeMovimentEnum typeMoviment) {
        this.typeMoviment = typeMoviment;
        return this;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public StockPortfolioAnalyticalVo setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public StockPortfolioAnalyticalVo setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }


    public BigDecimal getAverageBuy() {
        return quantity.compareTo(BigDecimal.ZERO)  == 0 ? BigDecimal.ZERO : totalPrice.divide(quantity, 2, RoundingMode.UP);
    }

    public BigDecimal getStockPortfolioQuantity() {
        return stockPortfolioQuantity;
    }

    public StockPortfolioAnalyticalVo setStockPortfolioQuantity(BigDecimal stockPortfolioQuantity) {
        this.stockPortfolioQuantity = stockPortfolioQuantity;
        return this;
    }

    public BigDecimal getStockPortfolioAveragePrice() {
        return stockPortfolioAveragePrice;
    }

    public StockPortfolioAnalyticalVo setStockPortfolioAveragePrice(BigDecimal stockPortfolioAveragePrice) {
        this.stockPortfolioAveragePrice = stockPortfolioAveragePrice;
        return this;
    }

    @Override
    public String toString() {
        return date.toString() +
                ";'" + product +
                ";" + typeMoviment +
                ";" + quantity +
                ";" + totalPrice +
                ";" + stockPortfolioQuantity +
                ";" + stockPortfolioAveragePrice;
    }
}
