package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dto.ApiErrorResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "CRUD operations for user resources")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  @Operation(summary = "List users")
  @ApiResponse(responseCode = "200", description = "Users retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
  public List<UserResponse> list() {
    return userService.listUsers();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get user by id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<UserResponse> getById(@Parameter(description = "User id") @PathVariable Long id) {
    return userService.getUserById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  @Operation(summary = "Create user")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = UserResponse.class))),
      @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      @ApiResponse(responseCode = "409", description = "Username already exists", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
    UserResponse created = userService.createUser(request);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(created.getId())
        .toUri();

    return ResponseEntity.created(location).body(created);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update user")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = UserResponse.class))),
      @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "409", description = "Username already exists", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  public ResponseEntity<UserResponse> update(@Parameter(description = "User id") @PathVariable Long id,
      @Valid @RequestBody UserRequest request) {
    return userService.updateUser(id, request)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete user")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "User deleted"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<Void> delete(@Parameter(description = "User id") @PathVariable Long id) {
    if (!userService.deleteUser(id)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }
}