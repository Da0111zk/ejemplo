# Primitivos, wrappers y entidades JPA
`int` es primitivo; `Integer` es una clase envoltorio y admite null. Igual sucede con boolean/Boolean y long/Long. String es una clase y no tiene equivalente primitivo. Java distingue mayúsculas: no es INTEGER ni BOOLEAN.

| Declaración de atributo | Valor inicial | Significado |
|---|---|---|
| int edad; | 0 | Siempre contiene un entero |
| Integer edad; | null | Puede distinguir cero de no informado |
| boolean activo; | false | Dos estados |
| Boolean activo; | null | Puede representar verdadero, falso o no informado |
| String nombre; | null | Texto; no tiene primitivo equivalente |

Estos valores iniciales corresponden a atributos y elementos de arreglos, no a variables locales sin inicializar.

JPA admite primitivos y wrappers. Long es práctico para un ID autogenerado: antes de persistir es null. Una columna nullable puede representarse con un wrapper; si el dominio no admite ausencia se puede usar un primitivo o validar un wrapper con @NotNull.
Un DTO con Integer y @NotNull distingue un campo omitido de cero. El cero debe validarse aparte si no es permitido, por ejemplo con @Positive.

En productos el precio usa BigDecimal para trabajar con valores decimales exactos. Double también es wrapper, pero mantiene la representación binaria aproximada de double; envolverlo no corrige la precisión monetaria.

List<Integer> es válido; List<int> no: los genéricos requieren tipos de referencia.
Integer numero = null; int copia = numero; lanza NullPointerException por autounboxing.
Para comparar wrappers se usa equals u Objects.equals, no == para comparar su valor.

Conclusión: no se usan wrappers obligatoriamente por estar en model; se eligen según ausencia de datos, validación y significado del dominio.
