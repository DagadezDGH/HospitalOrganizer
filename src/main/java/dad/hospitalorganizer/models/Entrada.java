package dad.hospitalorganizer.models;

import java.sql.Date;

public class Entrada {
	 int codEntrada;
	 int codProveedor;
	 String dni;
	 Date fecha;
	 boolean comprobar;
	 String proveedor;

	public Entrada(int codEntrada, int codProveedor, String dni, Date fecha, boolean comprobar) {
			this.codEntrada=codEntrada;
			this.codProveedor=codProveedor;
			this.dni=dni;
			this.fecha=fecha;
			this.comprobar=comprobar;
		}
	 public Entrada(int codEntrada, String nombre,Date fecha) {
			this.codEntrada=codEntrada;
			this.proveedor=nombre;
			this.fecha=fecha;
		}
	 public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public int getCodEntrada() {
		return codEntrada;
	}
	public void setCodEntrada(int codEntrada) {
		this.codEntrada = codEntrada;
	}
	public int getCodProveedor() {
		return codProveedor;
	}
	public void setCodProveedor(int codProveedor) {
		this.codProveedor = codProveedor;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isComprobar() {
		return comprobar;
	}
	public void setComprobar(boolean comprobar) {
		this.comprobar = comprobar;
	}
}
