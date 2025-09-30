package com.engenharia_software.agenda.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private UsuarioDTO usuario; // o usuário logado
    private String token;       // o JWT gerado
}
