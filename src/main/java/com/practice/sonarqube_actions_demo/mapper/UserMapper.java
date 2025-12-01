package com.practice.sonarqube_actions_demo.mapper;

import com.practice.sonarqube_actions_demo.model.dto.UserDto;
import com.practice.sonarqube_actions_demo.model.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

}
