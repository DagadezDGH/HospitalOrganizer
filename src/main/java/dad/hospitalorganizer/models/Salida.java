package dad.hospitalorganizer.models;

import java.sql.Date;

public class Salida {
	 int codSalida;
	 String lugar;
	 String motivo;
	 String codPaciente;
	 String codPersonal;
	 Date fechaSalida;
	 int comprobar;
	 
	 public Salida(int codSalida,  String lugar, String motivo, String codPaciente,  String codPersonal, Date fechaSalida, int comprobar) {
			this.codSalida=codSalida;
			this.lugar=lugar;
			this.motivo =motivo;
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
	public String getCodPersonal() {
		return codPersonal;
	}
	public void setCodPersonal(String codPersonal) {
		this.codPersonal = codPersonal;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
}
