package com.prova.service;

import org.springframework.security.core.Authentication;

public interface TokenSerivce
{
    public String generateJwt(Authentication auth);
}
