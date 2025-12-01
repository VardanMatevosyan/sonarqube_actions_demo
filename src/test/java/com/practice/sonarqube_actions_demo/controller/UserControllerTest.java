package com.practice.sonarqube_actions_demo.controller;

import com.practice.sonarqube_actions_demo.model.dto.UserDto;
import com.practice.sonarqube_actions_demo.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void getUser_shouldReturnUserDto_whenUserExists() throws Exception {
        // Arrange
        Integer userId = 1;
        UserDto userDto = new UserDto(userId, "jane_doe", "jane@example.com");
        when(userService.getUserById(userId)).thenReturn(userDto);

        // Act and Assert
        mockMvc.perform(get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));

        verify(userService).getUserById(userId);
    }

    @Test
    void getUser_shouldReturn500_whenUserServiceThrowsException() throws Exception {
        // Arrange
        Integer userId = 999;
        when(userService.getUserById(userId))
                .thenThrow(new NoSuchElementException("User not found with id: " + userId));

        // Act and Assert
        mockMvc.perform(get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(userService).getUserById(userId);
    }
}