package com.prova.security.cookie;


import com.prova.model.User;
import com.prova.security.jwt.TokenSerivce;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtCookie
{
    @Value("${server.servlet.context-path}")
    private String path;

    @Value("${security.jwtCookieName}")
    private String cookieName;

    @Autowired
    private TokenSerivce tokenSerivce;

    public ResponseCookie generateJwtCookie(Authentication authentication)
    {
        String jwt = tokenSerivce.generateJwt(authentication);

        ResponseCookie cookie = ResponseCookie.from(cookieName,jwt).path("/user").maxAge(24 * 60 * 60).httpOnly(true)
                .build();

        return cookie;
    }

    public ResponseCookie cleanCookie()
    {
        ResponseCookie cookie = ResponseCookie.from(cookieName,null).path("/user").build();
        return cookie;
    }



}
