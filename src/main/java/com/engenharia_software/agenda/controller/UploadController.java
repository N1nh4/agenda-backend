package com.engenharia_software.agenda.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.engenharia_software.agenda.DTO.UsuarioDTO;
import com.engenharia_software.agenda.model.Usuario;
import com.engenharia_software.agenda.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/uploads")
public class UploadController {

    private final UsuarioRepository ur;

    public UploadController(UsuarioRepository ur) {
        this.ur = ur;
    }

    @GetMapping("/{diretorio}")
    public ResponseEntity<Resource> getImage(@PathVariable String diretorio) throws IOException {
        Path filePath = Paths.get("uploads/", diretorio);
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PostMapping("/imagem_perfil")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, HttpSession session) {
        try {
            // Pega o DTO do usuário da sessão
            UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuario");
            if (usuarioDTO == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não logado");
            }

            // Busca a entidade completa no banco
            Usuario usuario = ur.findById(usuarioDTO.getId())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Cria pasta uploads se não existir
            File dir = new File("uploads/");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Nome do arquivo personalizado
            String extension = file.getOriginalFilename()
                                .substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = "usuario_" + usuario.getId() + extension;
            Path filePath = Paths.get("uploads/", fileName);
            Files.write(filePath, file.getBytes());

            // Atualiza a URL no usuário
            String imageUrl = "/uploads/" + fileName;
            usuario.setImagemUrl(imageUrl);

            // Salva no banco
            ur.save(usuario);

            // Atualiza também o DTO na sessão
            usuarioDTO.setImagemUrl(imageUrl);
            session.setAttribute("usuario", usuarioDTO);

            System.out.println("USUARIO NA SESSÃO: " + usuarioDTO.getImagemUrl());

            return ResponseEntity.ok(imageUrl);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar imagem: " + e.getMessage());
        }
    }

}