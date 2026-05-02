package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;

public interface UserService {

  List<UserResponse> listUsers();

  Optional<UserResponse> getUserById(Long id);

  UserResponse createUser(UserRequest request);

  Optional<UserResponse> updateUser(Long id, UserRequest request);

  boolean deleteUser(Long id);
}
