package es.curso.junit.aserciones;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class UtilidadesPersonasTest {
	
	private UtilidadesPersonas utils;

	@Before
	public void setUp() throws Exception {
		utils = new UtilidadesPersonas();
	}

	@Test
	public void testGetCarlos() throws Exception {
		//given
		
		//when
		Persona carlos = utils.getCarlos();
		//then
		assertEquals("Carlos", carlos.getNombre());
		Assert.assertEquals("Romero", carlos.getApellido());
	}

	@Test
	public void testGetJuan() throws Exception {
		//given

		//when
		Persona juan = utils.getJuan();
		//then
		Assert.assertEquals("Juan", juan.getNombre());
		Assert.assertEquals("Lopez", juan.getApellido());
	}
	
	@Test
	public void testPersonasDiferentes() {
		Persona carlos = utils.getCarlos();
		Persona juan = utils.getJuan();
		assertNotEquals(juan, carlos);
		
	}

}
