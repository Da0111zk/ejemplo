package cl.duoc.demo.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
public class Producto {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id; // null antes de guardar; JPA asigna el identificador.
 @Column(nullable=false)
 private String nombre;
 @Column(nullable=false, precision=12, scale=2)
 private BigDecimal precio;
 public Producto() {}
 public Producto(String nombre, BigDecimal precio) { this.nombre=nombre; this.precio=precio; }
 public Long getId() {return id;}
 public String getNombre() {return nombre;}
 public void setNombre(String valor) {this.nombre=valor;}
 public BigDecimal getPrecio() {return precio;}
 public void setPrecio(BigDecimal valor) {this.precio=valor;}
}
