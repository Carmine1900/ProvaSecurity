package com.prova.controller;

import com.prova.dto.RuoloDto;
import com.prova.dto.UserDto;
import com.prova.model.Ruolo;
import com.prova.model.User;
import com.prova.service.RuoloService;
import com.prova.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ruolo")
public class RuoloController
{
    @Autowired
    private RuoloService ruoloService;

    @GetMapping("/findAll")
    public ResponseEntity<List<RuoloDto>> findAllRoles()
    {
        return new ResponseEntity<List<RuoloDto>>(ruoloService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/saveRuolo")
    public ResponseEntity<Ruolo> saveRuolo(@RequestBody RuoloDto ruoloDto)
    {
        return new ResponseEntity<Ruolo>(ruoloService.saveRuolo(ruoloDto),HttpStatus.OK);
    }

    @PutMapping("/updateRuolo")
    public ResponseEntity<Ruolo> updateRuolo(@RequestBody RuoloDto ruoloDto)
    {
        return new ResponseEntity<Ruolo>(ruoloService.updateRuolo(ruoloDto),HttpStatus.OK);
    }

    @DeleteMapping("/deleteRuolo")
    public ResponseEntity<String> deleteRuoloById(@RequestParam("id") Integer ruoloId)
    {
        return new ResponseEntity<String>(ruoloService.deleteRuoloById(ruoloId), HttpStatus.OK);
    }
}
