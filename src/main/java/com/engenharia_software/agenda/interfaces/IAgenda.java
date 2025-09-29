package com.engenharia_software.agenda.interfaces;

import java.util.Collection;

import com.engenharia_software.agenda.model.Contato;

public interface IAgenda {
    public String getContato(String telefone);
    public boolean adicionarContato(Contato contato);
    public boolean removerContato(Contato contato);
    public Collection<Contato> getListaAgenda();
}
