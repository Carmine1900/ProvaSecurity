package com.prova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenSerivce
{
    @Autowired
    private JwtEncoder jwtEncoder;

    @Override
    public String generateJwt(Authentication auth)
    {
        Instant now = Instant.now();


        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        // set di claim
        JwtClaimsSet claims = JwtClaimsSet.builder()
                //indica che questo back-end sta emettendo il token
                .issuer("self")
                //Con questo diciamo quando lo abbiamo emesso (istante di tempo)
                .issuedAt(now)
                //La persona verso la quale si dirige il JWt
                .subject(auth.getName())
                //informazioni contenute, passa lo scope creato sopra
                .claim("roles", scope)
                .build();
        // effettua la codifica utilizzando i valori del claim
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }
}
