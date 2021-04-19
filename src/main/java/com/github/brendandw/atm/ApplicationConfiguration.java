/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm;

import com.github.brendandw.atm.cashdispenser.CashDispenser;
import com.github.brendandw.atm.cashdispenser.factory.CashDispenserFactory;
import com.github.brendandw.atm.cashdispenser.sorting.HighestDenominationSorter;
import com.github.brendandw.atm.cashdispenser.sorting.SortingFactory;
import com.github.brendandw.atm.cashdispenser.strategy.BruteForceCashDispenser;
import com.github.brendandw.atm.cashdispenser.strategy.DPCashDispenser;
import com.github.brendandw.atm.delegate.AtmDelegate;
import com.github.brendandw.atm.model.AtmModel;
import com.github.brendandw.atm.model.CashController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author brendandw
 */
@EnableSwagger2
@Configuration
public class ApplicationConfiguration  {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .apiInfo(apiInfo())
          .select()
          .apis(RequestHandlerSelectors.any())
          .paths(PathSelectors.any())
          .build();
    }

     private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ATM Cash Dispenser API")
                .description("ATM Cash Dispenser API with Swagger")
                .license("Apache License Version 2.0")
                .version("2.0")
                .build();
     }
}
