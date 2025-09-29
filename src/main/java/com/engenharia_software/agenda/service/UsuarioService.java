package com.engenharia_software.agenda.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.engenharia_software.agenda.DTO.UsuarioDTO;
import com.engenharia_software.agenda.Factory.FabricaAgenda;
import com.engenharia_software.agenda.model.Agenda;
import com.engenharia_software.agenda.model.Usuario;
import com.engenharia_software.agenda.repository.AgendaRepository;
import com.engenharia_software.agenda.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private UsuarioRepository ur;
    private AgendaRepository ar;

    public UsuarioService(UsuarioRepository ur, AgendaRepository ar) {
        this.ur = ur;
        this.ar = ar;
    }

    public UsuarioDTO criarUsuario(UsuarioDTO usuario) {
        Agenda a = ar.findById(usuario.getIdAgenda()).get();
        
        Usuario u = new Usuario();

        u.setNome(usuario.getNome());
        u.setEmail(usuario.getEmail());
        u.setTelefone(usuario.getTelefone());

        PasswordEncoder pe = new BCryptPasswordEncoder();
        String senhaCriptografada = pe.encode(usuario.getSenha());
        u.setSenha(senhaCriptografada);

        Agenda agenda = FabricaAgenda.getInstancia().criarAgenda(usuario.getTipoAgenda());

        agenda.setId(usuario.getIdAgenda());
        agenda.setTipo(usuario.getTipoAgenda());

        u.setAgenda(a);

        Usuario usuarioSalvo = ur.save(u);
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId(usuarioSalvo.getId());
        usuarioDTO.setIdAgenda(usuarioSalvo.getAgenda().getId());
        usuarioDTO.setNome(usuarioSalvo.getNome());
        usuarioDTO.setEmail(usuarioSalvo.getEmail());
        usuarioDTO.setTelefone(usuarioSalvo.getTelefone());
        usuarioDTO.setTipoAgenda(usuarioSalvo.getAgenda().getTipo());

        return usuarioDTO;
    }
}
