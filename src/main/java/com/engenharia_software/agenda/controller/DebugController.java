package com.engenharia_software.agenda.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DebugController {
    @GetMapping("/me")
    public Object me(Authentication authentication) {
        return authentication;
    }
}