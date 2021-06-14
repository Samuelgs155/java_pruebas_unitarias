package org.maven.app.mockito.ejemplo.services;

import org.maven.app.mockito.ejemplo.models.Examen;

import java.util.Optional;

public interface ExamenService {

    Optional<Examen> findExamenPorNombre(String nombre) throws InterruptedException;
}
