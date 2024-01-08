package com.prova.security.cookie;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.prova.security.jwt.TokenSerivce;
import com.prova.security.key.RsaKeyProperties;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtCookie
{
    @Value("${server.servlet.context-path}")
    private String pathForCookie;

    @Value("${security.jwtCookieName}")
    private String cookieName;

    @Autowired
    private TokenSerivce tokenSerivce;
    
    @Autowired
    private RsaKeyProperties rsaKeyProperties;

    public ResponseCookie generateJwtCookie(Authentication authentication)
    {
        String jwt = tokenSerivce.generateJwt(authentication);

        ResponseCookie cookie = ResponseCookie.from(cookieName,jwt).path(pathForCookie).maxAge(24 * 60 * 60).httpOnly(true)
                .build();

        return cookie;
    }

    public ResponseCookie cleanCookie()
    {
        ResponseCookie cookie = ResponseCookie.from(cookieName,null).path(pathForCookie).build();
        return cookie;
    }
    
    public String getJwtFromCookies(HttpServletRequest request)
    {
    	Cookie cookie = WebUtils.getCookie(request, cookieName);
    	
    	if(cookie != null)
    	{
    		return cookie.getValue();
    	}else
    	{
    		return null;
    	}
    }
    
    
//    public String getUsernameFromToken(String token)
//    {
//    	return Jwts.parserBuilder().setSigningKey(rsaKeyProperties.getPublicKey()).build()
//    			.parseClaimsJws(token).getBody().getSubject();
//    }



}
