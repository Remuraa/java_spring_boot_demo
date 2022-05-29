package uemura.java_spring_boot_demo.domais.transfer;

import com.poiji.annotation.ExcelCellName;

import java.io.Serializable;
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
    private String quantity;
    @ExcelCellName("Preço unitário")
    private String unityPrice;
    @ExcelCellName("Valor da Operação")
    private String totalPrice;

    public String getTypeMoviment() {
        return typeMoviment;
    }

    public IrExceltDto setTypeMoviment(String typeMoviment) {
        this.typeMoviment = typeMoviment;
        return this;
    }

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

    public String getQuantity() {
        return quantity;
    }

    public IrExceltDto setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getUnityPrice() {
        return unityPrice;
    }

    public IrExceltDto setUnityPrice(String unityPrice) {
        this.unityPrice = unityPrice;
        return this;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public IrExceltDto setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}
