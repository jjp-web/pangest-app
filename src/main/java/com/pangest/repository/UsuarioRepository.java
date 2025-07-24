package com.pangest.repository;

import com.pangest.model.Empresa;
import com.pangest.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    
    Usuario findByEmpresa(Empresa empresa);

}
