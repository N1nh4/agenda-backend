package com.engenharia_software.agenda.util;

import com.engenharia_software.agenda.DTO.UsuarioDTO;
import com.engenharia_software.agenda.Factory.TipoAgenda;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Chave secreta para assinar o token (pode ser mais longa e complexa em produção)
    private static final String SECRET = "MinhaChaveSuperSecretaParaJWTQueDeveSerMuitoLonga123!";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Tempo de expiração: 1 dia (em ms)
    private static final long EXPIRACAO = 24 * 60 * 60 * 1000;

    // Gera o token JWT
    public static String gerarToken(UsuarioDTO usuario) {
        return Jwts.builder()
                .setSubject(String.valueOf(usuario.getId()))
                .claim("nome", usuario.getNome())
                .claim("email", usuario.getEmail())
                .claim("telefone", usuario.getTelefone())
                .claim("idAgenda", String.valueOf(usuario.getIdAgenda()))
                .claim("tipoAgenda", usuario.getTipoAgenda().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Valida o token e retorna o usuário
    public static UsuarioDTO validarToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);

            Claims body = claims.getBody();

            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setId(Long.valueOf(body.getSubject()));
            usuario.setNome((String) body.get("nome"));
            usuario.setEmail((String) body.get("email"));
            usuario.setTelefone((String) body.get("telefone"));
            usuario.setIdAgenda(Long.valueOf((String) body.get("idAgenda")));
            usuario.setTipoAgenda(TipoAgenda.valueOf((String) body.get("tipoAgenda")));

            return usuario;
        } catch (JwtException | IllegalArgumentException e) {
            return null; // token inválido ou expirado
        }
    }
}
