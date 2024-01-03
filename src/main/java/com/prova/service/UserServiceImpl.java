package com.prova.service;

import com.prova.dto.LoginAccessDto;
import com.prova.dto.LoginResponseDto;
import com.prova.dto.UserDto;
import com.prova.model.User;
import com.prova.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenSerivce tokenSerivce;

    @Override
    public List<UserDto> findAll() {

        List<User> usersList = userRepository.findAll();

        List<UserDto> usersDtoList = new ArrayList<UserDto>();

        for(User user : usersList)
        {
            user.getRuolo().setUsersList(null);

            usersDtoList.add(this.toDto(user));
        }

        return usersDtoList;
    }

    @Override
    public UserDto findUserById(Integer id) {
        return this.toDto(userRepository.findById(id).orElseThrow());
    }

    @Override
    public User saveUser(UserDto userDto)
    {
        String password = passwordEncoder.encode(userDto.getPassword());

        User user = this.toClass(userDto);
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDto userDto)
    {
        return userRepository.save(this.toClass(userDto));
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Utente con questo username non trovato"));
    }


    @Override
    public LoginResponseDto loginUser(LoginAccessDto loginAccessDto)
    {
        try
        {
            Authentication auht = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginAccessDto.getUsername(), loginAccessDto.getPassword())
            );

            String token = tokenSerivce.generateJwt(auht);

            User user = userRepository.findByUsername(loginAccessDto.getUsername()).get();
            user.getRuolo().setUsersList(null);
            return new LoginResponseDto(user,token);

        }catch(AuthenticationException authException)
        {
            return new LoginResponseDto(null, "");
        }
    }
}
