package es.curso.junit.aserciones;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class UtilidadesNullTest {
	
	private UtilidadesNull utils;

	@Before
	public void setUp() throws Exception {
		utils = new UtilidadesNull();
	}

	@Test
	public void testDamePersona() throws Exception {
		//given

		//when
		Persona personaA = utils.damePersona("Juan");
		Persona personaNull = utils.damePersona("Pepe");
		//then
		Assert.assertNull(personaNull);
		Assert.assertNotNull(personaA);
		
		Assert.assertNull("No es nulo",personaA);
	}

}
