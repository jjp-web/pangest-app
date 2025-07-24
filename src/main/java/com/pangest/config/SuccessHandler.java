package com.pangest.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.pangest.model.Usuario;
import com.pangest.service.UsuarioService;

import java.io.IOException;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioService usuarioService;

    public SuccessHandler(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String username = authentication.getName();

        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario != null) {
            request.getSession().setAttribute("usuario", usuario);
            System.out.println("✔ Usuario guardado en sesión: " + usuario.getUsername());
        } else {
            System.out.println("✘ No se encontró el usuario en la BD");
        }

        var authorities = authentication.getAuthorities().toString();
        if (authorities.contains("ADMIN")) {
            response.sendRedirect("/admin/dashboard");
        } else if (authorities.contains("EMPRESARIO")) {
            response.sendRedirect("/empresario/dashboard");
        } else {
            response.sendRedirect("/");
        }
    }
}
