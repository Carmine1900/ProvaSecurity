package com.prova.service;

import com.prova.dto.LoginAccessDto;
import com.prova.dto.LoginResponseDto;
import com.prova.dto.UserDto;
import com.prova.model.User;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService
{
    public User registerUser(UserDto userDto);

    public LoginResponseDto loginUser(LoginAccessDto loginAccessDto, HttpServletResponse response);
}
