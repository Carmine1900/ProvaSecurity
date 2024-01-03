package com.prova.service;

import com.prova.dto.LoginAccessDto;
import com.prova.dto.LoginResponseDto;
import com.prova.dto.UserDto;
import com.prova.model.User;

import java.util.List;

public interface UserService
{
    public User toClass(UserDto userDto);

    public UserDto toDto(User user);

    public List<UserDto> findAll();

    public LoginResponseDto loginUser(LoginAccessDto loginAccessDto);

    public UserDto findUserById(Integer id);

    public User saveUser(UserDto userDto);

    public User updateUser(UserDto userDto);

    public String deleteUserById(Integer id);
}
