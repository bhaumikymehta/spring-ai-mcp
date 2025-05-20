package com.example.springaimcp.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springaimcp.service.AiMcpService;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/mcp") // Base path for all endpoints in this controller
public class AiMcpController {

    private static final Logger logger = LoggerFactory.getLogger(AiMcpController.class);
    private final AiMcpService aiMcpService;

    public AiMcpController(AiMcpService aiMcpService) {
        this.aiMcpService = aiMcpService;
    }

    /**
     * Endpoint to get an AI response based on a user query.
     * This version uses the service method that includes product context.
     *
     * Example POST request body (JSON):
     * {
     * "query": "Tell me about your laptops"
     * }
     */
    @PostMapping("/query")
    public ResponseEntity<Map<String, String>> handleQuery(@RequestBody Map<String, String> payload) {
        String userQuery = payload.get("query");
        logger.info("Received query request: {}", userQuery);

        if (userQuery == null || userQuery.trim().isEmpty()) {
            logger.warn("Empty query received.");
            return ResponseEntity.badRequest().body(Map.of("error", "Query cannot be empty."));
        }
        String aiResponse = aiMcpService.getAiResponse(userQuery);
        return ResponseEntity.ok(Map.of("response", aiResponse));
    }

    /**
     * Simpler endpoint for testing, doesn't explicitly pass DB context in the same
     * way.
     * Example GET request: /api/mcp/simple-query?text=Hello
     */
    @GetMapping("/simple-query")
    public ResponseEntity<Map<String, String>> handleSimpleQuery(@RequestParam("text") String text) {
        logger.info("Received simple query request: {}", text);
        if (text == null || text.trim().isEmpty()) {
            logger.warn("Empty simple query text received.");
            return ResponseEntity.badRequest().body(Map.of("error", "Query text cannot be empty."));
        }
        String aiResponse = aiMcpService.getSimpleAiResponse(text);
        return ResponseEntity.ok(Map.of("response", aiResponse));
    }
}
