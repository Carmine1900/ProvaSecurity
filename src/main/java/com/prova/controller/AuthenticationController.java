package com.prova.controller;

import com.prova.dto.LoginAccessDto;
import com.prova.dto.LoginResponseDto;
import com.prova.dto.UserDto;
import com.prova.model.User;
import com.prova.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController
{
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/registerUser")
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto)
    {
        return new ResponseEntity<User>(authenticationService.registerUser(userDto), HttpStatus.OK);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginAccessDto loginAccessDto, HttpServletResponse response)
    {
        return new ResponseEntity<LoginResponseDto>(authenticationService.loginUser(loginAccessDto,response),HttpStatus.OK);
    }
}
