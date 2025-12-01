package com.practice.sonarqube_actions_demo.service;

import com.practice.sonarqube_actions_demo.mapper.UserMapper;
import com.practice.sonarqube_actions_demo.model.dto.UserDto;
import com.practice.sonarqube_actions_demo.persistence.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserDao userDao;
    final UserMapper userMapper;

    public UserDto getUserById(Integer id) {
        requireNonNull(id, "id must not be null");
        return userDao.getUserById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

}
