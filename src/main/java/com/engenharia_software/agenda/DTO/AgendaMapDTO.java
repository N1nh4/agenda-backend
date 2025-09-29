package com.engenharia_software.agenda.DTO;

import java.util.HashMap;
import java.util.Map;

import com.engenharia_software.agenda.interfaces.IContato;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendaMapDTO {
    
    private Map<String, IContato> agenda = new HashMap<>();

    public Map<String, IContato> getAgenda() {
        return agenda;
    }

    public void setAgenda(Map<String, IContato> agenda) {
        this.agenda = agenda;
    }

    
}