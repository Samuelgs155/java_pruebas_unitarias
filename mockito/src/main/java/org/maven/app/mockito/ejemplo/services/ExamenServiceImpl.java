package org.maven.app.mockito.ejemplo.services;

import org.maven.app.mockito.ejemplo.dao.ExamenRespository;
import org.maven.app.mockito.ejemplo.models.Examen;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {

    private ExamenRespository examenRespository;

    public ExamenServiceImpl(ExamenRespository examenRespository) {
        this.examenRespository = examenRespository;
    }

    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) throws InterruptedException {
        return examenRespository.findAll()
                .stream().filter(ex-> ex.getNombre().equals(nombre)).findFirst();
    }
}
