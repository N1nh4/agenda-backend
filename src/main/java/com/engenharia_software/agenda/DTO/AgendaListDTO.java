package com.engenharia_software.agenda.DTO;

import java.util.List;
import com.engenharia_software.agenda.interfaces.IContato;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendaListDTO {
    private List<IContato> listaContato;

    public List<IContato> getListaContato() {
        return listaContato;
    }

    public void setListaContato(List<IContato> listaContato) {
        this.listaContato = listaContato;
    }


}