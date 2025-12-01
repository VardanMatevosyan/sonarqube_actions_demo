package com.practice.sonarqube_actions_demo.persistence;

import com.practice.sonarqube_actions_demo.exception.DaoException;
import com.practice.sonarqube_actions_demo.model.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

@JdbcTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver"
})
class UserDaoImplTest {

    @Autowired
    private DataSource dataSource;

    private UserDaoImpl userDao;

    @BeforeEach
    void setUp() {
        this.userDao = new UserDaoImpl(dataSource);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        // Arrange
        int userId = 10;
        // Act
        var userOpt = userDao.getUserById(userId);

        // Assert
        assertThat(userOpt).isPresent();
        User user = userOpt.get();
        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getUsername()).isEqualTo("alice");
        assertThat(user.getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void getUserById_shouldReturnEmpty_whenUserDoesNotExist() {
        // Act
        var userOpt = userDao.getUserById(999);

        // Assert
        assertThat(userOpt).isEmpty();
    }

    @Test
    void getUserById_shouldReturnEmpty_whenIdIsNull() {
        // Assert and Act
        assertThatException()
                .isThrownBy(() -> userDao.getUserById(null))
                .isInstanceOf(DaoException.class)
                .withMessage("SQL error");
    }
}