package com.cargo.solutions.infraestructure.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private String message;
}

