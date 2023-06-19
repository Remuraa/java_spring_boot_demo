package uemura.java_spring_boot_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uemura.java_spring_boot_demo.domais.transfer.IrPropertiesResponseDto;
import uemura.java_spring_boot_demo.domais.transfer.IrRequestDto;
import uemura.java_spring_boot_demo.domais.transfer.IrResponseDto;
import uemura.java_spring_boot_demo.domais.transfer.PropetiesRequestDto;
import uemura.java_spring_boot_demo.service.IrService;

@RestController
@RequestMapping("api/public")
public class IrController {

    @Autowired
    private IrService irService;

    @PostMapping("/v1/ir")
    public ResponseEntity<IrResponseDto> sales(@RequestBody IrRequestDto requestDto) {
        return ResponseEntity.ok(irService.calculateIr(requestDto));
    }

    @GetMapping("/v1/ir/properties")
    public ResponseEntity<IrPropertiesResponseDto> properties(@RequestBody PropetiesRequestDto requestDto) {
        return ResponseEntity.ok(irService.getProperties(requestDto));
    }

    @PostMapping("/v1/ir/import/{pathFile}")
    public ResponseEntity<Void> importIr(@PathVariable String pathFile) {
        irService.importIr(pathFile);
        return ResponseEntity.noContent().build();
    }

}
