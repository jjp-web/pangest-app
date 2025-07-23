package com.pangest.service;

import com.pangest.model.Producto;
import com.pangest.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepo;

    public List<Producto> listarTodos() {
        return productoRepo.findAll();
    }

    public List<Producto> listarPorEmpresa(Long empresaId) {
        return productoRepo.findByEmpresaId(empresaId);
    }

    public Producto guardar(Producto producto) {
        return productoRepo.save(producto);
    }

    public void eliminar(Long id) {
        productoRepo.deleteById(id);
    }

    public Producto buscarPorId(Long id) {
        return productoRepo.findById(id).orElse(null);
    }
}
