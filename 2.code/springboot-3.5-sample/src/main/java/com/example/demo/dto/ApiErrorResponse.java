package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Standard API error response")
@Data
public class ApiErrorResponse {

  @Schema(example = "2026-04-24T10:30:00")
  private LocalDateTime timestamp;

  @Schema(example = "400")
  private Integer status;

  @Schema(example = "Validation Failed")
  private String error;

  @Schema(example = "/api/users")
  private String path;

  @Schema(example = "username already exists")
  private String message;

  @Schema(description = "Field validation errors", example = "{\"email\":\"email must be a valid email address\"}")
  private Map<String, String> details;
}
