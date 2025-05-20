//package com.example.springaimcp.config; // Create this package if it doesn't exist
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.servers.Server;
//
///**
// * Configuration class for Springdoc OpenAPI (Swagger UI).
// * This class customizes the information displayed on the Swagger UI page.
// */
//@Configuration
//public class OpenApiConfig {
//
//    // You can inject values from application.properties if needed
//    @Value("${server.port:8080}") // Default to 8080 if not set
//    private String serverPort;
//
//    // Optional: If your app is deployed behind a proxy or has a specific base path
//    // @Value("${springdoc.server.base-url:http://localhost}")
//    // private String serverBaseUrl;
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        // Define server URL. This helps Swagger UI make correct calls.
//        // For local development, it's usually localhost.
//        // For deployed environments, you might want to make this configurable.
//        Server localServer = new Server();
//        localServer.setUrl("http://localhost:" + serverPort); // Uses the injected server.port
//        localServer.setDescription("Local development server");
//
//        // Example for a production server (you might have other ways to configure this)
//        // Server productionServer = new Server();
//        // productionServer.setUrl("https://api.yourcompany.com"); // Replace with your
//        // actual prod URL
//        // productionServer.setDescription("Production server");
//
//        return new OpenAPI()
//                .servers(List.of(localServer /* , productionServer */)) // Add more servers if needed
//                .info(new Info()
//                        .title("Spring AI MCP API")
//                        .version("1.0.0")
//                        .description(
//                                """
//                                        This API provides access to an AI-powered Mission Critical Platform (MCP) server.
//                                        It uses data from a PostgreSQL database to provide context-aware responses to user queries.
//                                        Key features include product information retrieval and natural language interaction.
//                                        """)
//                        .termsOfService("http://example.com/terms-of-service") // Replace with your ToS URL
//                        .contact(new Contact()
//                                .name("Support Team")
//                                .url("http://example.com/support") // Replace with your support URL
//                                .email("support@example.com")) // Replace with your support email
//                        .license(new License()
//                                .name("Apache License Version 2.0")
//                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
//    }
//}
