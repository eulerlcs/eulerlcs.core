package com.example.demo.mapper;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.example.demo.entity.User;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserMapperTest {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void findAll_shouldReturnUsersOrderedById() {
    List<User> result = userMapper.findAll();

    assertThat(result).hasSize(3);
    assertThat(result.get(0).getId()).isNotNull();
    assertThat(result.get(0).getUsername()).isEqualTo("alice");
    assertThat(result.get(0).getEmail()).isEqualTo("alice@example.com");

    assertThat(result.get(1).getId()).isNotNull();
    assertThat(result.get(1).getUsername()).isEqualTo("bob");
    assertThat(result.get(1).getEmail()).isEqualTo("bob@example.com");

    assertThat(result.get(2).getId()).isNotNull();
    assertThat(result.get(2).getUsername()).isEqualTo("charlie");
    assertThat(result.get(2).getEmail()).isEqualTo("charlie@example.com");
  }

  @Test
  void findById_shouldReturnUserWhenExists() {
    Long charlieId = getIdByUsername("charlie");
    User result = userMapper.findById(charlieId);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(charlieId);
    assertThat(result.getUsername()).isEqualTo("charlie");
    assertThat(result.getEmail()).isEqualTo("charlie@example.com");
    assertThat(result.getCreatedAt()).isNotNull();
  }

  @Test
  void findById_shouldReturnNullWhenNotExists() {
    User result = userMapper.findById(9999L);

    assertThat(result).isNull();
  }

  @Test
  void insert_shouldPersistUserAndSetGeneratedId() {
    User user = buildUser(null, "david", "david123", "david@example.com");

    int affected = userMapper.insert(user);

    assertThat(affected).isEqualTo(1);
    assertThat(user.getId()).isNotNull();

    User persisted = userMapper.findById(user.getId());
    assertThat(persisted).isNotNull();
    assertThat(persisted.getUsername()).isEqualTo("david");
    assertThat(persisted.getEmail()).isEqualTo("david@example.com");
  }

  @Test
  void update_shouldModifyExistingUserAndReturnAffectedRows() {
    Long bobId = getIdByUsername("bob");
    User toUpdate = buildUser(bobId, "bob-updated", "bob456", "bob.new@example.com");

    int affected = userMapper.update(toUpdate);

    assertThat(affected).isEqualTo(1);

    String username = jdbcTemplate.queryForObject(
        "SELECT username FROM \"user\" WHERE id = ?",
        String.class,
        bobId);
    String password = jdbcTemplate.queryForObject(
        "SELECT password FROM \"user\" WHERE id = ?",
        String.class,
        bobId);
    String email = jdbcTemplate.queryForObject(
        "SELECT email FROM \"user\" WHERE id = ?",
        String.class,
        bobId);

    assertThat(username).isEqualTo("bob-updated");
    assertThat(password).isEqualTo("bob456");
    assertThat(email).isEqualTo("bob.new@example.com");
  }

  @Test
  void update_shouldReturnZeroWhenUserNotExists() {
    User toUpdate = buildUser(404L, "ghost", "ghost123", "ghost@example.com");

    int affected = userMapper.update(toUpdate);

    assertThat(affected).isEqualTo(0);
  }

  @Test
  void deleteById_shouldRemoveUserAndReturnAffectedRows() {
    Long aliceId = getIdByUsername("alice");
    int affected = userMapper.deleteById(aliceId);

    assertThat(affected).isEqualTo(1);

    Integer count = jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM \"user\" WHERE id = ?",
        Integer.class,
        aliceId);
    assertThat(count).isZero();
  }

  @Test
  void deleteById_shouldReturnZeroWhenUserNotExists() {
    int affected = userMapper.deleteById(123456L);

    assertThat(affected).isEqualTo(0);
  }

  private User buildUser(Long id, String username, String password, String email) {
    User user = new User();
    user.setId(id);
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);
    return user;
  }

  private Long getIdByUsername(String username) {
    return jdbcTemplate.queryForObject(
        "SELECT id FROM \"user\" WHERE username = ?",
        Long.class,
        username);
  }
}
