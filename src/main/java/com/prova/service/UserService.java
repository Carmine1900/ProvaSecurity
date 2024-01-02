package com.prova.service;

import com.prova.dto.UserDto;
import com.prova.model.User;

import java.util.List;

public interface UserService
{
    public User toClass(UserDto userDto);

    public UserDto toDto(User user);

    public List<UserDto> findAll();

    public UserDto findUserById(Integer id);

    public User saveUser(UserDto userDto);

    public User updateUser(UserDto userDto);

    public String deleteAllUser();

    public String deleteUserById(Integer id);
}
