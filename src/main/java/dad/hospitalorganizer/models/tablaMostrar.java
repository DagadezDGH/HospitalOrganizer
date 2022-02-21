package dad.hospitalorganizer.models;

public class tablaMostrar {
		int codSalidaArticulo;
		int codArticulo;
		int cantidad;
		String nomArticulo;
		String lugar;

		public tablaMostrar(int codArticulo, int cantidad,String nomArticulo,String lugar) {
			this.codArticulo = codArticulo;
			this.cantidad = cantidad;
			this.nomArticulo=nomArticulo;
			this.lugar=lugar;
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

		public String getLugar() {
			return lugar;
		}

		public void setLugar(String lugar) {
			this.lugar = lugar;
		}
	}

