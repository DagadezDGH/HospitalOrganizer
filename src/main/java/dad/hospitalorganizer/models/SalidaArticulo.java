package dad.hospitalorganizer.models;

public class SalidaArticulo {
	int codSalidaArticulo;
	int codSalida;
	int codArticulo;
	int cantidad;
	String nomArticulo;
	String nombreProveedor;

	public SalidaArticulo(int codArticulo, int cantidad,String nomArticulo,String nombreProveedor) {
		this.codArticulo = codArticulo;
		this.cantidad = cantidad;
		this.nomArticulo=nomArticulo;
		this.nombreProveedor=nombreProveedor;
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

	public String getNomArticulo() {
		return nomArticulo;
	}

	public void setNomArticulo(String nomArticulo) {
		this.nomArticulo = nomArticulo;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
}
