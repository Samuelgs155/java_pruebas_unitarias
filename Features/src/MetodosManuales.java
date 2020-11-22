import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class MetodosManuales {
	
	@Before
	public void setUp() {
		System.out.println("antes de cada test");
	}
	
	@After
	public void tearDown() {
		System.out.println("después de cada test \n");
	}
	
	@BeforeClass
	public static void setUpClass() {
		System.out.println("antes de todos los test \n");
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println("después de todos test \n");
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
