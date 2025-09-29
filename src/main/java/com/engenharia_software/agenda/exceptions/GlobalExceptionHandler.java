package com.engenharia_software.agenda.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Violação de integridade de dados");

        // Extrair mensagem do banco (MySQL)
        String message = ex.getMostSpecificCause().getMessage();

        // Detectar se é violação de constraint UNIQUE
        if (message != null && message.contains("Duplicate entry")) {
            // Extrair valor duplicado (opcional)
            String valorDuplicado = message.split("'")[1];
            response.put("message", "O valor '" + valorDuplicado + "' já existe.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
            response.put("message", message);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }
}
