package com.pangest.service;

import com.pangest.model.Empresa;
import com.pangest.model.Usuario;
import com.pangest.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    public UsuarioServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarUsuario_codificaYGuarda() {
        Usuario usuario = new Usuario();
        usuario.setPassword("1234");

        when(passwordEncoder.encode("1234")).thenReturn("codificado");
        when(usuarioRepo.save(any(Usuario.class))).thenReturn(usuario);

        Usuario guardado = usuarioService.guardar(usuario);

        assertEquals("codificado", usuario.getPassword());
        verify(usuarioRepo).save(usuario);
    }

    @Test
    void testBuscarPorUsername() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan");

        when(usuarioRepo.findByUsername("juan")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorUsername("juan");

        assertNotNull(resultado);
        assertEquals("juan", resultado.getUsername());
    }

    @Test
    void testBuscarPorEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setNombre("Panaderia X");

        Usuario usuario = new Usuario();
        usuario.setEmpresa(empresa);

        when(usuarioRepo.findByEmpresa(empresa)).thenReturn(usuario);

        Usuario resultado = usuarioService.buscarPorEmpresa(empresa);

        assertNotNull(resultado);
        assertEquals(empresa, resultado.getEmpresa());
    }

    @Test
    void testEliminarUsuario() {
        Long id = 1L;

        usuarioService.eliminar(id);

        verify(usuarioRepo).deleteById(id);
    }
}
