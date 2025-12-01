package com.practice.sonarqube_actions_demo.persistence;

import com.practice.sonarqube_actions_demo.model.entities.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(Integer id);

}
