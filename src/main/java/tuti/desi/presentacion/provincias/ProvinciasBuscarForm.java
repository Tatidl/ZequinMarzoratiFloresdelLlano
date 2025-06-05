package tuti.desi.presentacion.provincias;

public class ProvinciasBuscarForm {
	private String nombre;
	
	public String getNombre() {
		if(nombre!=null && nombre.length()>0)
			return nombre;
		else
			return null;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

}
