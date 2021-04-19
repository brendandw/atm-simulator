package com.github.brendandw.atm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/*
    Read the ATM seed cash from the config file (the amount of each cash denomination that will be added to the ATM
    at application startup)
 */
@Component
@ConfigurationProperties(prefix = "atm")
@Getter
@Setter
public class AtmConfig {

    public static final String VALUE_KEY = "value";
    public static final String AMOUNT_KEY = "amount";

    private Map<String,Map<String,Integer>> seedcash;
}
