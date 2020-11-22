package es.curso.junit.aserciones;

public class Utilidades {

	public String concatenar(String uno, String dos) {
		if(uno != null && dos != null) {
			return uno.concat(" ").concat(dos);
		}
		return null;
	}

	public String[] getArrayStrings(String ... strings ) {
		String [] array = new String[strings.length];
		
		for(int i = 0; i<strings.length; i++) {
			array[i] = strings[i];
		}
		return array;
		
	}
	
	public boolean esMayorQue10(int num) {
		return num > 10;
	}
}
