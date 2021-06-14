package org.maven.app.mockito.ejemplo.dao;

import org.maven.app.mockito.ejemplo.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositorioOtro implements  ExamenRespository{
    @Override
    public List<Examen> findAll()  {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
