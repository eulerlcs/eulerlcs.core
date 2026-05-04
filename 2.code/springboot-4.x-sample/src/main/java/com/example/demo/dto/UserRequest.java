package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Payload used to create or update a user")
@Data
public class UserRequest {

  @Schema(description = "Unique username", example = "alice")
  @NotBlank(message = "username is required")
  @Size(max = 100, message = "username must be at most 100 characters")
  private String username;

  @Schema(description = "User password", example = "alice123")
  @NotBlank(message = "password is required")
  @Size(max = 255, message = "password must be at most 255 characters")
  private String password;

  @Schema(description = "User email address", example = "alice@example.com")
  @Email(message = "email must be a valid email address")
  @Size(max = 255, message = "email must be at most 255 characters")
  private String email;
}
