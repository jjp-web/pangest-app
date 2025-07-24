package com.pangest.config;

import com.pangest.model.Usuario;
import com.pangest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute("usuario")
    public Usuario agregarUsuarioAlModelo(Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            return usuarioService.buscarPorUsername(username);
        }
        return null; 
    }
}
