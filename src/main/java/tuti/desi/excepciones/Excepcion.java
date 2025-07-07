package tuti.desi.excepciones;

public class Excepcion extends RuntimeException {

	public Excepcion(String mensaje) {
		super(mensaje);
	}

	public Excepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}

