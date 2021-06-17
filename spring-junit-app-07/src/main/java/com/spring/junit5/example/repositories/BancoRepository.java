package com.spring.junit5.example.repositories;

import com.spring.junit5.example.model.Banco;
import com.spring.junit5.example.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BancoRepository extends JpaRepository<Banco, Long> {
    // List<Banco> findAll();
    // Banco findById(Long id);
    // void update(Banco banco);
}
