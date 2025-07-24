package com.pangest.service;

import com.pangest.model.Empresa;
import com.pangest.repository.EmpresaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepo;

    @Mock
    private ProductoService productoService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private EmpresaService empresaService;

    public EmpresaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarTodas() {
        Empresa e1 = new Empresa();
        e1.setNombre("Empresa A");
        Empresa e2 = new Empresa();
        e2.setNombre("Empresa B");

        when(empresaRepo.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Empresa> resultado = empresaService.listarTodas();

        assertEquals(2, resultado.size());
        assertEquals("Empresa A", resultado.get(0).getNombre());
    }
}
