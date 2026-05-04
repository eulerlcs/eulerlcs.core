package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.User;

@Mapper
public interface UserMapper {

  @Select("SELECT id, username, email, created_at AS createdAt FROM \"user\" ORDER BY id")
  List<User> findAll();

  @Select("SELECT id, username, email, created_at AS createdAt FROM \"user\" WHERE id = #{id}")
  User findById(Long id);

  @Insert("INSERT INTO \"user\" (username, password, email) VALUES (#{username}, #{password}, #{email})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(User user);

  @Update("UPDATE \"user\" SET username = #{username}, password = #{password}, email = #{email} WHERE id = #{id}")
  int update(User user);

  @Delete("DELETE FROM \"user\" WHERE id = #{id}")
  int deleteById(Long id);
}
