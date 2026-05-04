package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @AfterEach
  void cleanupRequestContext() {
    RequestContextHolder.resetRequestAttributes();
  }

  @Test
  void list_shouldReturnUsersFromService() {
    UserResponse user = buildResponse(1L, "alice", "alice@example.com");
    when(userService.listUsers()).thenReturn(List.of(user));

    List<UserResponse> result = userController.list();

    assertEquals(1, result.size());
    assertEquals("alice", result.get(0).getUsername());
  }

  @Test
  void getById_shouldReturn200WhenFound() {
    UserResponse user = buildResponse(1L, "alice", "alice@example.com");
    when(userService.getUserById(1L)).thenReturn(Optional.of(user));

    ResponseEntity<UserResponse> response = userController.getById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertSame(user, response.getBody());
  }

  @Test
  void getById_shouldReturn404WhenNotFound() {
    when(userService.getUserById(999L)).thenReturn(Optional.empty());

    ResponseEntity<UserResponse> response = userController.getById(999L);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
  }

  @Test
  void create_shouldReturn201WithLocationAndBody() {
    MockHttpServletRequest servletRequest = new MockHttpServletRequest();
    servletRequest.setRequestURI("/api/users");
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(servletRequest));

    UserRequest request = new UserRequest();
    request.setUsername("bob");
    request.setPassword("bob123");
    request.setEmail("bob@example.com");

    UserResponse created = buildResponse(2L, "bob", "bob@example.com");
    when(userService.createUser(request)).thenReturn(created);

    ResponseEntity<UserResponse> response = userController.create(request);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertSame(created, response.getBody());
    assertNotNull(response.getHeaders().getLocation());
    assertEquals("/api/users/2", response.getHeaders().getLocation().getPath());
  }

  @Test
  void update_shouldReturn200WhenUpdated() {
    UserRequest request = new UserRequest();
    request.setUsername("charlie");
    request.setPassword("charlie123");
    request.setEmail("charlie@example.com");

    UserResponse updated = buildResponse(3L, "charlie", "charlie@example.com");
    when(userService.updateUser(3L, request)).thenReturn(Optional.of(updated));

    ResponseEntity<UserResponse> response = userController.update(3L, request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertSame(updated, response.getBody());
  }

  @Test
  void update_shouldReturn404WhenNotFound() {
    UserRequest request = new UserRequest();
    request.setUsername("none");
    request.setPassword("none");
    request.setEmail("none@example.com");

    when(userService.updateUser(404L, request)).thenReturn(Optional.empty());

    ResponseEntity<UserResponse> response = userController.update(404L, request);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void delete_shouldReturn204WhenDeleted() {
    when(userService.deleteUser(1L)).thenReturn(true);

    ResponseEntity<Void> response = userController.delete(1L);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void delete_shouldReturn404WhenNotDeleted() {
    when(userService.deleteUser(1L)).thenReturn(false);

    ResponseEntity<Void> response = userController.delete(1L);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  private UserResponse buildResponse(Long id, String username, String email) {
    UserResponse response = new UserResponse();
    response.setId(id);
    response.setUsername(username);
    response.setEmail(email);
    return response;
  }
}
