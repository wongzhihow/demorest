package com.example.demorest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class CurrencyExchangeRateService {

    RestClient restClient = RestClient.create();

    private static final String url = "https://api.bnm.gov.my/public/exchange-rate";

    private static final Map<String, String> headersMap = Map.of("Accept", "application/vnd.BNM.API.v1+json") ;


    public String getExchangeRate() {

        var response = restClient.get().uri(url)
                .headers(httpHeaders -> httpHeaders.setAll(headersMap))
                .retrieve().body(String.class
                );

        return response;
    }


}
