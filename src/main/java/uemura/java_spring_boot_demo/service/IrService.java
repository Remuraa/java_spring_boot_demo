package uemura.java_spring_boot_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.component.ReadExcel;
import uemura.java_spring_boot_demo.domais.transfer.IrExceltDto;
import uemura.java_spring_boot_demo.domais.transfer.IrRequestDto;

import java.util.List;

@Service
public class IrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IrService.class);

    public void calculateIr(IrRequestDto irDto) {
        try {
            ReadExcel.read(irDto.getUrlPath());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
