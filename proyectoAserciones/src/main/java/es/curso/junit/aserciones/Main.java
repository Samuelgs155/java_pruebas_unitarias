package es.curso.junit.aserciones;

public class Main {

	public static void main(String[] args) {
		
		int a = 5;
		int b = 5;
		
		System.out.println("Primitivos -> " + (a == b));
		
		Persona personaA = new Persona("Paco", "Perez");
		Persona personaB = new Persona("Paco", "Perez");
		
		System.out.println("Objetos -> " + (personaA == personaB));
		System.out.println("Objetos -> " + (personaA.equals(personaB)));
	}

}
