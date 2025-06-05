package tuti.desi.presentacion.productos;

public class ProductosBuscarForm {
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
