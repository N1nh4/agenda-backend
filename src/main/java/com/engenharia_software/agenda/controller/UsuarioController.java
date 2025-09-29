package com.engenharia_software.agenda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engenharia_software.agenda.DTO.UsuarioDTO;
import com.engenharia_software.agenda.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    private UsuarioService us;

    public UsuarioController(UsuarioService us) {
        this.us = us;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuario) {
        UsuarioDTO resposta = us.criarUsuario(usuario);

        if (resposta != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
