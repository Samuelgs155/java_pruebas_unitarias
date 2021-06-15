package org.maven.app.mockito.ejemplo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.maven.app.mockito.ejemplo.dao.ExamenRespository;
import org.maven.app.mockito.ejemplo.dao.PreguntaRepository;
import org.maven.app.mockito.ejemplo.models.Examen;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest3 {

    @Mock
    ExamenRespository respository;
    @Mock
    PreguntaRepository preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this);
        // respository = mock(ExamenRepositorioOtro.class);
        // preguntaRepository = mock(PreguntaRepository.class);
        // service = new ExamenServiceImpl(respository, preguntaRepository);
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

    @Test
    void guardarExamenTest() {
        // Given
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        when(respository.guardar(any(Examen.class))).then(new Answer<Examen>() {
            Long secuencia = 8L;
            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen = invocation.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        });

        // When
        Examen examen = service.guardar(newExamen);

        // Then
        assertNotNull(examen.getId());
        assertEquals(8L,examen.getId());
        assertEquals("Física", examen.getNombre());
        verify(respository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }

    @Test
    void testManejoException() {
        // given
        when(respository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
           service.findExamenPorNombreConPreguntas("Matematicas");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testManejoExceptionNull() {
        // given
        when(respository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
        when(preguntaRepository.findPreguntasPorExamenId(isNull())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.findExamenPorNombreConPreguntas("Matematicas");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());
        verify(preguntaRepository).findPreguntasPorExamenId(null);
    }

    @Test
    void testArgumentMatchers() {

        when(respository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(respository).findAll();
        //verify(preguntaRepository).findPreguntasPorExamenId(ArgumentMatchers.argThat(arg -> arg.equals(5L)));
        //verify(preguntaRepository).findPreguntasPorExamenId(ArgumentMatchers.argThat(arg -> arg != null && arg.equals(5L)));
        //verify(preguntaRepository).findPreguntasPorExamenId(eq(5L));
        //verify(preguntaRepository).findPreguntasPorExamenId(ArgumentMatchers.argThat(arg -> arg != null && arg > 4L));
        // verify(preguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));
    }

    public static class MiArgsMatchers extends ArgumentMatchers {

        private Long argument;

        public boolean matches(Long argument) {
            this.argument = argument;
            return argument != null && argument > 0;
        }

        @Override
        public String toString() {
            return "es para un mensaje personalizado de error " +
                    "que imprime en caso de que falle el test "
                    + argument + "  debe ser un entero positivo";
        }
    }
}