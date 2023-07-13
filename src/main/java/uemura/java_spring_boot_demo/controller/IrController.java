package uemura.java_spring_boot_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uemura.java_spring_boot_demo.domais.transfer.IrPropertiesResponseDto;
import uemura.java_spring_boot_demo.domais.transfer.PropertyRequestDto;
import uemura.java_spring_boot_demo.service.ir.GetPropertyService;
import uemura.java_spring_boot_demo.service.ir.ImportMovimentsService;
import uemura.java_spring_boot_demo.service.ir.ImportPropertyService;

@RestController
@RequestMapping("api/public")
public class IrController {

    private ImportMovimentsService importMovimentsService;
    private GetPropertyService getPropertyService;
    private ImportPropertyService importPropertyService;

    @Autowired
    public IrController(ImportMovimentsService importMovimentsService, GetPropertyService getPropertyService, ImportPropertyService importPropertyService) {
        this.importMovimentsService = importMovimentsService;
        this.getPropertyService = getPropertyService;
        this.importPropertyService = importPropertyService;
    }

    @GetMapping("/v1/ir/properties/{year}")
    public ResponseEntity<IrPropertiesResponseDto> properties(@PathVariable int year) {
        return ResponseEntity.ok(getPropertyService.getProperties(year));
    }

    @PostMapping("/v1/ir/import/{pathFile}")
    public ResponseEntity<Void> importIr(@PathVariable String pathFile) {
        importMovimentsService.importIr(pathFile);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/v1/ir/property")
    public ResponseEntity<Void> saveProperty(@RequestBody PropertyRequestDto propertyRequestDto) {
        importPropertyService.importProperties(propertyRequestDto);
        return ResponseEntity.noContent().build();
    }

}
