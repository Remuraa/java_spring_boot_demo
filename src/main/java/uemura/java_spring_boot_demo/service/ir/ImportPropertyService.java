package uemura.java_spring_boot_demo.service.ir;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.domais.entity.PropertyEntity;
import uemura.java_spring_boot_demo.domais.transfer.PropertyRequestDto;
import uemura.java_spring_boot_demo.repository.MovimentRepository;
import uemura.java_spring_boot_demo.repository.PropertyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImportPropertyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportPropertyService.class);

    private final PropertyRepository propertyRepository;
    private final MovimentRepository movimentRepository;
    private final ModelMapper modelMapper;

    public void importProperties(PropertyRequestDto propertyRequestDto) {
        LOGGER.info("Save properties");
        List<PropertyEntity> propertyEntities = propertyRequestDto.getProperties()
                .stream()
                .map(propertyDto -> modelMapper.map(propertyDto, PropertyEntity.class))
                .collect(Collectors.toList());
        propertyRepository.saveAll(propertyEntities);
    }

    public void deleteAll() {
        LOGGER.info("Delete everything");
        propertyRepository.deleteAll();
        movimentRepository.deleteAll();
    }
}
