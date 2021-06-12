package org.spring.junit5.example;

import org.junit.jupiter.api.Test;
import org.spring.junit5.example.exceptions.DineroInsuficienteException;
import org.spring.junit5.example.models.Banco;
import org.spring.junit5.example.models.Cuenta;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

// import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void test_nombre_cuenta() {
        Cuenta cuenta = new Cuenta("Sam", new BigDecimal("1000.12345"));
        // cuenta.setPersona("Sam");
        String esperado = "Sam";
        String real = cuenta.getPersona();
        assertNotNull(real);
        assertEquals(esperado, real);
        assertTrue(real.equals("Sam"));
    }

    @Test
    void test_nombre_cuenta_2() {
        Cuenta cuenta = new Cuenta("Sam G", new BigDecimal("1000.12345"));
        // cuenta.setPersona("Sam");
        String esperado = "Sam";
        String real = cuenta.getPersona();
        assertNotNull(real, "La cuenta no puede ser nula");
        assertEquals(esperado, real, "El nombre de la cuenta no es el que se esperaba");
        assertTrue(real.equals("Sam"), "Nombre cuenta esperada no es igual a la real");
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Sam", new BigDecimal("1000.12345"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testReferencia() {
        Cuenta cuenta = new Cuenta("Sam", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Sam", new BigDecimal("8900.9997"));
        // assertNotEquals(cuenta, cuenta2);
        assertEquals(cuenta, cuenta2);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Sam", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal("100"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Sam", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal("100"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsufienteExceptionTestCuenta() {
        Cuenta cuenta = new Cuenta("Sam", new BigDecimal("1000.12345"));
        Exception e = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String real = e.getMessage();
        String esperado = "Dinero insuficiente";
        assertEquals(real, esperado);
    }

    @Test
    void transferirDineroCuenta() {
        Cuenta cuenta1 = new Cuenta("Sam", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuentasTest() {
        Cuenta cuenta1 = new Cuenta("Sam", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.setNombre("Banco del estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());

        assertEquals(2, banco.getCuentas().size());
        assertEquals("Banco del estado", cuenta1.getBanco().getNombre());

        assertEquals("Andres", banco.getCuentas().stream()
                .filter(c -> c.getPersona().equals("Andres"))
                .findFirst()
                .get().getPersona());

        assertTrue(banco.getCuentas().stream()
                .anyMatch(c -> c.getPersona().equals("Andres")));
    }

    @Test
    void testRelacionBancoCuentasTest_2() {
        Cuenta cuenta1 = new Cuenta("Sam", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.setNombre("Banco del estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertAll(() -> {
                    assertEquals("1000.898", cuenta2.getSaldo().toPlainString());
                },
                () -> {
                    assertEquals("3000", cuenta1.getSaldo().toPlainString());
                },
                () -> {
                    assertEquals(2, banco.getCuentas().size());
                },
                () -> {
                    assertEquals("Banco del estado.", cuenta1.getBanco().getNombre());
                },
                () -> {
                    assertEquals("Andres", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Andres"))
                            .findFirst()
                            .get().getPersona());
                },
                () -> {
                    assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("Andres")));
                });

    }

    @Test
    void testRelacionBancoCuentasTest_3() {
        Cuenta cuenta1 = new Cuenta("Sam", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.setNombre("Banco del estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertAll(() -> {
                    assertEquals("1000.898", cuenta2.getSaldo().toPlainString(), () -> {
                        return "El valor del saldo de la cuenta2 no es el esperado";
                    });
                },
                () -> {
                    assertEquals("3000", cuenta1.getSaldo().toPlainString());
                },
                () -> {
                    assertEquals(2, banco.getCuentas().size());
                },
                () -> {
                    assertEquals("Banco del estado.", cuenta1.getBanco().getNombre());
                },
                () -> {
                    assertEquals("Andres", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Andres"))
                            .findFirst()
                            .get().getPersona());
                },
                () -> {
                    assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("Andres")));
                });

    }
}