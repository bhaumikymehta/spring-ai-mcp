package com.example.springaimcp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Minimal OpenAPI configuration so Swagger UI always has the metadata it needs.
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        Server localServer = new Server()
                .url("http://localhost:" + serverPort)
                .description("Local development server");

        return new OpenAPI()
                .servers(List.of(localServer))
                .info(new Info()
                        .title("Spring AI MCP API")
                        .version("1.0.0")
                        .description("""
                                API surface for interacting with the Spring AI MCP server.
                                It exposes product-aware AI assistants and supporting data endpoints.
                                """)
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("Platform Support")
                                .url("https://example.com/support")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
