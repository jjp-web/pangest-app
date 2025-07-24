package com.pangest.controller;

import com.pangest.model.Producto;
import com.pangest.model.Usuario;
import com.pangest.service.ProductoService;
import com.pangest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empresario")
public class EmpresarioController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model, Authentication auth) {
        Usuario user = usuarioService.buscarPorUsername(auth.getName());
        model.addAttribute("usuario", user);
        return "empresario/dashboard-empresario";
    }

    @GetMapping("/productos")
    public String verProductos(Model model, Authentication auth) {
        Usuario user = usuarioService.buscarPorUsername(auth.getName());
        model.addAttribute("productos", productoService.listarPorEmpresa(user.getEmpresa().getId()));
        return "empresario/productos";
    }

    @GetMapping("/crear-producto")
    public String crearProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "empresario/crear-producto";
    }

    // 👉 Guardar nuevo producto
    @PostMapping("/productos/guardar")
    public String guardar(@ModelAttribute Producto producto, Authentication auth) {
        Usuario user = usuarioService.buscarPorUsername(auth.getName());
        producto.setEmpresa(user.getEmpresa());
        productoService.guardar(producto);
        return "redirect:/empresario/productos";
    }

    // 👉 Eliminar producto
    @GetMapping("/productos/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/empresario/productos";
    }
}
