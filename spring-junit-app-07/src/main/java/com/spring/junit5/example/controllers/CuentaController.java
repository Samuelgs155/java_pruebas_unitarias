package com.spring.junit5.example.controllers;

import com.spring.junit5.example.model.Cuenta;
import com.spring.junit5.example.model.TransaccionDTO;
import com.spring.junit5.example.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    @ResponseStatus(OK)
    public List<Cuenta> listarCuentas(){
        return cuentaService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cuenta guardar(@RequestBody Cuenta cuenta) {
        return cuentaService.save(cuenta);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Cuenta detalle(@PathVariable(name="id") Long id){
        return cuentaService.findById(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDTO transaccionDTO){
        cuentaService.transferir(transaccionDTO.getCuentaOrigenId(),
                transaccionDTO.getCuentaDestinoId(),
                transaccionDTO.getMonto(),
                transaccionDTO.getBancoId());
        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("message", "Transferencia realizada con exito!");
        response.put("transaccion", transaccionDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        cuentaService.deleteById(id);
    }

}
