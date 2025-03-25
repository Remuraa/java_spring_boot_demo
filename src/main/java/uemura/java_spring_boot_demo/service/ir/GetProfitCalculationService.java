package uemura.java_spring_boot_demo.service.ir;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.IrBuilder;
import uemura.java_spring_boot_demo.domains.entity.MovimentEntity;
import uemura.java_spring_boot_demo.domains.entity.PropertyEntity;
import uemura.java_spring_boot_demo.domains.transfer.ProfitCalculationResponseDto;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;
import uemura.java_spring_boot_demo.repository.MovimentRepository;
import uemura.java_spring_boot_demo.repository.PropertyRepository;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@AllArgsConstructor
public class GetProfitCalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetProfitCalculationService.class);

    private final IrBuilder irBuilder;
    private final MovimentRepository movimentRepository;
    private final PropertyRepository propertyRepository;

    public ProfitCalculationResponseDto getProfitCalculation(int year) {
        LOGGER.info("Get Profit Calculation: {}", year);
        LocalDate firstDayOfYear = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate lasDayOfYear = firstDayOfYear.with(TemporalAdjusters.lastDayOfYear());
        List<MovimentEntity> moviments = movimentRepository.findAllByMovimentDateBetweenAndMoviment(firstDayOfYear, lasDayOfYear, IrMovimentEnum.LIQUIDATION);
        List<PropertyEntity> propertiesLastYear = propertyRepository.findAllByYear(year - 1);

        return ProfitCalculationResponseDto.builder()
                .profitCalculation(irBuilder.getProfitCalculation(moviments, propertiesLastYear))
                .build();
    }

}
