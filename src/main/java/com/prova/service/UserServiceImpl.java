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
        UserDto userDto = this.toDto(userRepository.findById(id).orElseThrow());
        userDto.getRuolo().setUsersList(null);
        return userDto;
    }

    @Override
    public User saveUser(UserDto userDto)
    {
        // Prendo l'oggetto bean della classe SecurityConfig e vado a codificare la password
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Utente con questo username non trovato"));
    }


    @Override
    public LoginResponseDto loginUser(LoginAccessDto loginAccessDto)
    {
        try
        {
            // In base ai dati che riceve, permette di generare il token
            // Questa riga genera un'exception
            Authentication auht = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginAccessDto.getUsername(), loginAccessDto.getPassword())
            );

            // genera il token in base all'oggetto auth
            String token = tokenSerivce.generateJwt(auht);

            // Trova l'utente nel db in base all'username, se lo trova mi va a prendere col metodo get() l'utente.
            User user = userRepository.findByUsername(loginAccessDto.getUsername()).get();
            user.getRuolo().setUsersList(null);
            // mi restituisce l'oggetto login con user e token
            return new LoginResponseDto(user,token);

        }catch(AuthenticationException authException)
        {
            return new LoginResponseDto(null, "");
        }
    }
}
