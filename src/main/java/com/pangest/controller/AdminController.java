package com.pangest.controller;

import com.pangest.model.Empresa;
import com.pangest.model.Producto;
import com.pangest.model.Usuario;
import com.pangest.service.EmpresaService;
import com.pangest.service.ProductoService;
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
    
    @Autowired
    private ProductoService productoService;



    @GetMapping("/crear-empresario")
    public String formularioEmpresario(Model model) {
        model.addAttribute("usuarioNuevo", new Usuario()); 
        model.addAttribute("empresa", new Empresa());
        return "admin/crear-empresario";
    }

    @PostMapping("/crear-empresario")
    public String guardarEmpresario(@ModelAttribute Empresa empresa, @ModelAttribute("usuarioNuevo") Usuario usuario) {
        empresaService.guardar(empresa);
        usuario.setEmpresa(empresa);
        usuario.setRol("EMPRESARIO");
        usuarioService.guardar(usuario);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("empresas", empresaService.listarTodas());
        return "admin/dashboard";
    }
    
    
    @GetMapping("/metrics")
    public String mostrarMetricas(@RequestParam(required = false, defaultValue = "0") Long empresaId, Model model) {
        List<Empresa> empresas = empresaService.listarTodas();
        List<Producto> productos = productoService.listarPorEmpresa(empresaId);

        List<String> nombres = productos.stream().map(Producto::getNombre).toList();
        List<Double> precios = productos.stream().map(Producto::getPrecio).toList();

        model.addAttribute("empresas", empresas);
        model.addAttribute("empresaSeleccionada", empresaId);
        model.addAttribute("nombres", nombres);
        model.addAttribute("precios", precios);

        return "admin/admin-metrics";
    }


    


    @PostMapping("/empresas/{id}/editar")
    public String editarEmpresa(@PathVariable Long id,
                                @RequestParam String nombre,
                                @RequestParam String descripcion,
                                @RequestParam String rubro) {
        empresaService.editarEmpresa(id, nombre, descripcion, rubro);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/empresas/{id}/eliminar")
    public String eliminarEmpresa(@PathVariable Long id) {
        empresaService.eliminarEmpresa(id);
        return "redirect:/admin/dashboard";
    }

    
    
}
