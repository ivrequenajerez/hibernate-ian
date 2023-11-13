package actividad_Hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPaciente")
	private Integer idPaciente;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "dirección")
	private String dirección;

	@Column(name = "teléfono")
	private String teléfono;

	@Column(name = "ultimaConsulta")
	private java.sql.Date ultimaConsulta;

	public Paciente() {
	}

	public Paciente(String nombre, String apellidos, String teléfono) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.teléfono = teléfono;
	}

	public Paciente(String nombre, String apellidos, String dirección, String teléfono, java.sql.Date ultimaConsulta) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dirección = dirección;
		this.teléfono = teléfono;
		this.ultimaConsulta = ultimaConsulta;
	}

	public Integer getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDirección() {
		return dirección;
	}

	public void setDirección(String dirección) {
		this.dirección = dirección;
	}

	public String getTeléfono() {
		return teléfono;
	}

	public void setTeléfono(String teléfono) {
		this.teléfono = teléfono;
	}

	public java.sql.Date getUltimaConsulta() {
		return ultimaConsulta;
	}

	public void setUltimaConsulta(java.sql.Date ultimaConsulta) {
		this.ultimaConsulta = ultimaConsulta;
	}

	// Resto de los métodos hashCode, equals, toString
}
