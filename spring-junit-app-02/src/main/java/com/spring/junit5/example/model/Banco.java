package com.spring.junit5.example.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Banco {

    private Long id;
    private String nombre;
    private int totalTransferencias;

    public Banco() {
    }

    public Banco(Long id, String nombre, int totalTransferencias) {
        this.id = id;
        this.nombre = nombre;
        this.totalTransferencias = totalTransferencias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTotalTransferencias() {
        return totalTransferencias;
    }

    public void setTotalTransferencias(int totalTransferencias) {
        this.totalTransferencias = totalTransferencias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banco banco = (Banco) o;
        return totalTransferencias == banco.totalTransferencias && Objects.equals(id, banco.id) && Objects.equals(nombre, banco.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, totalTransferencias);
    }
}
