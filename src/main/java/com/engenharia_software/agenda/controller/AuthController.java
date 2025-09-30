package com.engenharia_software.agenda.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.engenharia_software.agenda.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.engenharia_software.agenda.DTO.LoginDTO;
import com.engenharia_software.agenda.DTO.UsuarioDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService as;

    public AuthController(AuthService as) {
        this.as = as;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login, HttpServletRequest request) { 
        UsuarioDTO usuario = as.login(login, request);
        if (usuario != null) {
            request.getSession().setAttribute("usuario", usuario);
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        }
        return ResponseEntity.status(401).body("Usuário ou senha inválidos!");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // sobrescreve o cookie JSESSIONID no cliente
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // expira imediatamente
        response.addCookie(cookie);

        return ResponseEntity.ok("Deslogado com sucesso!");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // não cria sessão nova
        if (session != null) {
            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autorizado!");
    }
}
