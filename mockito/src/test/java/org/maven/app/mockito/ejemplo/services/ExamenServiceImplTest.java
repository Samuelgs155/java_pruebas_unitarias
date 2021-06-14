package org.maven.app.mockito.ejemplo.services;

import org.junit.jupiter.api.Test;
import org.maven.app.mockito.ejemplo.dao.ExamenRepositorioOtro;
import org.maven.app.mockito.ejemplo.dao.ExamenRespository;
import org.maven.app.mockito.ejemplo.models.Examen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExamenServiceImplTest {

    @Test
    void findExamenPorNombre() {
        ExamenRespository respository = mock(ExamenRepositorioOtro.class);
        ExamenService service = new ExamenServiceImpl(respository);

        List<Examen> datos = Arrays.asList(new Examen(5L, "Matematicas"),
                new Examen(6L, "Lenguaje"),
                new Examen(7L, "Historia"));

        try {
            when(respository.findAll()).thenReturn(datos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Optional<Examen> examen = null;

        try {
            examen = service.findExamenPorNombre("Matematicas");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        assertTrue(examen.isPresent());
        assertEquals(5L,
                examen.orElseThrow(null).getId());
        assertEquals("Matematicas", examen.orElseThrow(null).getNombre());

    }

    @Test
    void findExamenPorNombreListaVacia() {
        ExamenRespository respository = mock(ExamenRepositorioOtro.class);
        ExamenService service = new ExamenServiceImpl(respository);

        List<Examen> datos = Collections.emptyList();

        try {
            when(respository.findAll()).thenReturn(datos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Optional<Examen> examen = null;

        try {
            examen = service.findExamenPorNombre("Matematicas");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        assertTrue(examen.isPresent());
        assertEquals(5L,
                examen.orElseThrow(null).getId());
        assertEquals("Matematicas", examen.orElseThrow(null).getNombre());

    }

}