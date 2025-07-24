package com.pangest.controller;

import com.pangest.repository.EmpresaRepository;
import com.pangest.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @Autowired
    private EmpresaRepository empresaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("empresas", empresaRepo.findAll());
        model.addAttribute("productos", productoRepo.findAll());
        return "index";
    }
    
    @GetMapping("/empresas")
    public String empresas(Model model) {
        model.addAttribute("empresas", empresaRepo.findAll());
        model.addAttribute("productos", productoRepo.findAll());
        return "pages/empresas"; 
    }
    
    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("empresas", empresaRepo.findAll());
        model.addAttribute("productos", productoRepo.findAll());
        return "pages/productos";
    }
}
