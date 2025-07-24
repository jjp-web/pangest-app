package com.pangest.service;

import com.pangest.model.Empresa;
import com.pangest.model.Producto;
import com.pangest.repository.ProductoRepository;

import jakarta.transaction.Transactional;

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

    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepo.findById(id).orElseThrow();
        Empresa empresa = producto.getEmpresa();

        empresa.getProductos().remove(producto);

        System.out.println("⚠️ Eliminando producto manualmente de la empresa...");
        productoRepo.delete(producto);
    }


    public Producto buscarPorId(Long id) {
        return productoRepo.findById(id).orElse(null);
    }
}
