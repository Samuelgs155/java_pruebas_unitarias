package es.curso.junit.aserciones;

import java.util.ArrayList;
import java.util.List;

public class UtilidadesNull {
	
	private List<Persona> personas;
	
	public UtilidadesNull() {
		personas = new ArrayList<Persona>();
		personas.add(new Persona("Carlos", "Romero"));
		personas.add(new Persona("Juan", "Lopez"));
		personas.add(new Persona("Paco", "Martinez"));
	}
	
	public Persona damePersona(String nombre) {
		Persona p = null;
		for(Persona persona: personas) {
			if(persona.getNombre().equalsIgnoreCase(nombre)) {
				p = persona;
			}
		}
		
		return p;
	}

}
