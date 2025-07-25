package com.pangest.repository;

import com.pangest.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEmpresaId(Long empresaId);
}
