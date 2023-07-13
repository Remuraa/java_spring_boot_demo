package uemura.java_spring_boot_demo.service.ir;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.domais.entity.PropertyEntity;
import uemura.java_spring_boot_demo.domais.transfer.PropertyRequestDto;
import uemura.java_spring_boot_demo.repository.PropertyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportPropertyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportPropertyService.class);

    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ImportPropertyService(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    public void importProperties(PropertyRequestDto propertyRequestDto) {
        LOGGER.info("Save properties");
        List<PropertyEntity> propertyEntities = propertyRequestDto.getProperties()
                .stream()
                .map(propertyDto -> modelMapper.map(propertyDto, PropertyEntity.class))
                .collect(Collectors.toList());
        propertyRepository.saveAll(propertyEntities);
    }

}
