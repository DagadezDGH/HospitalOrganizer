package dad.hospitalorganizer.models;

import java.sql.Date;

public class EntradaArticulo {
	 int codEntradaArticulo;
	 int codArticulo;
	 int codEntrada;
	 int cantidad;
	 Date caducidad;
	 String nombreArticulo;

	public EntradaArticulo(int codEntradaArticulo, int codArticulo, int codEntrada, int cantidad, Date caducidad) {
			this.codArticulo=codArticulo;
			this.codEntradaArticulo=codEntradaArticulo;
			this.codEntrada=codEntrada;
			this.caducidad=caducidad;
			this.cantidad=cantidad;
		}
	 public EntradaArticulo(int codArticulo, String nombreArticulo, int cantidad, Date caducidad) {
		    this.codArticulo = codArticulo;
		    this.nombreArticulo=nombreArticulo;
			this.caducidad=caducidad;
			this.cantidad=cantidad;
	}
	 public String getNombreArticulo() {
		return nombreArticulo;
	}
	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}
	public int getCodEntradaArticulo() {
		return codEntradaArticulo;
	}
	public void setCodEntradaArticulo(int codEntradaArticulo) {
		this.codEntradaArticulo = codEntradaArticulo;
	}
	public int getCodArticulo() {
		return codArticulo;
	}
	public void setCodArticulo(int codArticulo) {
		this.codArticulo = codArticulo;
	}
	public int getCodEntrada() {
		return codEntrada;
	}
	public void setCodEntrada(int codEntrada) {
		this.codEntrada = codEntrada;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public Date getCaducidad() {
		return caducidad;
	}
	public void setCaducidad(Date caducidad) {
		this.caducidad = caducidad;
	}
}
