package uemura.java_spring_boot_demo.service.ir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.IrBuilder;
import uemura.java_spring_boot_demo.domais.entity.MovimentEntity;
import uemura.java_spring_boot_demo.domais.entity.PropertyEntity;
import uemura.java_spring_boot_demo.domais.transfer.IrPropertiesResponseDto;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;
import uemura.java_spring_boot_demo.repository.MovimentRepository;
import uemura.java_spring_boot_demo.repository.PropertyRepository;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class GetPropertyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetPropertyService.class);

    private final IrBuilder irBuilder;
    private final MovimentRepository movimentRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public GetPropertyService(IrBuilder irBuilder, MovimentRepository movimentRepository, PropertyRepository propertyRepository) {
        this.irBuilder = irBuilder;
        this.movimentRepository = movimentRepository;
        this.propertyRepository = propertyRepository;
    }

    public IrPropertiesResponseDto getProperties(int year) {
        LOGGER.info("Get properties: {}", year);
        LocalDate firstDayOfYear = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate lasDayOfYear = firstDayOfYear.with(TemporalAdjusters.lastDayOfYear());
        List<MovimentEntity> moviments = movimentRepository.findAllByMovimentDateBetweenAndMoviment(firstDayOfYear, lasDayOfYear, IrMovimentEnum.LIQUIDATION);
        List<PropertyEntity> propertiesLastYear = propertyRepository.findAllByYear(year-1);

        return IrPropertiesResponseDto.builder()
                .properties(irBuilder.getProperty(moviments, propertiesLastYear))
                .build();
    }

}
