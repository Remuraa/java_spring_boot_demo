package uemura.java_spring_boot_demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uemura.java_spring_boot_demo.domais.transfer.ReportsResponseDto;
import uemura.java_spring_boot_demo.service.ir.ReportService;

@RestController
@RequestMapping("api/public")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/v1/report")
    public ResponseEntity<ReportsResponseDto> properties() {
        return ResponseEntity.ok(reportService.getReport());
    }
}
