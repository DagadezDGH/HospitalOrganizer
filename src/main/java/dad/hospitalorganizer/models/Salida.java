package dad.hospitalorganizer.models;

import java.sql.Date;

public class Salida {
	 int codSalida;
	 String lugar;
	 String motivo;
	 String codPaciente;
	 int codPersonal;
	 Date fechaSalida;
	 boolean comprobar;
	 public Salida(int codSalida,  String lugar, String codPaciente,  int codPersonal, Date fechaSalida, boolean comprobar) {
			this.codSalida=codSalida;
			this.lugar=lugar;
			this.codPaciente=codPaciente;
			this.codPersonal=codPersonal;
			this.fechaSalida= fechaSalida;
			this.comprobar=comprobar;
		}
	 public Salida(int codSalida,  String lugar, String motivo, String codPaciente, Date fechaSalida) {
			this.codSalida=codSalida;
			this.lugar=lugar;
			this.motivo =motivo;
			this.codPaciente=codPaciente;
			this.fechaSalida= fechaSalida;
		}
	 public int getCodSalida() {
		return codSalida;
	}
	public void setCodSalida(int codSalida) {
		this.codSalida = codSalida;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getCodPaciente() {
		return codPaciente;
	}
	public void setCodPaciente(String codPaciente) {
		this.codPaciente = codPaciente;
	}
	public int getCodPersonal() {
		return codPersonal;
	}
	public void setCodPersonal(int codPersonal) {
		this.codPersonal = codPersonal;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public boolean isComprobar() {
		return comprobar;
	}
	public void setComprobar(boolean comprobar) {
		this.comprobar = comprobar;
	}
}
