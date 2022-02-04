package com.hkmc.sample.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * http://localhost:8080/swagger-ui/ API 테스트를 위한 UI를 제공한 테스트
 * */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket restAPI() {
        /*
         * TODO: DocumentationType.OAS_30 적용 테스트 필요
         * 현재 springfox 버전(3.0.0)에서 ApiModelProperty example 동작안함
         * https://github.com/springfox/springfox/issues/3509
         */
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hkmc.sample.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API for Sample")
                .version("1.0.0")
                .description("테스트용 Spring boot swagger")
                .build();
    }
}