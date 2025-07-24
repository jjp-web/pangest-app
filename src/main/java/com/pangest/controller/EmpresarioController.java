package com.pangest.controller;

import com.pangest.model.Producto;
import com.pangest.model.Usuario;
import com.pangest.service.EmpresaService;
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
    
    
    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model, Authentication auth) {
        Usuario user = usuarioService.buscarPorUsername(auth.getName());
        model.addAttribute("usuario", user);
        model.addAttribute("productos", productoService.listarPorEmpresa(user.getEmpresa().getId()));
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
    
    
    @PostMapping("/empresa/editar")
    public String editarEmpresa(@RequestParam String nombre,
                                @RequestParam String descripcion,
                                @RequestParam String rubro,
                                Authentication auth) {
        Usuario user = usuarioService.buscarPorUsername(auth.getName());
        empresaService.editarEmpresa(user.getEmpresa().getId(), nombre, descripcion, rubro);
        return "redirect:/empresario/dashboard";
    }

    

    @PostMapping("/productos/guardar")
    public String guardar(@ModelAttribute Producto producto, Authentication auth) {
        Usuario user = usuarioService.buscarPorUsername(auth.getName());
        producto.setEmpresa(user.getEmpresa());
        productoService.guardar(producto);
        return "redirect:/empresario/productos";
    }

    
    @PostMapping("/productos/editar")
    public String editarProducto(@ModelAttribute Producto producto, Authentication auth) {
        Usuario user = usuarioService.buscarPorUsername(auth.getName());
        producto.setEmpresa(user.getEmpresa());
        productoService.guardar(producto);
        return "redirect:/empresario/dashboard";
    }

    
    @GetMapping("/editar-producto/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Producto producto = productoService.buscarPorId(id);
        model.addAttribute("producto", producto);
        return "empresario/editar-producto";
    }
    
    
    
    @PostMapping("/productos/eliminar")
    public String eliminar(@RequestParam Long id, Authentication auth) {
        Usuario user = usuarioService.buscarPorUsername(auth.getName());
        Producto producto = productoService.buscarPorId(id);

        if (producto == null) {
            System.out.println("❌ Producto no encontrado con ID: " + id);
        } else if (producto.getEmpresa() == null) {
            System.out.println("❌ Producto sin empresa asociada");
        } else if (!producto.getEmpresa().getId().equals(user.getEmpresa().getId())) {
            System.out.println("❌ Usuario no autorizado para eliminar este producto");
        } else {
            System.out.println("✅ Eliminando producto: " + producto.getNombre());
            productoService.eliminar(id);
        }

        return "redirect:/empresario/dashboard";
    }



}
