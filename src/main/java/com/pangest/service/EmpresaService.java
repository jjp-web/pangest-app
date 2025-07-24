package com.pangest.service;


import com.pangest.model.Empresa;
import com.pangest.model.Producto;
import com.pangest.model.Usuario;
import com.pangest.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepo;
    
    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    public List<Empresa> listarTodas() {
        return empresaRepo.findAll();
    }

    
    public Empresa guardar(Empresa empresa) {
        return empresaRepo.save(empresa);
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepo.findById(id).orElse(null);
    }
    
    
    public void editarEmpresa(Long id, String nombre, String descripcion, String rubro) {
        Empresa empresa = empresaRepo.findById(id).orElse(null);
        if (empresa != null) {
            empresa.setNombre(nombre);
            empresa.setDescripcion(descripcion);
            empresa.setRubro(rubro);
            empresaRepo.save(empresa);
        }
    }




    public void eliminarEmpresa(Long id) {
        Empresa empresa = empresaRepo.findById(id).orElse(null);
        if (empresa != null) {
            System.out.println("Eliminando empresa: " + empresa.getNombre());

            List<Producto> productos = empresa.getProductos();
            if (productos != null) {
                for (Producto producto : productos) {
                    System.out.println("Eliminando producto: " + producto.getNombre());
                    productoService.eliminar(producto.getId());
                }
            }

            Usuario usuario = usuarioService.buscarPorEmpresa(empresa);
            if (usuario != null) {
                System.out.println("Eliminando usuario: " + usuario.getUsername());
                usuarioService.eliminar(usuario.getId());
            }

            empresaRepo.deleteById(id);
            System.out.println("Empresa eliminada correctamente.");
        }
    }

    
    
    
    
}
