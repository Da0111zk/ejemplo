# Versión final de respaldo — productos con JWT y MySQL

Este es el mismo proyecto de 01_inicio, con Dockerfile, docker-compose.yml y .dockerignore ya incorporados.

## Ejecución directa (PowerShell)
Requiere Docker Desktop iniciado. Maven y Java 21 se descargan como imágenes; no necesitas compilarlos desde Windows. Detener la API local y cualquier contenedor que ocupe 3309.
Desde esta carpeta:
```powershell
docker compose up -d --build
docker compose ps
docker compose logs -f api
```
Esperar `Started Application`; importar postman/Demo.postman_collection.json y ejecutar en orden.
API: http://localhost:8082. Login: POST /auth/login con {"username":"admin","password":"1234"}.
JWT dura 5 minutos. MySQL desde Windows: localhost:3309, base productos_docker_base, demo / Demo123!.
El usuario de la API es didáctico y reside en memoria; los productos persisten en MySQL.

No ejecutar a la vez 01_inicio y 03_final: son dos estados del mismo proyecto, comparten puertos y nombre Compose.
Linux/macOS: chmod +x mvnw y usar ./mvnw en lugar de .\mvnw.cmd.
Para detener: docker compose down. El volumen conserva los datos; down -v los elimina deliberadamente.
Guía completa: ../GUIA_CLASE.md. Alcance de las pruebas: ../VERIFICACION.md.
Credenciales y configuración exclusivamente de laboratorio local.

## Compilación automática y variables
El Dockerfile ejecuta `mvn -B clean package -DskipTests` en la etapa Maven/JDK 21.
Luego copia el JAR a la etapa Java 21 JRE y lo ejecuta al iniciar el contenedor.
`-DskipTests` omite la ejecución de pruebas durante la construcción; para ejecutarlas
por separado usa `./mvnw test` o `.\mvnw.cmd test` con Java 21 local.
No necesitas tener `target` ni un JAR previo. Ante cambios: `docker compose up -d --build`.
Docker puede reutilizar capas sin cambios. Para forzar toda la compilación:
`docker compose build --no-cache api`, seguido de `docker compose up -d`.

El archivo `.env` incluido debe permanecer junto a `docker-compose.yml`.
Contiene MYSQL_ROOT_PASSWORD=RootDemo123! y DB_PASSWORD=Demo123!.
Compose reemplaza las expresiones `${...}`; las variables del terminal tienen prioridad.
La contraseña root inicial es RootDemo123!; la API usa demo / Demo123!.
Cambiar `.env` no cambia las contraseñas de un volumen MySQL ya inicializado.
`.env` queda excluido de Git y del contexto Docker. `.env.example` sirve de modelo.

## Cuatro o más microservicios
Abrir `alumnos_microservicios/LEEME.txt`: contiene una plantilla de Compose
para los proyectos de los alumnos. Deben agregar sus proyectos antes de ejecutarla.
