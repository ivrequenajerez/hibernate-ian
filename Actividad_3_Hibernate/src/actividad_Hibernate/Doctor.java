package actividad_Hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor implements Serializable {

    // Identificador único del doctor
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDoctor")
    private Integer idDoctor;

    // Nombre del doctor
    @Column(name = "nombre")
    private String nombre;

    // Apellidos del doctor
    @Column(name = "apellidos")
    private String apellidos;

    // Teléfono del doctor
    @Column(name = "telefono")
    private String telefono;

    // Dirección del doctor
    @Column(name = "direccion")
    private String direccion;

    // RELACIÓN: Uno a uno con Especialidad
    @OneToOne
    @JoinColumn(name = "idEspecialidad_FK", nullable = false)
    private Especialidad especialidad;

    // Salario del doctor
    @Column(name = "salario")
    private Integer salario;

    // Correo electrónico del doctor
    @Column(name = "email")
    private String email;

    // Constructor por defecto
    public Doctor() {
    }

    // Constructor con parámetros para inicializar el doctor
    public Doctor(String nombre, String apellidos, String telefono, String direccion,
                  Especialidad especialidad, Integer salario, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.especialidad = especialidad;
        this.salario = salario;
        this.email = email;
    }

    // Getter y Setter para idDoctor
    public Integer getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Integer idDoctor) {
        this.idDoctor = idDoctor;
    }

    // Métodos getter y setter para cada atributo

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Representación en formato de cadena del doctor
    @Override
    public String toString() {
        return String.format(
                "ID Doctor: %d\nNombre: %s %s\nTeléfono: %s\nDirección: %s\nEspecialidad: %s\nSalario: %d\nEmail: %s",
                idDoctor, nombre, apellidos, telefono, direccion, especialidad.getTipo(), salario, email
        );
    }

    // Método para establecer el ID del doctor
    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }
}
