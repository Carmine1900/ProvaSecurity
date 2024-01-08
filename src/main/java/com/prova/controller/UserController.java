package com.prova.controller;

import com.prova.dto.LoginAccessDto;
import com.prova.dto.LoginResponseDto;
import com.prova.dto.UserDto;
import com.prova.model.User;
import com.prova.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDto>> findAllUsers()
    {
        return new ResponseEntity<List<UserDto>>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("id") Integer id)
    {
        return new ResponseEntity<UserDto>(userService.findUserById(id), HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto)
    {
        return new ResponseEntity<User>(userService.updateUser(userDto),HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUserById(@RequestParam("id") Integer userId)
    {
        return new ResponseEntity<String>(userService.deleteUserById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<String> deleteAllUsers()
    {
        return new ResponseEntity<String>(userService.deleteAllUser(), HttpStatus.OK);
    }

}
