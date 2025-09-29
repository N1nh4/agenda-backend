package com.engenharia_software.agenda.DTO;

import com.engenharia_software.agenda.Factory.TipoAgenda;
import com.engenharia_software.agenda.model.Contato;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContatoDTO {
    private Long id;
    private Long idAgenda;
    private String nome;
    private String telefone;

    public ContatoDTO(Contato contato) {
        this.id = contato.getId();
        this.nome = contato.getNome();
        this.telefone = contato.getTelefone();
    }
}