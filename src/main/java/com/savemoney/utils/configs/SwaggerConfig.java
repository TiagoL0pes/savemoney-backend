package com.savemoney.utils.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        String BASE_PACKAGE = "com.savemoney";
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(Collections.singletonList(createParameter()));
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "Tiago Lopes",
                "http://localhost:8080/swagger-ui.html",
                "email@email.com.br");

        return new ApiInfoBuilder()
                .title("Documentação da Api")
                .description("Documentação da Api")
                .version("v1")
                .termsOfServiceUrl(null)
                .contact(contact)
                .license(null)
                .licenseUrl(null)
                .extensions(Collections.emptyList())
                .build();

    }

    private Parameter createParameter() {
        return new ParameterBuilder()
                .name("Authorization")
                .description("Header para Token JWT")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
    }
}
