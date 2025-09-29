package com.engenharia_software.agenda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engenharia_software.agenda.DTO.ContatoDTO;
import com.engenharia_software.agenda.service.ContatoService;

@RestController
@RequestMapping("/minha_agenda/contatos")
public class ContatoController {

    private ContatoService cs;

    public ContatoController(ContatoService cs) {
        this.cs = cs;
    }
    @GetMapping("/agenda/{idAgenda}")
    public ResponseEntity<List<ContatoDTO>> listarContatos(@PathVariable Long idAgenda) {
        List<ContatoDTO> contatos = cs.listarContato(idAgenda);
        return ResponseEntity.status(HttpStatus.OK).body(contatos);
    }
    
    @PostMapping
    public ResponseEntity<ContatoDTO> adicionarContato(@RequestBody ContatoDTO contato) {
        ContatoDTO contatoCriado = cs.adicionarContato(contato);
        return ResponseEntity.status(HttpStatus.CREATED).body(contatoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoDTO> atualizarContato(@RequestBody ContatoDTO contato, @PathVariable Long id) {
        ContatoDTO contatoAtualizado = cs.atualizarContato(contato, id);
        return ResponseEntity.status(HttpStatus.OK).body(contatoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerContato(@PathVariable Long id) {
        boolean contatoRemovido = cs.removerContato(id);
        if (!contatoRemovido) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    

    public ResponseEntity<String> localizarContato() {
        return null;
    }

    public ResponseEntity<String> removerContato() {
        return null;
    }

    @GetMapping("/filtrar/{string}")
    public ResponseEntity<List<ContatoDTO>> filtrarContatos(@PathVariable String string) {
        List<ContatoDTO> contatosFiltrados = cs.filtrarContatos(string);
        return ResponseEntity.status(HttpStatus.OK).body(contatosFiltrados);
    }

    @DeleteMapping("/deletar/ids")
    public ResponseEntity<String> removerContatos(@RequestBody List<Long> ids) {
        boolean removido = cs.removerContatosListados(ids);
        if (removido) {
            return ResponseEntity.ok("Contatos removidos com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Nenhum contato encontrado para os IDs fornecidos.");
        }
    }

}
