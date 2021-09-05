package com.lh.srb.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket adminApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2).groupName("admin");

    }
    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2).groupName("web");

    }
//    private ApiInfo adminApiInfo(){
//
//        return new ApiInfoBuilder()
//                .title("尚融宝后台管理系统-API文档")
//                .description("本文档描述了尚融宝后台管理系统接口")
//                .version("1.0")
//                .contact(new Contact("linhua", "http://atguigu.com", "55317332@qq.com"))
//                .build();
//    }
}
