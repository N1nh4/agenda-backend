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
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginDTO login, HttpServletRequest request) {
        UsuarioDTO usuario = as.login(login, request);
        if (usuario == null) {
            System.out.println("Login falhou para: " + login.getTelefone());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        System.out.println("Login bem-sucedido para: " + usuario.getNome());

        // Mostra os cookies depois do login
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Cookie após login: " + cookie.getName() + " = " + cookie.getValue());
            }
        }

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("Logout da sessão: " + session.getId());
            session.invalidate();
        }

        // sobrescreve o cookie JSESSIONID no cliente
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // deixa false para testes em local/dev
        cookie.setPath("/");
        cookie.setMaxAge(0); // expira imediatamente
        response.addCookie(cookie);

        return ResponseEntity.ok("Deslogado com sucesso!");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // não cria sessão nova

        if (session != null) {
            System.out.println("Session ID: " + session.getId());

            // Loga os cookies recebidos
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    System.out.println("Cookie recebido: " + cookie.getName() + " = " + cookie.getValue());
                }
            } else {
                System.out.println("Nenhum cookie recebido!");
            }

            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
            if (usuario != null) {
                System.out.println("Usuario na sessão: " + usuario.getNome());
                return ResponseEntity.ok(usuario);
            } else {
                System.out.println("Sessão existe mas usuário é null");
            }
        } else {
            System.out.println("Sessão não encontrada!");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autorizado!");
    }
}
