package uemura.java_spring_boot_demo.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uemura.java_spring_boot_demo.domais.transfer.EarningsDto;
import uemura.java_spring_boot_demo.domais.transfer.declare.LucrosDto;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class DeclareConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeclareConverter.class);

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,##0.00");

    public static List<LucrosDto> converterEarnings(List<EarningsDto> earningsAndDividendsReceived) {
        return earningsAndDividendsReceived.stream()
                .map(earning -> LucrosDto.builder()
                        .produto(earning.getProduct())
                        .valor(DECIMAL_FORMAT.format(earning.getValue()))
                        .build())
                .collect(Collectors.toList());
    }

}
