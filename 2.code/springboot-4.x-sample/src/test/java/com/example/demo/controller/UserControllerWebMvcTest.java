package com.example.demo.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.UserResponse;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.service.UserService;

@WebMvcTest(controllers = UserController.class)
@Import(GlobalExceptionHandler.class)
class UserControllerWebMvcTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private UserService userService;

  @Test
  void list_shouldReturnJsonArray() throws Exception {
    UserResponse user = buildResponse(1L, "alice", "alice@example.com");
    when(userService.listUsers()).thenReturn(List.of(user));

    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].username", is("alice")))
        .andExpect(jsonPath("$[0].email", is("alice@example.com")));
  }

  @Test
  void getById_shouldReturn404WhenNotFound() throws Exception {
    when(userService.getUserById(999L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/users/{id}", 999L))
        .andExpect(status().isNotFound());
  }

  @Test
  void create_shouldReturn201AndLocation() throws Exception {
    String requestBody = """
        {
          "username": "bob",
          "password": "bob123",
          "email": "bob@example.com"
        }
        """;

    UserResponse created = buildResponse(2L, "bob", "bob@example.com");
    when(userService.createUser(any())).thenReturn(created);

    mockMvc.perform(post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/users/2"))
        .andExpect(jsonPath("$.id", is(2)))
        .andExpect(jsonPath("$.username", is("bob")))
        .andExpect(jsonPath("$.email", is("bob@example.com")));
  }

  @Test
  void create_shouldReturn400WhenValidationFails() throws Exception {
    String invalidBody = """
        {
          "username": "",
          "password": "",
          "email": "not-an-email"
        }
        """;

    mockMvc.perform(post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("Validation Failed")))
        .andExpect(jsonPath("$.details.username", is("username is required")))
        .andExpect(jsonPath("$.details.password", is("password is required")))
        .andExpect(jsonPath("$.details.email", is("email must be a valid email address")));
  }

  @Test
  void update_shouldReturn404WhenNotFound() throws Exception {
    String requestBody = """
        {
          "username": "charlie",
          "password": "charlie123",
          "email": "charlie@example.com"
        }
        """;

    when(userService.updateUser(eq(404L), any())).thenReturn(Optional.empty());

    mockMvc.perform(put("/api/users/{id}", 404L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isNotFound());
  }

  @Test
  void delete_shouldReturn204WhenDeleted() throws Exception {
    when(userService.deleteUser(1L)).thenReturn(true);

    mockMvc.perform(delete("/api/users/{id}", 1L))
        .andExpect(status().isNoContent());
  }

  private UserResponse buildResponse(Long id, String username, String email) {
    UserResponse response = new UserResponse();
    response.setId(id);
    response.setUsername(username);
    response.setEmail(email);
    return response;
  }
}
