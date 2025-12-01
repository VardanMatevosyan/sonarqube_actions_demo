package com.practice.sonarqube_actions_demo.service;

import com.practice.sonarqube_actions_demo.mapper.UserMapper;
import com.practice.sonarqube_actions_demo.model.dto.UserDto;
import com.practice.sonarqube_actions_demo.model.entities.User;
import com.practice.sonarqube_actions_demo.persistence.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void test_whenGetUserById_shouldReturnUserDto_whenUserExists() {
        // Arrange
        Integer userId = 1;
        User user = new User(userId, "john_doe", "john@example.com");
        UserDto userDto = new UserDto(userId, "john_doe", "john@example.com");

        when(userDao.getUserById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userService.getUserById(userId);

        // Assert
        assertThat(result).isEqualTo(userDto);
        verify(userDao).getUserById(userId);
        verify(userMapper).toDto(user);
    }

    @Test
    void test_whenGetUserById_shouldThrowNoSuchElementException_whenUserNotFound() {
        // Arrange
        Integer userId = 999;
        when(userDao.getUserById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("User not found with id: " + userId);

        verify(userDao).getUserById(userId);
        verify(userMapper, never()).toDto(any());
    }

    @Test
    void getUserById_shouldHandleNullId() {
        // Act and Assert
        assertThatThrownBy(() -> userService.getUserById(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("id must not be null");
    }
}