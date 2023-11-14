package actividad_Hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "proveedor")
public class Proveedor implements Serializable {

    // Identificador único del proveedor
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProveedor")
    private Integer idProveedor;

    // Nombre del proveedor, no puede ser nulo
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Teléfono del proveedor
    @Column(name = "telefono", nullable = false)
    private int telefono;

    // Correo del proveedor, no puede ser nulo
    @Column(name = "correo", nullable = false)
    private String correo;

    // Dirección del proveedor, no puede ser nula
    @Column(name = "direccion", nullable = false)
    private String direccion;

    // Constructor por defecto
    public Proveedor() {
    }

    // Constructor con parámetros para inicializar el proveedor
    public Proveedor(String nombre, int telefono, String correo, String direccion) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    // Métodos getter y setter para cada atributo

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Representación en formato de cadena del proveedor
    @Override
    public String toString() {
        return String.format(
                "ID Proveedor: %d\nNombre: %s\nTeléfono: %d\nCorreo: %s\nDirección: %s",
                idProveedor, nombre, telefono, correo, direccion
        );
    }
}
