package org.maven.app.mockito.ejemplo.dao;

import org.maven.app.mockito.ejemplo.Datos;
import org.maven.app.mockito.ejemplo.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositorioOtro implements  ExamenRespository{
    @Override
    public Examen guardar(Examen examen) {
        return Datos.EXAMEN;
    }

    @Override
    public List<Examen> findAll()  {
       return Datos.EXAMENES;
    }
}
