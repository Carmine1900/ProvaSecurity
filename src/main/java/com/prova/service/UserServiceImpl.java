package com.prova.service;

import com.prova.dto.UserDto;
import com.prova.model.User;
import com.prova.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map((user) -> this.toDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto findUserById(Integer id) {
        return this.toDto(userRepository.findById(id).orElseThrow());
    }

    @Override
    public User saveUser(UserDto userDto) {
        return userRepository.save(this.toClass(userDto));
    }

    @Override
    public User updateUser(UserDto userDto)
    {
        return userRepository.save(this.toClass(userDto));
    }

    @Override
    public String deleteAllUser() {
        userRepository.deleteAll();
        return "Users deleted successfully";
    }

    @Override
    public String deleteUserById(Integer id)
    {
        if(userRepository.findById(id).isPresent())
        {
            userRepository.deleteById(id);
            return "User deleted successfully";
        }else
        {
            return "User with this id not found";
        }
    }

    @Override
    public User toClass(UserDto userDto)
    {
        return modelMapper.map(userDto,User.class);
    }

    @Override
    public UserDto toDto(User user)
    {
        return modelMapper.map(user,UserDto.class);
    }



}
