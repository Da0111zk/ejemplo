package cl.duoc.demo.controller;
import cl.duoc.demo.model.Producto;
import cl.duoc.demo.dto.ProductoRequest;
import cl.duoc.demo.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/api/productos")
public class ProductoController {
 private final ProductoService service;
 public ProductoController(ProductoService service) {this.service=service;}
 @GetMapping public List<Producto> listar() {return service.listar();}
 @GetMapping("/{id}") public Producto buscar(@PathVariable Long id) {return service.buscar(id);}
 @PostMapping public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoRequest datos) {
  Producto creado=service.crear(datos);
  return ResponseEntity.created(URI.create("/api/productos/"+creado.getId())).body(creado);
 }
 @PutMapping("/{id}") public Producto actualizar(@PathVariable Long id,@Valid @RequestBody ProductoRequest datos) {return service.actualizar(id,datos);}
 @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) {service.eliminar(id);return ResponseEntity.noContent().build();}
}
