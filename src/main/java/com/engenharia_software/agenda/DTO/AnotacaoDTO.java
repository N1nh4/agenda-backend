package com.engenharia_software.agenda.DTO;

import com.engenharia_software.agenda.model.Anotacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnotacaoDTO {
    private String titulo;
    private String conteudo;
    private Long agendaId;

    public AnotacaoDTO(Anotacao anotacao) {
        this.titulo = anotacao.getTitulo();
        this.conteudo = anotacao.getConteudo();
        this.agendaId = anotacao.getAgenda().getId();
    }
}