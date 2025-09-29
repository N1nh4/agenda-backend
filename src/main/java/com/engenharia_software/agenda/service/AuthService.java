package com.engenharia_software.agenda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.engenharia_software.agenda.DTO.LoginDTO;
import com.engenharia_software.agenda.DTO.UsuarioDTO;
import com.engenharia_software.agenda.model.Usuario;
import com.engenharia_software.agenda.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO login(LoginDTO login, HttpServletRequest request) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByTelefone(login.getTelefone());

        if (usuarioOpt.isEmpty()) return null;

        Usuario usuario = usuarioOpt.get();

        // compara senha com hash
        if (!passwordEncoder.matches(login.getSenha(), usuario.getSenha())) {
            return null;
        }

        // cria sessão
        HttpSession session = request.getSession(true);
        session.setAttribute("usuario", usuario.getNome());

        // define autenticação pro Spring Security
        Authentication auth = new UsernamePasswordAuthenticationToken(
            usuario.getNome(), null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setIdAgenda(usuario.getAgenda().getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setTelefone(usuario.getTelefone());
        usuarioDTO.setTipoAgenda(usuario.getAgenda().getTipo());
        usuarioDTO.setImagemUrl(usuario.getImagemUrl());

        return usuarioDTO;
    }
}
