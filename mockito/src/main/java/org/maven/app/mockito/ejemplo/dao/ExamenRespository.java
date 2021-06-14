package org.maven.app.mockito.ejemplo.dao;

import org.maven.app.mockito.ejemplo.models.Examen;

import java.util.List;

public interface ExamenRespository {

    List<Examen> findAll() throws InterruptedException;
}
