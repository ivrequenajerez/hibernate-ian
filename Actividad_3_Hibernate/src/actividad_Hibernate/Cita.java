package actividad_Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cita")
public class Cita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCita")
    private Integer idCita;
    
    // RELACIÓN
    @ManyToOne
    @JoinColumn(name = "idPaciente_FK")
    private Paciente paciente;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "hora")
    private String hora;

    @Column(name = "motivo")
    private String motivo;
    
    /*
    @Column(name = "idPaciente_FK")
    private Integer paciente;
    */
    
    @Column(name = "idDoctor_FK")
    private String idDoctor;

    public Cita() {
    }

    public Cita(Date fecha, String hora, String motivo, Paciente paciente, String idDoctor) {
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.paciente = paciente;
        this.idDoctor = idDoctor;
    }

	public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(String idDoctor) {
        this.idDoctor = idDoctor;
    }

    @Override
    public String toString() {
        return String.format(
                "ID Cita: %d\nFecha: %s\nHora: %s\nMotivo: %s\nPaciente: %s\nID Doctor: %s",
                idCita, fecha, hora, motivo, paciente, idDoctor
        );
    }
}
