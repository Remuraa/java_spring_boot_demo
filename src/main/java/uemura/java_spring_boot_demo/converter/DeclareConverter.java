package uemura.java_spring_boot_demo.converter;

import uemura.java_spring_boot_demo.domains.transfer.EarningsDto;
import uemura.java_spring_boot_demo.domains.transfer.PropertyDto;
import uemura.java_spring_boot_demo.domains.transfer.declare.LucrosDto;
import uemura.java_spring_boot_demo.domains.transfer.declare.PropriedadeDto;
import uemura.java_spring_boot_demo.enums.ProductEnum;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

public class DeclareConverter {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,##0.00");

    private DeclareConverter() {
    }

    public static List<LucrosDto> converterEarnings(List<EarningsDto> earningsAndDividendsReceived, Function<EarningsDto, String> fnDescricao) {
        return earningsAndDividendsReceived.stream()
                .map(earning -> LucrosDto.builder()
                        .produto(earning.getProduct())
                        .cnpj(earning.getCnpj())
                        .valor(DECIMAL_FORMAT.format(earning.getValue()))
                        .descricao(fnDescricao.apply(earning))
                        .build())
                .toList();
    }

    public static List<PropriedadeDto> converterProprieadade(List<PropertyDto> properties) {
        return properties.stream()
                .map(propertie -> PropriedadeDto.builder()
                        .descricao("AÇÕES DE " + propertie.getProduct() + " / QUANTIDADE: " + propertie.getQuantity() + " UN / CUSTO MEDIO: " + propertie.getAveragePrice() + " / CUSTODIADA NA NU INVEST - CNPJ: 62.169.875/0001-79")
                        .produto(propertie.getProduct())
                        .valor(DECIMAL_FORMAT.format(propertie.getTotalPrice()))
                        .cnpj(ProductEnum.getCnpjByProduct(propertie.getProduct()))
                        .build())
                .toList();
    }
}