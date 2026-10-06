-- Ejecutar como administrador en MySQL instalado localmente, si no usarás el contenedor.
CREATE DATABASE IF NOT EXISTS productos_docker_base;
CREATE USER IF NOT EXISTS 'demo'@'%' IDENTIFIED BY 'Demo123!';
GRANT ALL PRIVILEGES ON productos_docker_base.* TO 'demo'@'%';
-- Si demo ya existe, verifica su contraseña. CREATE USER IF NOT EXISTS no la cambia.
-- Las tablas las crea JPA al iniciar la API.
