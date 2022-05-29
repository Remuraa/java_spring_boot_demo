package uemura.java_spring_boot_demo.domais.transfer;

import com.poiji.annotation.ExcelCellName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class IrExceltDto implements Serializable {

    @ExcelCellName("Entrada/Saída")
    private String typeMoviment;
    @ExcelCellName("Data")
    private LocalDate date;
    @ExcelCellName("Movimentação")
    private String moviment;
    @ExcelCellName("Produto")
    private String product;
    @ExcelCellName("Instituição")
    private String institution;
    @ExcelCellName("Quantidade")
    private BigDecimal quantity;
    @ExcelCellName("Preço unitário")
    private BigDecimal priceUnity;

    public LocalDate getDate() {
        return date;
    }

    public IrExceltDto setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getMoviment() {
        return moviment;
    }

    public IrExceltDto setMoviment(String moviment) {
        this.moviment = moviment;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public IrExceltDto setProduct(String product) {
        this.product = product;
        return this;
    }

    public String getInstitution() {
        return institution;
    }

    public IrExceltDto setInstitution(String institution) {
        this.institution = institution;
        return this;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public IrExceltDto setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getPriceUnity() {
        return priceUnity;
    }

    public IrExceltDto setPriceUnity(BigDecimal priceUnity) {
        this.priceUnity = priceUnity;
        return this;
    }
}
