package cl.duoc.demo.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ProductoRequest(@NotBlank @Size(max=255) String nombre, @NotNull @DecimalMin("0.01") @Digits(integer=10, fraction=2) BigDecimal precio) {}
