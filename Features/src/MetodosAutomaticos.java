import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MetodosAutomaticos {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Antes de todos \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Después de todos \n");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("Antes de cada test");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Despues de cada test\n");
	}

	@Test
	public void primerTest() {
		System.out.println("Primer test");
	}

	@Test
	public void segundoTest() {
		System.out.println("Segundo test");
	}

}
