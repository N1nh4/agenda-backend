package com.engenharia_software.agenda.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Entity
@PrimaryKeyJoinColumn(name = "agenda_id") 
public class AgendaMap extends Agenda {

    @Transient
    private Map<String, Contato> contatos = new HashMap<>();

    @Override
    public void adicionarContato(Contato contato) {
        if(contatos.containsKey(contato.getTelefone())){
            throw new IllegalArgumentException("Telefone ja cadastrado");
        }
        contatos.put(contato.getTelefone(), contato);
    }

    @Override
    public Contato atualizarContato(Contato contato) {
        if(!contatos.containsKey(contato.getTelefone())){
           throw new IllegalArgumentException("Nao foi encontrado o contato " + contato.getTelefone() + " para atualizar");
    
        }
        contatos.put(contato.getTelefone(), contato);
        return contato;

    }

    @Override
    public boolean removerContato(Contato contato) {
        return contatos.remove(contato.getTelefone())!=null;

    }

    @Override
    public Collection<Contato> getListaAgenda() {
        return contatos.values();
    }

    @Override
    public Contato getContato(String telefone) {
        return contatos.get(telefone);
    }

    @Override
    public boolean removerContatosListados() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removerContatosListados'");
    }

    @Override
    public Contato localizarContato(Contato contato) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'localizarContato'");
    }
}
