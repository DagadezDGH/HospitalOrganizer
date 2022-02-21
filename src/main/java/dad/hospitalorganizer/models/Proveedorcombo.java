package dad.hospitalorganizer.models;

public class Proveedorcombo {
	 int codProveedor;
	 String nombre;
	 public Proveedorcombo(int codArticulo,String nombre) {
		 this.codProveedor=codArticulo;
		 this.nombre=nombre;
	 }
	public int getCodProveedor() {
		return codProveedor;
	}
	public void setCodProveedor(int codProveedor) {
		this.codProveedor = codProveedor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
	    return this.getNombre();
	}
	 
	
}
