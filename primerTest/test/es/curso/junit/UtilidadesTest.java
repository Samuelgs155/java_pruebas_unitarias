package es.curso.junit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UtilidadesTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testConcatenar() {
		// Instanciamos la clase a probar
		Utilidades utilidades = new Utilidades();
		
		// Ejecutar el metodo a probar
		String resultado = utilidades.concatenar("hola", "mundo");
		
		// validaciones
		Assert.assertEquals("Las cadenas son diferentes.", "hola mundo", resultado);
	}
	
	@Test
	public void testConcatenarFallo() {
		Utilidades utilidades = new Utilidades();
		String resultado = utilidades.concatenar("hola", "mundo");
		Assert.assertEquals("Las cadenas son diferentes.", "hola mundo", resultado);
	}
	
	@Test
	public void testConcatenarError() {
		Utilidades utilidades = new Utilidades();
		String resultado = utilidades.concatenar(null, "mundo");
		Assert.assertEquals("Las cadenas son diferentes.", null, resultado);
	}

}
