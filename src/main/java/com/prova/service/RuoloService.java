package com.prova.service;

import com.prova.dto.RuoloDto;
import com.prova.dto.UserDto;
import com.prova.model.Ruolo;
import com.prova.model.User;

import java.util.List;

public interface RuoloService
{
    public Ruolo toClass(RuoloDto ruoloDto);

    public RuoloDto toDto(Ruolo ruolo);

    public List<RuoloDto> findAll();

    public Ruolo saveRuolo(RuoloDto ruoloDto);

    public Ruolo updateRuolo(RuoloDto ruoloDto);

    public String deleteRuoloById(Integer id);
}
