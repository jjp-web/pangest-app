package com.pangest.controller;

import com.pangest.model.Usuario;
import com.pangest.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; 
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 HttpSession session,
                                 Model model) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            // Compara contraseñas (por ahora simple, pero deberías usar BCrypt)
            if (usuario.getPassword().equals(password)) {
                session.setAttribute("usuario", usuario);
                return "redirect:/admin/dashboard";
            }
        }

        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }
}
