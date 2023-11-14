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

    @Id
    @Column(name = "idMaterial")
    private Integer idMaterial;

    // RELACIÓN
    @ManyToOne
    @JoinColumn(name = "idProveedorFK", nullable = false, foreignKey = @ForeignKey(name = "idProveedorFK"))
    private Proveedor proveedor;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "cantidad")
    private Integer cantidad;

    public Stock() {
    }

    public Stock(Integer idMaterial, Proveedor proveedor, String tipo, Integer cantidad) {
        this.idMaterial = idMaterial;
        this.proveedor = proveedor;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

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

    @Override
    public String toString() {
        return String.format(
                "ID Material: %d\nProveedor: %s\nTipo: %s\nCantidad: %d",
                idMaterial, proveedor.getNombre(), tipo, cantidad
        );
    }
}
