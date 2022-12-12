package uemura.java_spring_boot_demo.domais.transfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class IrResponseDto implements Serializable {

    private List<PropertyDto> propertys;
    private List<ProfitCalculationDto> profitCalculation;
    private List<ProfitCalculationDto> lostCalculation;
    private BigDecimal annualIncome;
    private String moviments;

    public List<PropertyDto> getPropertys() {
        return propertys;
    }

    public IrResponseDto setPropertys(List<PropertyDto> propertys) {
        this.propertys = propertys;
        return this;
    }

    public String getMoviments() {
        return moviments;
    }

    public IrResponseDto setMoviments(String moviments) {
        this.moviments = moviments;
        return this;
    }

    public IrResponseDto setProfitCalculation(List<ProfitCalculationDto> profitCalculation) {
        this.profitCalculation = profitCalculation;
        return this;
    }

    public List<ProfitCalculationDto> getProfitCalculation() {
        return profitCalculation;
    }

    public IrResponseDto setLostCalculation(List<ProfitCalculationDto> lostCalculation) {
        this.lostCalculation = lostCalculation;
        return this;
    }

    public List<ProfitCalculationDto> getLostCalculation() {
        return lostCalculation;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public IrResponseDto setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
        return this;
    }
}
