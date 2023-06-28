package uemura.java_spring_boot_demo.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.IrBuilder;
import uemura.java_spring_boot_demo.component.ReadExcel;
import uemura.java_spring_boot_demo.converter.MovimentConverter;
import uemura.java_spring_boot_demo.domais.entity.MovimentEntity;
import uemura.java_spring_boot_demo.domais.entity.PropertyEntity;
import uemura.java_spring_boot_demo.domais.transfer.*;
import uemura.java_spring_boot_demo.enums.IrMovimentEnum;
import uemura.java_spring_boot_demo.repository.MovimentRepository;
import uemura.java_spring_boot_demo.repository.PropertyRepository;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class IrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IrService.class);

    @Value("${config.url-files}")
    private String urlFiles;


    @Autowired
    private IrBuilder irBuilder;
    @Autowired
    private MovimentRepository movimentRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private ModelMapper modelMapper;

    public IrResponseDto calculateIr(IrRequestDto irDto) {
        try {
            List<IrExceltDto> irExceltDtos = ReadExcel.read(urlFiles + irDto.getNameFile());

            return irBuilder.getResponse(irDto, irExceltDtos);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public void importIr(String pathFile) {
        List<MovimentEntity> movimentEntities = ReadExcel.read(urlFiles + pathFile)
                .stream()
                .map(MovimentConverter::converter)
                .filter(movimentEntity -> Objects.nonNull(movimentEntity.getMoviment()))
                .collect(Collectors.toList());
        movimentRepository.saveAll(movimentEntities);
    }

    public IrPropertiesResponseDto getProperties(PropetiesRequestDto requestDto) {
        LocalDate firstDayOfYear = LocalDate.of(requestDto.getYear(), Month.JANUARY, 1);
        LocalDate lasDayOfYear = firstDayOfYear.with(TemporalAdjusters.lastDayOfYear());
        List<MovimentEntity> moviments = movimentRepository.findAllByMovimentDateBetweenAndMoviment(firstDayOfYear, lasDayOfYear, IrMovimentEnum.LIQUIDATION);

        return IrPropertiesResponseDto.builder()
                .properties(irBuilder.getProperty(moviments, requestDto))
                .build();
    }

    public void saveProperty(PropertyRequestDto propertyRequestDto) {
        List<PropertyEntity> propertyEntities = propertyRequestDto.getProperties()
                .stream()
                .map(propertyDto -> modelMapper.map(propertyDto, PropertyEntity.class))
                .collect(Collectors.toList());
        propertyRepository.saveAll(propertyEntities);
    }
}
