package cl.duoc.demo.service;
import cl.duoc.demo.model.Producto;
import cl.duoc.demo.dto.ProductoRequest;
import cl.duoc.demo.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@Service
@Transactional
public class ProductoService {
 private final ProductoRepository repository;
 public ProductoService(ProductoRepository repository) {this.repository=repository;}
 @Transactional(readOnly=true)
 public List<Producto> listar() {return repository.findAll();}
 @Transactional(readOnly=true)
 public Producto buscar(Long id) {return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Registro no encontrado"));}
 public Producto crear(ProductoRequest datos) {return repository.save(new Producto(datos.nombre(),datos.precio()));}
 public Producto actualizar(Long id,ProductoRequest datos) {
  Producto actual=buscar(id); actual.setNombre(datos.nombre()); actual.setPrecio(datos.precio());
  return repository.save(actual);
 }
 public void eliminar(Long id) {repository.delete(buscar(id));}
}
