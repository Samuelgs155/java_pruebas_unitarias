package com.spring.junit5.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.junit5.example.model.Cuenta;
import com.spring.junit5.example.model.TransaccionDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuentaControllerTestRestTemplate {

    @Autowired
    private TestRestTemplate client;

    private ObjectMapper objectMapper;

    @LocalServerPort
    private int puerto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testTransferir() throws JsonProcessingException {

        TransaccionDTO dto = new TransaccionDTO();
        dto.setMonto(new BigDecimal("100"));
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);

        // ResponseEntity<String> response =
        //        client.postForEntity("/api/cuentas/transferir", dto, String.class);

        ResponseEntity<String> response =
                client.postForEntity(crearUri("/api/cuentas/transferir") , dto, String.class);
        System.out.println("puerto " + puerto);

        String json = response.getBody();
        System.out.println("json " + json);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertNotNull(json);
        assertTrue(json.contains("Transferencia realizada con exito!"));

        JsonNode jsonNode = objectMapper.readTree(json);
        assertEquals("Transferencia realizada con exito!", jsonNode.get("message").asText());
        assertEquals(LocalDate.now().toString(), jsonNode.get("date").asText());
        assertEquals("100", jsonNode.get("transaccion").path("monto").asText());
        assertEquals(1L, jsonNode.get("transaccion").path("cuentaOrigenId").asLong());

        Map<String, Object> response2 = new HashMap<>();
        response2.put("date", LocalDate.now().toString());
        response2.put("message", "Transferencia realizada con exito!");
        response2.put("transaccion", dto);

        assertEquals(objectMapper.writeValueAsString(response2), json);

    }

    private String crearUri(String uri){
        return "http://localhost:" + puerto + uri;
    }

    @Test
    @Order(2)
    void testDetalle() {


        ResponseEntity<Cuenta> respuesta =
                client.getForEntity(crearUri("/api/cuentas/1"), Cuenta.class);

        Cuenta cuenta = respuesta.getBody();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());

        assertNotNull(cuenta);
        assertEquals(1l, cuenta.getId());
        assertEquals("Sam", cuenta.getPersona());
        assertEquals("900.00", cuenta.getSaldo().toString());
        assertEquals(new Cuenta(1L, "Sam", new BigDecimal("900.00")), cuenta);
    }

    @Test
    @Order(3)
    void testListar() throws JsonProcessingException {

        ResponseEntity<Cuenta[]> respuesta =
                client.getForEntity(crearUri("/api/cuentas"), Cuenta[].class);

        List<Cuenta> cuentas = Arrays.asList(respuesta.getBody());
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());

        assertNotNull(cuentas);
        assertEquals(2, cuentas.size());

        assertEquals(1l, cuentas.get(0).getId());
        assertEquals("Sam", cuentas.get(0).getPersona());
        assertEquals("900.00", cuentas.get(0).getSaldo().toString());
        assertEquals(new Cuenta(1L, "Sam", new BigDecimal("900.00")), cuentas.get(0));

        assertNotNull(cuentas);
        assertEquals(2l, cuentas.get(1).getId());
        assertEquals("John", cuentas.get(1).getPersona());
        assertEquals("2100.00", cuentas.get(1).getSaldo().toString());
        assertEquals(new Cuenta(2L, "John", new BigDecimal("2100.00")), cuentas.get(1));

        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(cuentas));
        assertEquals(1L, jsonNode.get(0).path("id").asLong());
        assertEquals("Sam", jsonNode.get(0).path("persona").asText());
        assertEquals("900.0", jsonNode.get(0).path("saldo").asText());

        assertEquals(2L, jsonNode.get(1).path("id").asLong());
        assertEquals("John", jsonNode.get(1).path("persona").asText());
        assertEquals("2100.0", jsonNode.get(1).path("saldo").asText());

    }

    @Test
    @Order(4)
    void testGuardar() {

        Cuenta cuenta = new Cuenta(null, "Pepa", new BigDecimal("3800"));
        ResponseEntity<Cuenta> respuesta =
                client.postForEntity(crearUri("/api/cuentas"), cuenta, Cuenta.class);
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());

        Cuenta cuentaCreada = respuesta.getBody();
        assertNotNull(cuentaCreada);
        assertEquals(3L, cuentaCreada.getId());
        assertEquals("Pepa", cuentaCreada.getPersona());
        assertEquals("3800", cuentaCreada.getSaldo().toPlainString());


    }

    @Test
    @Order(5)
    void testEliminar() {

        ResponseEntity<Cuenta[]> respuesta = client.getForEntity(crearUri("/api/cuentas"), Cuenta[].class);
        List<Cuenta> cuentas = Arrays.asList(respuesta.getBody());
        assertEquals(3, cuentas.size());
/*
        client.delete(crearUri("/api/cuentas/3"));
     */
        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("id", 3L);
        ResponseEntity<Void> exchange = client.exchange(crearUri("/api/cuentas/{id}"), HttpMethod.DELETE, null, Void.class,
                pathVariables);

        assertEquals(HttpStatus.NO_CONTENT, exchange.getStatusCode());
        assertFalse(exchange.hasBody());

        respuesta = client.getForEntity(crearUri("/api/cuentas"), Cuenta[].class);
        cuentas = Arrays.asList(respuesta.getBody());
        assertEquals(2, cuentas.size());

        ResponseEntity<Cuenta> respuestaDetalle = client.getForEntity(crearUri("/api/cuentas/3"), Cuenta.class);
        assertEquals(HttpStatus.NOT_FOUND, respuestaDetalle.getStatusCode());
        assertFalse(respuestaDetalle.hasBody());


    }

}