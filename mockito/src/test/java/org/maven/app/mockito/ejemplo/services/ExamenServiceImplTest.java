package org.maven.app.mockito.ejemplo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maven.app.mockito.ejemplo.dao.ExamenRepositorioOtro;
import org.maven.app.mockito.ejemplo.dao.ExamenRespository;
import org.maven.app.mockito.ejemplo.dao.PreguntaRepository;
import org.maven.app.mockito.ejemplo.models.Examen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamenServiceImplTest {

    ExamenRespository respository;
    ExamenService service;
    PreguntaRepository preguntaRepository;

    @BeforeEach
    void setUp() {
        respository = mock(ExamenRepositorioOtro.class);
        preguntaRepository = mock(PreguntaRepository.class);
        service = new ExamenServiceImpl(respository, preguntaRepository);
    }

    @Test
    void findExamenPorNombre() {
        when(respository.findAll()).thenReturn(Datos.EXAMENES);
        Optional<Examen> examen = null;
        examen = service.findExamenPorNombre("Matematicas");
        assertTrue(examen.isPresent());
        assertEquals(5L,
                examen.orElseThrow(null).getId());
        assertEquals("Matematicas", examen.orElseThrow(null).getNombre());

    }

    @Test
    void findExamenPorNombreListaVacia() {

        List<Examen> datos = Collections.emptyList();

        when(respository.findAll()).thenReturn(datos);

        Optional<Examen> examen = null;
        examen = service.findExamenPorNombre("Matematicas");

        assertFalse(examen.isPresent());

    }

    @Test
    void testPreguntasExamen() {
        when(respository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5L, examen.getPreguntas().size());

    }

    @Test
    void testPreguntasExamenVerify() {
        when(respository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5L, examen.getPreguntas().size());
        verify(respository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());

    }

    @Test
    void testNoExisteExamenVerify() {
        when(respository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas2");
        assertNull(examen);
        verify(respository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(5L);

    }
}