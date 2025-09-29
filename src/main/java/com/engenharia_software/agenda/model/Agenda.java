package com.engenharia_software.agenda.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.engenharia_software.agenda.Factory.TipoAgenda;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoAgenda tipo;

    @OneToMany(mappedBy = "agenda", fetch = FetchType.EAGER)
    private List<Contato> contatos = new ArrayList<>();

    @OneToMany(mappedBy = "agenda", fetch = FetchType.EAGER)
    private List<Anotacao> anotacoes = new ArrayList<>();
    
    public abstract void adicionarContato(Contato contato);
    public abstract Contato getContato(String telefone);
    public abstract Contato atualizarContato(Contato contato);
    public abstract boolean removerContato(Contato contato);
    public abstract Collection<Contato> getListaAgenda();
    public abstract boolean removerContatosListados();
    public abstract Contato localizarContato(Contato contato);
}