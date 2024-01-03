package com.prova.dto;

import com.prova.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RuoloDto
{
    private Integer id;

    private String authority;
}
