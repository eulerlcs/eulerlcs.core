package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  @Override
  public List<UserResponse> listUsers() {
    return userMapper.findAll().stream().map(UserResponse::from).collect(Collectors.toList());
  }

  @Override
  public Optional<UserResponse> getUserById(Long id) {
    User user = userMapper.findById(id);
    return Optional.ofNullable(user).map(UserResponse::from);
  }

  @Override
  public UserResponse createUser(UserRequest request) {
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    user.setEmail(request.getEmail());
    user.setId(null);
    userMapper.insert(user);

    User created = userMapper.findById(user.getId());
    return UserResponse.from(created);
  }

  @Override
  public Optional<UserResponse> updateUser(Long id, UserRequest request) {
    User existing = userMapper.findById(id);
    if (existing == null) {
      return Optional.empty();
    }

    User user = new User();
    user.setId(id);
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    user.setEmail(request.getEmail());
    userMapper.update(user);

    User updated = userMapper.findById(id);
    return Optional.ofNullable(updated).map(UserResponse::from);
  }

  @Override
  public boolean deleteUser(Long id) {
    return userMapper.deleteById(id) > 0;
  }
}
