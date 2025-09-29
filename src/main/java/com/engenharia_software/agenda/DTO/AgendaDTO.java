package com.engenharia_software.agenda.DTO;

import java.util.ArrayList;
import java.util.List;

import com.engenharia_software.agenda.Factory.TipoAgenda;
import com.engenharia_software.agenda.model.Contato;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaDTO {
   
    private Long id;

    private TipoAgenda tipo;

    private List<Contato> contatos = new ArrayList<>();
}
