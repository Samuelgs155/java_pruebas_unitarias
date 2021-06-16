package com.spring.junit5.example.repositories;

import com.spring.junit5.example.model.Banco;

import java.util.List;

public interface BancoRepository {
    List<Banco> findAll();
    Banco findById(Long id);
    void update(Banco banco);
}
