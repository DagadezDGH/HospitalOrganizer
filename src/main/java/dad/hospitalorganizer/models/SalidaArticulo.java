package dad.hospitalorganizer.models;

public class SalidaArticulo {
	 int codSalidaArticulo;
	 int codArticulo;
	 int codSalida;
	 int cantidad;
	 public SalidaArticulo(int codSalidaArticulo, int codArticulo, int codSalida, int cantidad) {
			this.codArticulo=codArticulo;
			this.codSalidaArticulo=codSalidaArticulo;
			this.codSalida=codSalida;
			this.cantidad=cantidad;
		}
	 public int getCodSalidaArticulo() {
		return codSalidaArticulo;
	}
	public void setCodSalidaArticulo(int codSalidaArticulo) {
		this.codSalidaArticulo = codSalidaArticulo;
	}
	public int getCodArticulo() {
		return codArticulo;
	}
	public void setCodArticulo(int codArticulo) {
		this.codArticulo = codArticulo;
	}
	public int getCodSalida() {
		return codSalida;
	}
	public void setCodSalida(int codSalida) {
		this.codSalida = codSalida;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
