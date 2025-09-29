package com.engenharia_software.agenda.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Transient;


@Entity
@PrimaryKeyJoinColumn(name = "agenda_id") 
public class AgendaList extends Agenda {

    @Transient
    private List<Contato> contatos = new ArrayList<>();

    @Override
    public void adicionarContato(Contato contato) {
        for (Contato c : contatos){
            if(c.getTelefone().equals(contato.getTelefone())){
                throw new IllegalArgumentException("Telefone ja cadastrado");
            }

        }
        contatos.add(contato);

    }

    @Override
    public Contato atualizarContato(Contato contato) {
         for (int i = 0; i< contatos.size(); i++){
            Contato existente = contatos.get(i);
            if (existente.getTelefone().equals(contato.getTelefone())) {
                contatos.set(i, contato);
                return contato;
            }

        }
        throw new IllegalArgumentException("Nao foi encontrado o contato " + contato.getTelefone() + " para atualizar");


    }

    @Override
    public boolean removerContato(Contato contato) {
        return contatos.removeIf(c -> c.getTelefone().equals(contato.getTelefone()));

    }

    @Override
    public Collection<Contato> getListaAgenda() {
        return contatos;
    }

    @Override
    public Contato getContato(String telefone) {
        for (Contato c : contatos){
            if (c.getTelefone().equals(telefone)) {
                return c;
            }

        }
        throw new IllegalArgumentException("Nao foi encontrado o contato " + telefone);
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