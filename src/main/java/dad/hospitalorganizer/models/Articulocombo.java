package dad.hospitalorganizer.models;

public class Articulocombo {
	 int codArticulo;
	 String nombre;
	 
	 public Articulocombo(int codArticulo,String nombre) {
		 this.codArticulo=codArticulo;
		 this.nombre=nombre;
	 }
	 
	public int getCodArticulo() {
		return codArticulo;
	}
	public void setCodArticulo(int codArticulo) {
		this.codArticulo = codArticulo;
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
