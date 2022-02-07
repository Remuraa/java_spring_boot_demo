package uemura.java_spring_boot_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.domais.SalesEntity;
import uemura.java_spring_boot_demo.domais.SalesRequestDto;
import uemura.java_spring_boot_demo.repository.SalesRepository;

@Service
public class SalesService {

    @Autowired
    SalesRepository salesRepository;

    public void saveSales(SalesRequestDto salesRequestDto) {
        salesRepository.save(new SalesEntity());
    }

}
