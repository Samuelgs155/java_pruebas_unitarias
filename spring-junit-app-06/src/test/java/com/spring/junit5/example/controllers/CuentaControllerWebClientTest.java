package com.spring.junit5.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.junit5.example.model.Cuenta;
import com.spring.junit5.example.model.TransaccionDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuentaControllerWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }
    /*
    @Test
    void transferir() throws JsonProcessingException {
        // given
        TransaccionDTO dto = new TransaccionDTO();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal("100"));

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Transferencia realizada con exito!");
        response.put("transaccion", dto);

        // when
        webTestClient.post().uri("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                // then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").isNotEmpty()
                .jsonPath("$.message").value(is("Transferencia realizada con exito!"))
                .jsonPath("$.message").value(valor -> assertEquals("Transferencia realizada con exito!", valor))
                .jsonPath("$.message").isEqualTo("Transferencia realizada con exito!")
                .jsonPath("$.transaccion.cuentaOrigenId").isEqualTo(dto.getCuentaOrigenId())
                .jsonPath("$.date").isEqualTo(LocalDate.now().toString());
                //.json(objectMapper.writeValueAsString(response));
    }
*/
    @Test
    @Order(1)
    void testTransferir() throws JsonProcessingException {
        // given
        TransaccionDTO dto = new TransaccionDTO();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal("100"));

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Transferencia realizada con exito!");
        response.put("transaccion", dto);

        // when
        webTestClient.post().uri("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                // then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(respuesta -> {
                    try {
                        JsonNode json = objectMapper.readTree(respuesta.getResponseBody());
                        assertEquals("Transferencia realizada con exito!", json.path("message").asText());
                        assertEquals(1L, json.path("transaccion").path("cuentaOrigenId").asLong());
                        assertEquals(LocalDate.now().toString(), json.path("date").asText());
                        assertEquals("100", json.path("transaccion").path("monto").asText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .jsonPath("$.message").isNotEmpty()
                .jsonPath("$.message").value(is("Transferencia realizada con exito!"))
                .jsonPath("$.message").value(valor -> assertEquals("Transferencia realizada con exito!", valor))
                .jsonPath("$.message").isEqualTo("Transferencia realizada con exito!")
                .jsonPath("$.transaccion.cuentaOrigenId").isEqualTo(dto.getCuentaOrigenId())
                .jsonPath("$.date").isEqualTo(LocalDate.now().toString());
                // .json(objectMapper.writeValueAsString(response));

    }

    @Test
    @Order(2)
    void testDetalle() throws JsonProcessingException {

        Cuenta cuenta = new Cuenta(1L, "Sam", new BigDecimal("1000"));

        webTestClient.get().uri("/api/cuentas/1").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.persona").isEqualTo("Sam")
                .jsonPath("$.saldo").isEqualTo(1000)
                .json(objectMapper.writeValueAsString(cuenta));
    }

    @Test
    @Order(3)
    void testDetalle2() {

        webTestClient.get().uri("/api/cuentas/2").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Cuenta.class)
                .consumeWith(response -> {
                    Cuenta cuenta = response.getResponseBody();
                    assertNotNull(cuenta);
                    assertEquals("John", cuenta.getPersona());
                    assertEquals("2000.00", cuenta.getSaldo().toPlainString());
                });
    }

    @Test
    @Order(4)
    void testListar() {
        webTestClient.get().uri("/api/cuentas").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].persona").isEqualTo("Sam")
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].saldo").isEqualTo(1000)
                .jsonPath("$[1].persona").isEqualTo("John")
                .jsonPath("$[1].id").isEqualTo(2)
                .jsonPath("$[1].saldo").isEqualTo(2000)
                .jsonPath("$").isArray()
                .jsonPath("$").value(hasSize(2));
    }

    @Test
    @Order(5)
    void testListar2() {
        webTestClient.get().uri("/api/cuentas").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Cuenta.class)
                .consumeWith(response -> {
                    List<Cuenta> cuentas = response.getResponseBody();
                    assertNotNull(cuentas);
                    assertEquals(2, cuentas.size());
                    assertEquals(1L, cuentas.get(0).getId());
                    assertEquals("Sam", cuentas.get(0).getPersona());
                    assertEquals(900, cuentas.get(0).getSaldo().intValue());
                    assertEquals(2L, cuentas.get(1).getId());
                    assertEquals("John", cuentas.get(1).getPersona());
                    assertEquals("2100.0", cuentas.get(1).getSaldo().toPlainString());
                })
                .hasSize(2)
                .value(hasSize(2));
    }

    @Test
    @Order(6)
    void testGuardar() {
        // given
        Cuenta cuenta = new Cuenta(null, "Pepe", new BigDecimal("3000"));

        // when
        webTestClient.post().uri("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cuenta)
                .exchange()
                // then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(3)
                .jsonPath("$.persona").isEqualTo("Pepe")
                .jsonPath("$.persona").value(is("Pepe"))
                .jsonPath("$.saldo").isEqualTo(3000);
    }

    @Test
    @Order(7)
    void testGuardar2() {
        // given
        Cuenta cuenta = new Cuenta(null, "Pepa", new BigDecimal("3500"));

        // when
        webTestClient.post().uri("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cuenta)
                .exchange()
                // then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Cuenta.class)
                .consumeWith(response -> {
                    Cuenta c = response.getResponseBody();
                    assertNotNull(c);
                    assertEquals(4L, c.getId());
                    assertEquals("Pepa", c.getPersona());
                    assertEquals("3500", c.getSaldo().toPlainString());
                });
    }

    @Test
    @Order(8)
    void testEliminar() {
        webTestClient.get().uri("/api/cuentas").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Cuenta.class)
                .hasSize(4);

        webTestClient.delete().uri("/api/cuentas/3")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        webTestClient.get().uri("/api/cuentas").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Cuenta.class)
                .hasSize(3);

        webTestClient.get().uri("/api/cuentas/3").exchange()
//                .expectStatus().is5xxServerError();
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

}