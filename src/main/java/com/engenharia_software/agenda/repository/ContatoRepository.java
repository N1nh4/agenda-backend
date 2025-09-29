package com.engenharia_software.agenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.engenharia_software.agenda.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    List<Contato> findByAgendaId(Long idAgenda);
    List<Contato> findByNomeContainingIgnoreCaseOrTelefoneContainingIgnoreCase(String nome, String telefone);
}
