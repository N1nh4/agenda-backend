package com.engenharia_software.agenda.DTO;

import com.engenharia_software.agenda.Factory.TipoAgenda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private Long idAgenda;

    private String nome;
    private String email;
    private String telefone;
    private TipoAgenda tipoAgenda;
    private String senha;
    private String imagemUrl;
}
