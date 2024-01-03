package com.prova.dto;

import com.prova.model.Ruolo;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto
{

    private Integer id;

    private String nome;

    private String cognome;

    private String username;

    private String password;

    private Ruolo ruolo;
}
