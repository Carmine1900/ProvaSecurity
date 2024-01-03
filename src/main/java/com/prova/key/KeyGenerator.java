package com.prova.key;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

//Classe per generare chiavi per il JWT
public class KeyGenerator
{
    //Genero le coppie di chiavi che poi mi serviranno per crittogafare il jwt
    public static KeyPair generateRsaKey()
    {
        KeyPair keyPair = null;

        try
        {
            //RSA rappresenta l'algoritmo di codifica del mio jwt
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //Generiamo la size della chiave in questo caso 2048 bit
            keyPairGenerator.initialize(2048);
            //Ora la generiamo
            keyPair = keyPairGenerator.generateKeyPair();
        }catch(Exception exception)
        {
            exception.printStackTrace();
        }

        return keyPair;
    }
}