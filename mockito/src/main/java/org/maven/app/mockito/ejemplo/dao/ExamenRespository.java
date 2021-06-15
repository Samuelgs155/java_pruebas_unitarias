package org.maven.app.mockito.ejemplo.dao;

import org.maven.app.mockito.ejemplo.models.Examen;

import java.util.List;

public interface ExamenRespository {
    Examen guardar(Examen examen);
    List<Examen> findAll();
}
