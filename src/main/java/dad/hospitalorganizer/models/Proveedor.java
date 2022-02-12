package dad.hospitalorganizer.models;

public class Proveedor {
	private int codProveedor;
	private String nombre;
	private String telefono;
	 
	 public Proveedor(int codigo, String name, String telefon) {
		this.codProveedor=codigo;
		this.nombre=name;
		this.telefono=telefon;
	 }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getCodProveedor() {
		return codProveedor;
	}
	 
}
