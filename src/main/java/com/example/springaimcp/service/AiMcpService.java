package com.example.springaimcp.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springaimcp.model.Product;
import com.example.springaimcp.repository.ProductRepository;

@Service // Marks this class as a Spring service component
public class AiMcpService {

    private static final Logger logger = LoggerFactory.getLogger(AiMcpService.class);
    private static final String PRODUCT_PROMPT_TEMPLATE = """
            You are an AI assistant for an e-commerce store.
            You have access to the following product information from our database:
            --- START OF PRODUCT DATA ---
            {products}
            --- END OF PRODUCT DATA ---

            A customer has asked the following question:
            "{query}"

            Please answer the customer's question based ONLY on the provided product information.
            If the information to answer the question is not available in the product data,
            clearly state that you don't have specific details on that from the provided data.
            Do not invent information or use external knowledge beyond this product data.
            Keep answers concise but friendly.
            """;

    private final ChatClient chatClient; // Spring AI ChatClient for interacting with the AI model
    private final ProductRepository productRepository; // Repository for accessing product data
    private final PromptTemplate productPromptTemplate;

    /**
     * Constructor for AiMcpService.
     *
     * @param chatClientBuilder The ChatClient.Builder to build the ChatClient.
     * @param productRepository The repository for product data.
     */
    public AiMcpService(ChatClient.Builder chatClientBuilder, ProductRepository productRepository) {
        this.chatClient = chatClientBuilder.build();
        this.productRepository = productRepository;
        this.productPromptTemplate = new PromptTemplate(PRODUCT_PROMPT_TEMPLATE);
    }

    /**
     * Processes a user query using AI, with context from the product database.
     *
     * @param userQuery The query from the user.
     * @return The AI-generated response.
     */
    @Transactional(readOnly = true)
    public String getAiResponse(String userQuery) {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            logger.warn("No products found in the database to provide as context.");
        }

        String productContext = products.stream()
                .map(Product::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        if (productContext.isEmpty()) {
            productContext = "No product data available.";
        }

        Prompt prompt = productPromptTemplate.create(Map.of(
                "products", productContext,
                "query", userQuery));

        logger.info("Sending prompt to AI. Instruction count: {}", prompt.getInstructions().size());
        if (prompt.getInstructions().size() > 3000) {
            logger.warn("Prompt is very long ({} characters), which might impact performance or hit token limits.",
                    prompt.getInstructions().size());
        }

        // Call the AI model and get the response.
        try {
            ChatClient.CallResponseSpec response = chatClient.prompt(prompt).call();
            String aiContent = response.content();
            logger.info("Received AI response."); // Avoid logging potentially large AI content directly unless needed
            // for debugging
            return aiContent;
        } catch (Exception e) {
            logger.error("Error calling AI service: " + e.getMessage(), e);
            return "Sorry, I encountered an error trying to process your request. Please try again later.";
        }
    }

    /**
     * A simpler AI interaction without explicit database context in the prompt.
     *
     * @param userQuery The query from the user.
     * @return The AI-generated response.
     */
    public String getSimpleAiResponse(String userQuery) {
        logger.info("Processing simple AI query: {}", userQuery);
        try {
            return chatClient.prompt()
                    .user(userQuery) // Simple user message
                    .call()
                    .content(); // Get the content of the response
        } catch (Exception e) {
            logger.error("Error calling AI service (simple query): " + e.getMessage(),
                    e);
            return "Sorry, I couldn't process that simple request due to an error.";
        }
    }
}
