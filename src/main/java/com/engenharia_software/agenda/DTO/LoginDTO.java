package com.engenharia_software.agenda.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private Long id;
    private String telefone;
    private String senha;
    private String imagemUrl;
}