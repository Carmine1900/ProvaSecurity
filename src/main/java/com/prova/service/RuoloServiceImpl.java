package com.prova.service;

import com.prova.dto.RuoloDto;
import com.prova.model.Ruolo;
import com.prova.repository.RuoloRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuoloServiceImpl implements RuoloService{

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Ruolo toClass(RuoloDto ruoloDto) {
        return modelMapper.map(ruoloDto,Ruolo.class);
    }

    @Override
    public RuoloDto toDto(Ruolo ruolo) {
        return modelMapper.map(ruolo,RuoloDto.class);
    }

    @Override
    public List<RuoloDto> findAll() {
        return ruoloRepository.findAll().stream().map((ruolo) -> this.toDto(ruolo)).collect(Collectors.toList());
    }

    @Override
    public Ruolo saveRuolo(RuoloDto ruoloDto) {
        return ruoloRepository.save(this.toClass(ruoloDto));
    }

    @Override
    public Ruolo updateRuolo(RuoloDto ruoloDto) {
        return ruoloRepository.save(this.toClass(ruoloDto));
    }

    @Override
    public String deleteRuoloById(Integer id) {
        if(ruoloRepository.findById(id).isPresent())
        {
            ruoloRepository.deleteById(id);
            return "Ruolo eliminato con successo";
        }else
        {
            return "Nessun ruolo corrisponde all'id inserito";
        }
    }
}
