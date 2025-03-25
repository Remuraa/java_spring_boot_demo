package uemura.java_spring_boot_demo.domains.transfer.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockData {

    @JsonProperty("Meta Data")
    private MetaData metaData;
    @JsonProperty("Time Series (Daily)")
    private Map<String, DailyData> timeSeries;
}

