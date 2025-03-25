package uemura.java_spring_boot_demo.service.ir;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uemura.java_spring_boot_demo.domains.transfer.ReportsResponseDto;

@Service
@AllArgsConstructor
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    private final StockPriceService stockPriceService;

    public ReportsResponseDto getReport() {
        return null; //TODO
    }
}
