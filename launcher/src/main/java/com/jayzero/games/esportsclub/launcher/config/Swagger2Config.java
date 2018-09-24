package com.jayzero.games.esportsclub.launcher.config;

import com.jayzero.games.esportsclub.commonutils.util.ApplicationEnvChecker;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * Swagger2Config
 *
 * @author 成至
 * @version Swagger2Config.java, v0.1
 * @date 2017/08/08
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.profiles.active}")
    private String env;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .enable(ApplicationEnvChecker.checkIfNotProductEnv(env))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.jayzero.games.esportsclub.web.controller"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Jayzero", "https://jayzero.com/", "forrestzhu.zl@alibaba-inc.com");
        return new ApiInfoBuilder()
            .title(String.format("电竞俱乐部[%s]", applicationName))
            .description("Win the World Championship Title for your club.")
            .termsOfServiceUrl("https://jazyero.com/")
            .contact(contact)
            .version("1.0")
            .build();
    }

}
