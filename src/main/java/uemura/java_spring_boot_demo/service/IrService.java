package uemura.java_spring_boot_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.IrBuilder;
import uemura.java_spring_boot_demo.component.ReadExcel;
import uemura.java_spring_boot_demo.converter.MovimentConverter;
import uemura.java_spring_boot_demo.domais.entity.MovimentEntity;
import uemura.java_spring_boot_demo.domais.transfer.IrExceltDto;
import uemura.java_spring_boot_demo.domais.transfer.IrRequestDto;
import uemura.java_spring_boot_demo.domais.transfer.IrResponseDto;
import uemura.java_spring_boot_demo.repository.PropertyRepository;

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
    private PropertyRepository propertyRepository;

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
        propertyRepository.saveAll(movimentEntities);

        movimentEntities
                .forEach(movimentEntity -> {
                    LOGGER.info(movimentEntity.getProduct() + " " + movimentEntity.getDate());
                    propertyRepository.save(movimentEntity);
                });
    }
}
