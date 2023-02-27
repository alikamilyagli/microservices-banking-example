package com.cenoa.transaction.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi transactionApi() {
        return GroupedOpenApi.builder()
                .group("transaction")
                .pathsToMatch("/v1/transaction", "/v1/transaction/**")
                .build();
    }

    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Cenoa Bank - Transaction Service")
                        .description("")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("https://cenoa.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Cenoa Bank Wiki Documentation")
                        .url("https://cenoa.com"));
    }

}
