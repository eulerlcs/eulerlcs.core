package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User API response without sensitive fields")
@Data
public class UserResponse {

  @Schema(example = "1")
  private Long id;

  @Schema(example = "alice")
  private String username;

  @Schema(example = "alice@example.com")
  private String email;

  @Schema(example = "2026-04-24T10:30:00")
  private LocalDateTime createdAt;

  public static UserResponse from(User user) {
    UserResponse response = new UserResponse();
    response.setId(user.getId());
    response.setUsername(user.getUsername());
    response.setEmail(user.getEmail());
    response.setCreatedAt(user.getCreatedAt());
    return response;
  }
}
