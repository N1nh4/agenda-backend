package com.engenharia_software.agenda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engenharia_software.agenda.DTO.AgendaDTO;
import com.engenharia_software.agenda.Factory.TipoAgenda;
import com.engenharia_software.agenda.service.AgendaService;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private AgendaService as;

    public AgendaController(AgendaService as) {
        this.as = as;
    }

    @PostMapping
    public ResponseEntity<AgendaDTO> criarAgenda(@RequestBody String tipo) {
        tipo = tipo.replace("\"", "");
        
        AgendaDTO agenda = as.criarAgenda(TipoAgenda.valueOf(tipo));
        if (agenda != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(agenda);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
