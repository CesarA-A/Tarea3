package com.tarea.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.tarea.mock.entidades.Perro;
import com.tarea.mock.excepciones.PerroNoEncontradoException;
import com.tarea.mock.repositorios.PerroRepository;
import com.tarea.mock.servicios.PerroComunitarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PerroComunitarioServiceTest {

    PerroRepository mockRepository;
    PerroComunitarioService service;

    @BeforeEach
    public void inicializarPrueba() {
        // Mock del repositorio
        mockRepository = Mockito.mock(PerroRepository.class);
        // Servicio a probar
        service = new PerroComunitarioService(mockRepository);
    }

    @Test
    public void deberiaDevolverPerroCuandoElPerroExiste() {
        Perro perro = new Perro("Fido", 4);
        when(mockRepository.buscarPorNombre("Fido")).thenReturn(perro);
        Perro resultado = service.obtenerPerroPorNombre("Fido");
        assertEquals(perro, resultado);

    }

    @Test
    public void deberiaLanzarPerroNoEncontradoExceptioCuandoElPerroNoExiste() {
      
        when(mockRepository.buscarPorNombre("Rex")).thenReturn(null);
      
        assertThrows(PerroNoEncontradoException.class, () -> {
            service.obtenerPerroPorNombre("Rex");
        });

    }

    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsNull() {
       
       when(mockRepository.buscarPorNombre(null)).thenReturn(null);

       assertThrows(IllegalArgumentException.class, ()->{
        service.obtenerPerroPorNombre(null);
       });
       
    }

    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsVacio() {
        assertThrows(IllegalArgumentException.class, () -> 
        { service.obtenerPerroPorNombre(""); });

    }

    @Test
    public void deberiaConsultarRepositorioUnaSolaVezCuandoElPerroExiste() {
        Perro perro = new Perro("Fido",5); 
        when(mockRepository.buscarPorNombre("Fido")).thenReturn(perro); 
        service.obtenerPerroPorNombre("Fido");
        verify(mockRepository, times(1)).buscarPorNombre("Fido");
    }
}
