package actividad_Hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock implements Serializable {

    // Identificador único del material
    @Id
    @Column(name = "idMaterial")
    private Integer idMaterial;

    // RELACIÓN: Muchos stocks pueden tener un proveedor, utilizando una relación Many-to-One
    @ManyToOne
    @JoinColumn(name = "idProveedorFK", nullable = false, foreignKey = @ForeignKey(name = "idProveedorFK"))
    private Proveedor proveedor;

    // Tipo del material
    @Column(name = "tipo")
    private String tipo;

    // Cantidad del material en stock
    @Column(name = "cantidad")
    private Integer cantidad;

    // Constructor por defecto
    public Stock() {
    }

    // Constructor con parámetros para inicializar el stock
    public Stock(Integer idMaterial, Proveedor proveedor, String tipo, Integer cantidad) {
        this.idMaterial = idMaterial;
        this.proveedor = proveedor;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    // Métodos getter y setter para cada atributo

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    // Representación en formato de cadena del stock
    @Override
    public String toString() {
        return String.format(
                "ID Material: %d\nProveedor: %s\nTipo: %s\nCantidad: %d",
                idMaterial, proveedor.getNombre(), tipo, cantidad
        );
    }
}
