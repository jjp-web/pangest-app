package com.pangest.controller;

import com.pangest.model.Empresa;
import com.pangest.model.Usuario;
import com.pangest.service.EmpresaService;
import com.pangest.service.UsuarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/crear-empresario")
    public String formularioEmpresario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("empresa", new Empresa());
        return "admin/crear-empresario";
    }
    
    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        List<Empresa> empresas = empresaService.listarTodas(); // puede estar vacío
        model.addAttribute("empresas", empresas);
        return "admin/dashboard";
    }


    @PostMapping("/crear-empresario")
    public String guardarEmpresario(@ModelAttribute Empresa empresa, @ModelAttribute Usuario usuario) {
        empresaService.guardar(empresa);
        usuario.setEmpresa(empresa);
        usuario.setRol("EMPRESARIO");
        usuarioService.guardar(usuario);
        return "redirect:/";
    }
}
