package com.engenharia_software.agenda.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engenharia_software.agenda.DTO.AnotacaoDTO;
import com.engenharia_software.agenda.model.Anotacao;
import com.engenharia_software.agenda.service.AnotacaoService;

@RestController
@RequestMapping("/minha_agenda/anotacoes")
public class AnotacaoController {

    private final AnotacaoService anotacaoService;

    public AnotacaoController(AnotacaoService anotacaoService) {
        this.anotacaoService = anotacaoService;
    }

    @PostMapping
    public ResponseEntity<Anotacao> criarAnotacao(@RequestBody AnotacaoDTO dto) {
        Anotacao anotacao = anotacaoService.criarAnotacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(anotacao);
    }
    
    @GetMapping("/agenda/{idAgenda}")
     public ResponseEntity<List<AnotacaoDTO>> listarAnotacoesPorAgenda(@PathVariable Long idAgenda) {
        List<AnotacaoDTO> anotacoes = anotacaoService.listarAnotacoes(idAgenda);
        return ResponseEntity.status(HttpStatus.OK).body(anotacoes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAnotacao(@PathVariable UUID id) {
        anotacaoService.deletarAnotacao(id);
        return ResponseEntity.noContent().build(); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anotacao> atualizarAnotacao(
        @PathVariable UUID id,
        @RequestBody AnotacaoDTO dto) {
    Anotacao anotacaoAtualizada = anotacaoService.atualizarAnotacao(id, dto);
    return ResponseEntity.ok(anotacaoAtualizada);
}
}