package com.example.demo.exception;

import com.example.demo.response.ResponseStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private ResponseStatus status;
    private int code;
    private String message;
}
