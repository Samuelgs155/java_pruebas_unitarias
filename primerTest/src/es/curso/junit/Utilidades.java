package es.curso.junit;

public class Utilidades {
	
	public String concatenar(String uno, String dos) {
		if(uno != null && dos != null) {
			return uno.concat(" ").concat(dos);
		}
		return null;
	}

}
