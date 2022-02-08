package uemura.java_spring_boot_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uemura.java_spring_boot_demo.domais.SalesRequestDto;
import uemura.java_spring_boot_demo.service.SalesService;

@RestController
@RequestMapping("api/public")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @PostMapping("/v1/sales")
    public ResponseEntity sales(@RequestBody SalesRequestDto requestDto) {
        salesService.saveSales(requestDto);
        return ResponseEntity.noContent().build();
    }

}
