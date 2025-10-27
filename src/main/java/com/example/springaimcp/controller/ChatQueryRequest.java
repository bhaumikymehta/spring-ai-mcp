package com.example.springaimcp.controller;

import jakarta.validation.constraints.NotBlank;

public record ChatQueryRequest(@NotBlank(message = "Query cannot be empty.") String query) {
}
