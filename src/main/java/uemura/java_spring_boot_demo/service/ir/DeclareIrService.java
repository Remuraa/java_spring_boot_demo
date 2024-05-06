package uemura.java_spring_boot_demo.service.ir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.IrBuilder;
import uemura.java_spring_boot_demo.converter.DeclareConverter;
import uemura.java_spring_boot_demo.domais.entity.MovimentEntity;
import uemura.java_spring_boot_demo.domais.transfer.declare.DeclareIrResponseDto;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;
import uemura.java_spring_boot_demo.repository.MovimentRepository;
import uemura.java_spring_boot_demo.repository.PropertyRepository;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class DeclareIrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeclareIrService.class);

    private final IrBuilder irBuilder;
    private final MovimentRepository movimentRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public DeclareIrService(IrBuilder irBuilder, MovimentRepository movimentRepository, PropertyRepository propertyRepository) {
        this.irBuilder = irBuilder;
        this.movimentRepository = movimentRepository;
        this.propertyRepository = propertyRepository;
    }

    public DeclareIrResponseDto getDeclareIr(int year) {
        LOGGER.info("Declare IR: {}", year);
        LocalDate firstDayOfYear = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate lasDayOfYear = firstDayOfYear.with(TemporalAdjusters.lastDayOfYear());
        List<MovimentEntity> movimentsDividend = movimentRepository.findAllByMovimentDateBetweenAndMoviment(firstDayOfYear, lasDayOfYear, IrMovimentEnum.DIVIDEND);
        List<MovimentEntity> movimentsInterestOnEquity = movimentRepository.findAllByMovimentDateBetweenAndMoviment(firstDayOfYear, lasDayOfYear, IrMovimentEnum.INTEREST_ON_EQUITY);
        List<MovimentEntity> movimentsYield = movimentRepository.findAllByMovimentDateBetweenAndMoviment(firstDayOfYear, lasDayOfYear, IrMovimentEnum.YIELD);



        return DeclareIrResponseDto.builder()
                .lucrosDividendos(DeclareConverter.converterEarnings(irBuilder.getEarningsReceived(movimentsDividend)))
                .jurosSobreCapitalProprio(DeclareConverter.converterEarnings(irBuilder.getEarningsReceived(movimentsInterestOnEquity)))
                .redimento(DeclareConverter.converterEarnings(irBuilder.getEarningsReceived(movimentsYield)))
                .build();
    }

}
