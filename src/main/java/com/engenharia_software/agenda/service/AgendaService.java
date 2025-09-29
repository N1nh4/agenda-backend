package com.engenharia_software.agenda.service;

import org.springframework.stereotype.Service;

import com.engenharia_software.agenda.DTO.AgendaDTO;
import com.engenharia_software.agenda.Factory.FabricaAgenda;
import com.engenharia_software.agenda.Factory.TipoAgenda;
import com.engenharia_software.agenda.model.Agenda;
import com.engenharia_software.agenda.repository.AgendaRepository;

@Service
public class AgendaService {

    private AgendaRepository ar;

    public AgendaService(AgendaRepository ar) {
        this.ar = ar;
    }
    
    public AgendaDTO criarAgenda(TipoAgenda tipo) {

        Agenda agenda = FabricaAgenda.getInstancia().criarAgenda(tipo);
        agenda.setTipo(tipo);
        Agenda agendaSalva = ar.save(agenda);

        if (agendaSalva == null) {
            return null;
        }

        AgendaDTO agendaDTO = new AgendaDTO();
        agendaDTO.setId(agendaSalva.getId());
        agendaDTO.setTipo(agendaSalva.getTipo());

        return agendaDTO;
    }
}