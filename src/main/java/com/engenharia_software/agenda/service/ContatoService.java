package com.engenharia_software.agenda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.engenharia_software.agenda.model.Agenda;
import com.engenharia_software.agenda.model.Contato;

import com.engenharia_software.agenda.DTO.ContatoDTO;
import com.engenharia_software.agenda.repository.AgendaRepository;
import com.engenharia_software.agenda.repository.ContatoRepository;

import jakarta.transaction.Transactional;

@Service
public class ContatoService {

    private final ContatoRepository cr;
    private final AgendaRepository ar;
    
    public ContatoService(ContatoRepository cr, AgendaRepository ar) {
        this.cr = cr;
        this.ar = ar;
    }

    public List<ContatoDTO> listarContato(Long idAgenda) {
        List<Contato> contatos = cr.findByAgendaId(idAgenda); // retorna lista vazia se não houver

        System.out.println("ID AGENDA: " + idAgenda);
        System.out.println("CONTATOS: " + contatos);

        if (contatos.isEmpty()) {
            return List.of(); // evita retornar null
        }

        // mapear os dados do Contato para o DTO
        List<ContatoDTO> contatosDTO = contatos.stream()
            .map(contato -> new ContatoDTO(contato))
            .toList();

        return contatosDTO;
    }
    
    @Transactional
    public ContatoDTO adicionarContato(ContatoDTO contato) {

        Agenda a = ar.findById(contato.getIdAgenda()).get();

        Contato c = new Contato();
        c.setAgenda(a);
        c.setNome(contato.getNome());
        c.setTelefone(contato.getTelefone());

        Contato contatoCriado = cr.save(c);
        ContatoDTO contatoCriadoDTO = new ContatoDTO(contatoCriado);

        return contatoCriadoDTO;
    }

    public ContatoDTO atualizarContato(ContatoDTO contato, Long id) {
        Contato contatoAtualizado = cr.findById(id).get();
        contatoAtualizado.setNome(contato.getNome());
        contatoAtualizado.setTelefone(contato.getTelefone());
        Contato contatoAtualizadoCriado = cr.save(contatoAtualizado);
        ContatoDTO contatoAtualizadoCriadoDTO = new ContatoDTO(contatoAtualizadoCriado);
        return contatoAtualizadoCriadoDTO;
    }

    public boolean removerContato(Long id) {
        if (!cr.existsById(id)) {
            cr.deleteById(id);
            return false;
        }

        cr.deleteById(id);
        return true;
    }

    public void localizarContato(ContatoDTO contato) {

    }


    public void Contato(ContatoDTO contato) {

    }

    public List<ContatoDTO> filtrarContatos(String string) {
        List<Contato> contatos = cr.findByNomeContainingIgnoreCaseOrTelefoneContainingIgnoreCase(string, string);
        
        if (contatos.isEmpty()) {
            return List.of(); // evita retornar null
        }

        // mapear os dados do Contato para o DTO
        List<ContatoDTO> contatosDTO = contatos.stream()
            .map(contato -> new ContatoDTO(contato))
            .toList();

        return contatosDTO;
    }
    
    @Transactional
    public boolean removerContatosListados(List<Long> ids) {
        List<Contato> contatosParaRemover = cr.findAllById(ids);
        if (contatosParaRemover.isEmpty()) {
            return false; // Nenhum contato encontrado para os IDs fornecidos
        }
        cr.deleteAll(contatosParaRemover);
        return true;
    }
    
}