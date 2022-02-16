package dad.hospitalorganizer.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usuario {
	StringProperty dni = new SimpleStringProperty();
	StringProperty contraseña = new SimpleStringProperty();
	StringProperty nombre = new SimpleStringProperty();
	
	public final StringProperty dniProperty() {
		return this.dni;
	}
	
	public final String getDni() {
		return this.dniProperty().get();
	}
	
	public final void setDni(final String dni) {
		this.dniProperty().set(dni);
	}
	
	public final StringProperty contraseñaProperty() {
		return this.contraseña;
	}
	
	public final String getContraseña() {
		return this.contraseñaProperty().get();
	}
	
	public final void setContraseña(final String contraseña) {
		this.contraseñaProperty().set(contraseña);
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}
	

	public final String getNombre() {
		return this.nombreProperty().get();
	}
	

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}
	
	
	
}
