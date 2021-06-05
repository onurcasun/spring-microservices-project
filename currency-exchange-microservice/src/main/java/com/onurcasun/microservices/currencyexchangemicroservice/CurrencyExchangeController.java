package com.onurcasun.microservices.currencyexchangemicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
    
    @Autowired
    private CurrencyExchangeRepository repository;

    @Autowired
    private Environment environment;
        
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeRate(@PathVariable String from, @PathVariable String to) {
        //CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if(currencyExchange == null){
            throw new RuntimeException(String.format("Unable to find data for from: {%s}, to: {%s}", from,to));
        }

        String port = getEnvironmentPort();
        currencyExchange.setEnvironment(port);
        repository.findAll();
        
        return currencyExchange;
    }

    private String getEnvironmentPort() {
        String port = environment.getProperty("local.server.port");
        return port;
    }
}
