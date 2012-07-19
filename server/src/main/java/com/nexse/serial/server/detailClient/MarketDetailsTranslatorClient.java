package com.nexse.serial.server.detailClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class MarketDetailsTranslatorClient {
    static final Logger logger = LoggerFactory.getLogger(MarketDetailsTranslatorClient.class);

    private  String url;
    private RestTemplate restClient;

    private static MarketDetailsTranslatorClient instance;

    public static MarketDetailsTranslatorClient getInstance() throws Exception {
        if (instance == null) {
           throw new Exception("MarketDetailsTranslatorClient is not initialized");
        }
        return instance;
    }

    public static void initialize(String url) {

        instance = new MarketDetailsTranslatorClient();
        instance.url = url;
        instance.restClient = new RestTemplate();
    }

    private MarketDetailsTranslatorClient() {
    }

    public String getMarketDetailsByCode(String code) {
        try {
            logger.debug(" Searching for market with code : {}  ............",code);
          ResponseEntity<String> responseEntity = restClient.getForEntity(url,String.class,code);
            logger.debug(" MarketDetails found: {} ",responseEntity.getBody() );
            return responseEntity.getBody();

        } catch (RestClientException e) {
            logger.error(" Market details with code {} not found ",code);
            return null;
        }


    }

   /* public static void main(String[] args) {
        MarketDetailsTranslatorClient c = new MarketDetailsTranslatorClient();
        c.restClient = new RestTemplate();
        c.url = "http://localhost:8080/stub/marketdetails/{code}";
        System.out.println(c.getMarketDetailsByCode("12AA"));
    }*/

}
