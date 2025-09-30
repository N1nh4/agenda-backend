package com.engenharia_software.agenda.controller;

import com.engenharia_software.agenda.DTO.LoginDTO;
import com.engenharia_software.agenda.DTO.LoginResponse;
import com.engenharia_software.agenda.DTO.UsuarioDTO;
import com.engenharia_software.agenda.service.AuthService;
import com.engenharia_software.agenda.util.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService as;

    public AuthController(AuthService as) {
        this.as = as;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        UsuarioDTO usuario = as.login(login, null); // sem session
        if (usuario != null) {
            String token = JwtUtil.gerarToken(usuario);
            return ResponseEntity.ok(new LoginResponse(usuario, token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos!");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Com JWT, logout é feito no front removendo o token
        return ResponseEntity.ok("Deslogado com sucesso!");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente ou inválido!");
        }

        String token = authorizationHeader.replace("Bearer ", "");
        UsuarioDTO usuario = JwtUtil.validarToken(token);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado!");
    }
}
