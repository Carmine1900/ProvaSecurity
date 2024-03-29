package com.prova.security.key;

import com.prova.security.key.KeyGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
@Getter
@Setter
public class RsaKeyProperties
{
    private RSAPublicKey publicKey;

    private RSAPrivateKey privateKey;

    public RsaKeyProperties()
    {
        KeyPair keyPair = KeyGenerator.generateRsaKey();

        this.publicKey = (RSAPublicKey) keyPair.getPublic();

        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();

    }
}
