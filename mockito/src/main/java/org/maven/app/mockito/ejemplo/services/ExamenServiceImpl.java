package org.maven.app.mockito.ejemplo.services;

import org.junit.jupiter.api.Test;
import org.maven.app.mockito.ejemplo.dao.ExamenRespository;
import org.maven.app.mockito.ejemplo.dao.PreguntaRepository;
import org.maven.app.mockito.ejemplo.models.Examen;

import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {

    private ExamenRespository examenRespository;
    private PreguntaRepository preguntaRepository;

    public ExamenServiceImpl(ExamenRespository examenRespository, PreguntaRepository preguntaRepository) {
        this.examenRespository = examenRespository;
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
        return examenRespository.findAll()
                .stream().filter(ex-> ex.getNombre().equals(nombre)).findFirst();
    }

    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {
        Optional<Examen> examenOptional = findExamenPorNombre(nombre);
        Examen examen = null;
        if(examenOptional.isPresent()){
            examen = examenOptional.orElseThrow(null);
            List<String> preguntas = preguntaRepository.findPreguntasPorExamenId(examen.getId());
            examen.setPreguntas(preguntas);
        }
        return examen;
    }

    @Override
    public Examen guardar(Examen examen) {
        if(!examen.getPreguntas().isEmpty()) {
            preguntaRepository.guardarVarias(examen.getPreguntas());
        }
        return examenRespository.guardar(examen);
    }


}
