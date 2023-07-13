package uemura.java_spring_boot_demo.service.ir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.IrBuilder;
import uemura.java_spring_boot_demo.component.ReadExcel;
import uemura.java_spring_boot_demo.converter.MovimentConverter;
import uemura.java_spring_boot_demo.domais.entity.MovimentEntity;
import uemura.java_spring_boot_demo.repository.MovimentRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImportMovimentsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportMovimentsService.class);

    @Value("${config.url-files}")
    private String urlFiles;

    private final MovimentRepository movimentRepository;

    @Autowired
    public ImportMovimentsService(IrBuilder irBuilder, MovimentRepository movimentRepository) {
        this.movimentRepository = movimentRepository;
    }

    public void importIr(String pathFile) {
        List<MovimentEntity> movimentEntities = ReadExcel.read(urlFiles + pathFile)
                .stream()
                .map(MovimentConverter::converter)
                .filter(movimentEntity -> Objects.nonNull(movimentEntity.getMoviment()))
                .collect(Collectors.toList());
        movimentRepository.saveAll(movimentEntities);
    }
}
