package es.curso.junit.aserciones;

public class UtilidadesPersonas {
	
	private Persona carlos;
	private Persona juan;
	
	public UtilidadesPersonas() {
		carlos = new Persona("Carlos", "Romero");
		juan = new Persona("Juan", "Lopez");
	}

	public Persona getCarlos() {
		return carlos;
	}

	public Persona getJuan() {
		return juan;
	}

	public Persona[] getArrayPersonas()	 {
		Persona[] personas = {carlos, juan};
		return personas;
	}

}
