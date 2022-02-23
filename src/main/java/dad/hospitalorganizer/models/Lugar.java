package dad.hospitalorganizer.models;

public class Lugar {
	private int codLugar;
	private String nombre;
	
	public Lugar(int cod, String lugar) {
		this.codLugar=cod;
		this.nombre=lugar;
	}

	public int getCodLugar() {
		return codLugar;
	}

	public void setCodLugar(int codLugar) {
		this.codLugar = codLugar;
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
