package com.sporty.betting.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Swagger/OpenAPI documentation.
 * <p>
 * Provides metadata such as title, description, and contact info for the API documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bettingServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("F1 Betting API")
                        .description("F1 betting service System.")
                        .version("1.0.0")
                        .contact(new Contact().name("Support Team").email("support@example.com")))
                .addServersItem(new Server().url("http://localhost:8080").description("Local Development"));
    }

}
