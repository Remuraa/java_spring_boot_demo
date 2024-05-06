package uemura.java_spring_boot_demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uemura.java_spring_boot_demo.domais.transfer.declare.DeclareIrResponseDto;
import uemura.java_spring_boot_demo.service.ir.DeclareIrService;

@RestController
@RequestMapping("api/public")
@AllArgsConstructor
public class DeclareIR {

    private DeclareIrService declareIrService;

    @GetMapping("/v1/declare/ir/{year}")
    public ResponseEntity<DeclareIrResponseDto> declare(@PathVariable int year) {
        return ResponseEntity.ok(declareIrService.getDeclareIr(year));
    }
}

