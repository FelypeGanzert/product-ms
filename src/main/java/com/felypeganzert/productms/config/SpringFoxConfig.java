package com.felypeganzert.productms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.felypeganzert.productms"))
                .paths(PathSelectors.regex("/products.*"))
                .build()
                .apiInfo(metaInfo());                                          
    }

    private ApiInfo metaInfo() {
        return new ApiInfoBuilder()
                .title("Product MS")
                .description("API REST desenvolvida para vaga de trabalho na Compasso Uol")
                .version("1.0.0")
                .license("MIT License")
                .licenseUrl("https://github.com/FelypeGanzert/product-ms/blob/main/LICENSE")
                .contact(new Contact("Felype Ganzert", "https://www.felypeganzert.com", "felypeganzert@gmail.com"))
                .build();
    }
}


