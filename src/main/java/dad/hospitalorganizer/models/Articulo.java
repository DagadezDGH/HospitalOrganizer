package dad.hospitalorganizer.models;

public class Articulo {
 int codArticulo;
 String nombre;
 String descripcion;
 String ubicacion;
 String proveedor;
 int cantidad;
 
 
public Articulo(int codArticulo, String nombre, String descripcion, String ubicacion, int cantidad, String proveedor) {
	this.codArticulo=codArticulo;
	this.nombre=nombre;
	this.descripcion=descripcion;
	this.ubicacion=ubicacion;
	this.cantidad=cantidad;
	this.proveedor= proveedor;
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
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
public String getUbicacion() {
	return ubicacion;
}
public void setUbicacion(String ubicacion) {
	this.ubicacion = ubicacion;
}
public int getCantidad() {
	return cantidad;
}
public void setCantidad(int cantidad) {
	this.cantidad = cantidad;
}
public String getProveedor() {
	return proveedor;
}
public void setProveedor(String proveedor) {
	this.proveedor = proveedor;
}
}
