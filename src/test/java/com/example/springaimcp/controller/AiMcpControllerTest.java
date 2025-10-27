package com.example.springaimcp.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.springaimcp.service.AiMcpService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AiMcpController.class)
@Import(GlobalExceptionHandler.class)
class AiMcpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AiMcpService aiMcpService;

    @Test
    void handleQuery_returnsResponse_whenValidRequest() throws Exception {
        when(aiMcpService.getAiResponse("Hello"))
                .thenReturn("Hi there");

        ChatQueryRequest request = new ChatQueryRequest("Hello");

        mockMvc.perform(post("/api/mcp/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Hi there"));

        verify(aiMcpService).getAiResponse("Hello");
    }

    @Test
    void handleQuery_returnsBadRequest_whenQueryBlank() throws Exception {
        mockMvc.perform(post("/api/mcp/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"query\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Query cannot be empty."));
    }

    @Test
    void handleQuery_returnsBadRequest_whenQueryWhitespace() throws Exception {
        mockMvc.perform(post("/api/mcp/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"query\":\"   \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Query cannot be empty."));
    }
}
