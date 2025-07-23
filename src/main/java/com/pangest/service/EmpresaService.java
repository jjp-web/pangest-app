package com.pangest.service;

import com.pangest.model.Empresa;
import com.pangest.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepo;


    public List<Empresa> listarTodas() {
        return empresaRepo.findAll()
            .stream()
            .filter(e -> e.getNombre() != null ) 
            .toList();
    }
    
    
    public Empresa guardar(Empresa empresa) {
        return empresaRepo.save(empresa);
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepo.findById(id).orElse(null);
    }
}
