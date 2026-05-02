package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void listUsers_shouldReturnMappedResponses() {
    User user = buildUser(1L, "alice", "alice@example.com");
    when(userMapper.findAll()).thenReturn(List.of(user));

    List<UserResponse> result = userService.listUsers();

    assertEquals(1, result.size());
    assertEquals("alice", result.get(0).getUsername());
    assertEquals("alice@example.com", result.get(0).getEmail());
  }

  @Test
  void getUserById_shouldReturnEmptyWhenNotFound() {
    when(userMapper.findById(99L)).thenReturn(null);

    Optional<UserResponse> result = userService.getUserById(99L);

    assertTrue(result.isEmpty());
  }

  @Test
  void createUser_shouldInsertAndReturnCreatedUser() {
    UserRequest request = buildRequest("bob", "bob123", "bob@example.com");

    when(userMapper.insert(any(User.class))).thenAnswer(invocation -> {
      User arg = invocation.getArgument(0);
      arg.setId(2L);
      return 1;
    });
    when(userMapper.findById(2L)).thenReturn(buildUser(2L, "bob", "bob@example.com"));

    UserResponse result = userService.createUser(request);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userMapper).insert(captor.capture());
    User inserted = captor.getValue();
    assertEquals("bob", inserted.getUsername());
    assertEquals("bob123", inserted.getPassword());
    assertEquals("bob@example.com", inserted.getEmail());

    assertEquals(2L, result.getId());
    assertEquals("bob", result.getUsername());
  }

  @Test
  void updateUser_shouldReturnEmptyWhenUserNotFound() {
    UserRequest request = buildRequest("newname", "newpass", "new@example.com");
    when(userMapper.findById(100L)).thenReturn(null);

    Optional<UserResponse> result = userService.updateUser(100L, request);

    assertTrue(result.isEmpty());
  }

  @Test
  void updateUser_shouldUpdateAndReturnUserWhenFound() {
    UserRequest request = buildRequest("charlie", "charlie123", "charlie@example.com");
    when(userMapper.findById(3L)).thenReturn(buildUser(3L, "old", "old@example.com"));
    when(userMapper.findById(3L)).thenReturn(buildUser(3L, "old", "old@example.com"),
        buildUser(3L, "charlie", "charlie@example.com"));

    Optional<UserResponse> result = userService.updateUser(3L, request);

    verify(userMapper).update(any(User.class));
    assertTrue(result.isPresent());
    assertEquals("charlie", result.get().getUsername());
  }

  @Test
  void deleteUser_shouldReturnTrueWhenDeleted() {
    when(userMapper.deleteById(1L)).thenReturn(1);

    boolean result = userService.deleteUser(1L);

    assertTrue(result);
  }

  @Test
  void deleteUser_shouldReturnFalseWhenNotDeleted() {
    when(userMapper.deleteById(1L)).thenReturn(0);

    boolean result = userService.deleteUser(1L);

    assertFalse(result);
  }

  private UserRequest buildRequest(String username, String password, String email) {
    UserRequest request = new UserRequest();
    request.setUsername(username);
    request.setPassword(password);
    request.setEmail(email);
    return request;
  }

  private User buildUser(Long id, String username, String email) {
    User user = new User();
    user.setId(id);
    user.setUsername(username);
    user.setEmail(email);
    return user;
  }
}
