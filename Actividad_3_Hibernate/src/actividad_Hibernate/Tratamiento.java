package actividad_Hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tratamiento")
public class Tratamiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTratamiento")
    private Integer idTratamiento;

    @Column(name = "coste")
    private String coste;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cita_idCita")
    private Integer cita_idCita;

    public Tratamiento() {
    }

    public Tratamiento(String coste, String nombre, Integer cita_idCita) {
        this.coste = coste;
        this.nombre = nombre;
        this.cita_idCita = cita_idCita;
    }

    public Integer getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(Integer idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public String getCoste() {
        return coste;
    }

    public void setCoste(String coste) {
        this.coste = coste;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCita_idCita() {
        return cita_idCita;
    }

    public void setCita_idCita(Integer cita_idCita) {
        this.cita_idCita = cita_idCita;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTratamiento != null ? idTratamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tratamiento)) {
            return false;
        }
        Tratamiento other = (Tratamiento) object;
        return (this.idTratamiento != null || other.idTratamiento == null)
                && (this.idTratamiento == null || this.idTratamiento.equals(other.idTratamiento));
    }

    @Override
    public String toString() {
        return String.format(
                "Tratamiento:\nID: %d\nCoste: %f\nNombre: %s\nCita ID: %d",
                idTratamiento, coste, nombre, cita_idCita
        );
    }

}
