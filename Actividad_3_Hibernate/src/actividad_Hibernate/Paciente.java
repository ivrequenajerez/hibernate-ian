package actividad_Hibernate;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    // RELACIÓN
    @OneToMany(mappedBy = "paciente")
    @Cascade(value = CascadeType.ALL)
    private Set<Cita> citas = new HashSet<>();

    public Paciente() {
    }

    // Constructor básico para crear un paciente con nombre, apellidos y teléfono
    public Paciente(String nombre, String apellidos, String teléfono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.teléfono = teléfono;
    }

    // Constructor completo que incluye dirección y última consulta
    public Paciente(String nombre, String apellidos, String dirección, String teléfono, java.sql.Date ultimaConsulta) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dirección = dirección;
        this.teléfono = teléfono;
        this.ultimaConsulta = ultimaConsulta;
    }

    // Métodos getter y setter para cada atributo

    public Set<Cita> getCitas() {
        return citas;
    }

    // Método para añadir una cita al conjunto de citas del paciente
    public void addCita(Cita cita, Session session) {
        if (citas == null) {
            citas = new HashSet<>();
        }
        citas.add(cita);
        cita.setPaciente(this);

        try {
            session.beginTransaction();
            session.save(cita);
            session.getTransaction().commit();
            System.out.println("Cita añadida y guardada exitosamente en la base de datos.");
        } catch (Exception e) {
            System.err.println("Error al guardar la cita en la base de datos. Error: " + e.getMessage());
            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d\nNombre: %s %s\nDirección: %s\nTeléfono: %s\nÚltima Consulta: %s",
                idPaciente, nombre, apellidos, dirección, teléfono, ultimaConsulta
        );
    }
}
