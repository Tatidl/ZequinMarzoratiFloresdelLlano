package tuti.desi.presentacion.productos;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import tuti.desi.entidades.Producto;

/**
 * Objeto necesario para insertar o eliminar una persona. 
 * Nótese que en lugar de referenciar al objeto Ciudad, reemplaza ese atributo por el idCiudad, lo cual resulta mas sencillo de representar en JSON
 *
 */
public class ProductoForm {

	private Long id;
	@NotNull
	private String nombre;
	@NotNull
	private Long calorias;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fechaVencimiento;
	@NotNull
	private Long stock;
	@NotNull
	private Double precio;
	

	
	public ProductoForm() {
		super();
	}
	public ProductoForm(Producto p) {
		super();
		this.id=p.getId();
		this.nombre=p.getNombre();
		this.calorias=p.getCalorias();
		this.fechaVencimiento=p.getFechaVencimiento();
		this.stock=p.getStock();
		this.precio=p.getPrecio();
	}
	
	
	public Producto toPojo()
	{
		Producto p = new Producto();
		p.setId(this.getId());
		p.setNombre(this.getNombre());
		p.setCalorias(this.getCalorias());
		p.setFechaVencimiento(this.getFechaVencimiento());
		p.setStock(this.getStock());
		p.setPrecio(this.getPrecio());
		return p;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getCalorias() {
		return calorias;
	}
	public void setCalorias(Long calorias) {
		this.calorias = calorias;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	
	
	
	
}
