package uemura.java_spring_boot_demo.service.ir;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uemura.java_spring_boot_demo.domains.transfer.stock.StockData;

@Service
public class StockPriceService {

    @Value("${alphavantage.apikey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public StockPriceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public StockData getStockPrice(String stockCode) {
        String url = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s.SA&interval=5min&apikey=%s", stockCode, apiKey);
        return restTemplate.getForObject(url, StockData.class);
    }

}
