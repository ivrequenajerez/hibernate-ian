package actividad_Hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    // Identificador único del usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    // Nombre del usuario
    @Column(name = "Nombre")
    private String nombre;

    // Contraseña del usuario
    @Column(name = "contraseña")
    private String contraseña;

    // Rol del usuario
    @Column(name = "rol")
    private String rol;

    // Constructor por defecto
    public Usuario() {
    }

    // Constructor con parámetros para inicializar el usuario
    public Usuario(String nombre, String contraseña, String rol) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Métodos getter y setter para cada atributo

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Representación en formato de cadena del usuario
    @Override
    public String toString() {
        return String.format(
                "ID Usuario: %d\nNombre: %s\nContraseña: %s\nRol: %s",
                idUsuario, nombre, contraseña, rol
        );
    }
}
