package cl.duoc.demo.controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
public class EstadoController {
 @GetMapping("/api/publico/estado") public Map<String,String> estado() {return Map.of("estado","OK");}
}
