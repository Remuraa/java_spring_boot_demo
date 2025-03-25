package uemura.java_spring_boot_demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uemura.java_spring_boot_demo.domains.transfer.IrPropertiesResponseDto;
import uemura.java_spring_boot_demo.domains.transfer.ProfitCalculationResponseDto;
import uemura.java_spring_boot_demo.domains.transfer.PropertyRequestDto;
import uemura.java_spring_boot_demo.service.ir.GetProfitCalculationService;
import uemura.java_spring_boot_demo.service.ir.GetPropertyService;
import uemura.java_spring_boot_demo.service.ir.ImportMovimentsService;
import uemura.java_spring_boot_demo.service.ir.ImportPropertyService;

@RestController
@RequestMapping("api/public")
@AllArgsConstructor
public class IrController {

    private ImportMovimentsService importMovimentsService;
    private GetPropertyService getPropertyService;
    private ImportPropertyService importPropertyService;
    private final GetProfitCalculationService getProfitCalculationService;

    @GetMapping("/v1/ir/properties/{year}")
    public ResponseEntity<IrPropertiesResponseDto> properties(@PathVariable int year) {
        return ResponseEntity.ok(getPropertyService.getProperties(year));
    }

    @GetMapping("/v1/ir/profitCalculation/{year}")
    public ResponseEntity<ProfitCalculationResponseDto> profitCalculation(@PathVariable int year) {
        return ResponseEntity.ok(getProfitCalculationService.getProfitCalculation(year));
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

    @DeleteMapping("/v1/ir")
    public ResponseEntity<Void> deleteProperty() {
        importPropertyService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
