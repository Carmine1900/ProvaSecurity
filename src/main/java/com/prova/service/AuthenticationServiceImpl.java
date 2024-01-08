package com.prova.service;

import com.prova.dto.LoginAccessDto;
import com.prova.dto.LoginResponseDto;
import com.prova.dto.UserDto;
import com.prova.model.User;
import com.prova.repository.UserRepository;
import com.prova.security.cookie.JwtCookie;
import com.prova.security.jwt.TokenSerivce;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtCookie jwtCookie;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public User registerUser(UserDto userDto)    {
        // Prendo l'oggetto bean della classe SecurityConfig e vado a codificare la password
        String password = passwordEncoder.encode(userDto.getPassword());

        User user = userService.toClass(userDto);
        user.setPassword(password);
        return userRepository.save(user);
    }
    @Override
    public LoginResponseDto loginUser(LoginAccessDto loginAccessDto, HttpServletResponse response)
    {
        try
        {
            // In base ai dati che riceve, permette di generare il token
            // Questa riga genera un'exception
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginAccessDto.getUsername(), loginAccessDto.getPassword())
            );

            // genero il cookie che conterrà il token generato in base all'oggetto auth
            ResponseCookie responseCookie = jwtCookie.generateJwtCookie(auth);

            response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

            // Trova l'utente nel db in base all'username, se lo trova mi va a prendere col metodo get() l'utente.
            User user = userRepository.findByUsername(loginAccessDto.getUsername()).get();
            user.getRuolo().setUsersList(null);
            // mi restituisce l'oggetto login con user e token
            return new LoginResponseDto(user);

        }catch(AuthenticationException authException)
        {
            return new LoginResponseDto(null);
        }
    }
}
