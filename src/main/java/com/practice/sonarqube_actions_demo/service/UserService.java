package com.practice.sonarqube_actions_demo.service;

import com.practice.sonarqube_actions_demo.model.dto.UserDto;

public interface UserService {

    UserDto getUserById(Integer id);

}
