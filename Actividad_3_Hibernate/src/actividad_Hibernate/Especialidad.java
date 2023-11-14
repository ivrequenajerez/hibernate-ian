package actividad_Hibernate;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "especialidad")
public class Especialidad implements Serializable {

    // Identificador único de la especialidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEspecialidad")
    private Integer idEspecialidad;

    // Tipo de especialidad, no puede ser nulo
    @Column(name = "tipo", nullable = false)
    private String tipo;

    // Descripción de la especialidad, no puede ser nula
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    // Nueva relación uno a uno bidireccional con Doctor
    // mappedBy se refiere al atributo 'especialidad' en la clase Doctor
    @OneToOne(mappedBy = "especialidad", cascade = CascadeType.ALL, orphanRemoval = true)
    private Doctor doctor;

    // Constructor por defecto
    public Especialidad() {
    }

    // Constructor con parámetros para inicializar la especialidad
    public Especialidad(String tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    // Métodos getter y setter para cada atributo

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    // Representación en formato de cadena de la especialidad
    @Override
    public String toString() {
        return String.format(
                "ID Especialidad: %d\nTipo: %s\nDescripción: %s",
                idEspecialidad, tipo, descripcion
        );
    }
}
