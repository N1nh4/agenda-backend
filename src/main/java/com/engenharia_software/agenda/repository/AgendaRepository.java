package com.engenharia_software.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.engenharia_software.agenda.model.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    
}
