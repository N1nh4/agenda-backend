package com.engenharia_software.agenda.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping("/publico/ola")
    public String publico() {
        return "Rota pública acessada!";
    }

    @GetMapping("/user/ola")
    public String user() {
        return "Rota protegida (qualquer usuário autenticado pode acessar).";
    }

    @GetMapping("/admin/ola")
    public String admin() {
        return "Rota exclusiva para ADMIN.";
    }
}
