package com.pangest.service;

import com.pangest.model.Producto;
import com.pangest.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepo;

    @InjectMocks
    private ProductoService productoService;

    public ProductoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodos() {
        Producto p1 = new Producto();
        p1.setId(1L);
        p1.setNombre("Pan");

        Producto p2 = new Producto();
        p2.setId(2L);
        p2.setNombre("Torta");

        List<Producto> listaSimulada = Arrays.asList(p1, p2);

        when(productoRepo.findAll()).thenReturn(listaSimulada);

        List<Producto> resultado = productoService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Pan", resultado.get(0).getNombre());
    }
}
