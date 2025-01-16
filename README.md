# API Foro - Gestión de Tópicos

Este proyecto es una API desarrollada con **Spring Boot** y **PostgreSQL** para gestionar un foro donde los usuarios pueden crear, actualizar, listar y eliminar tópicos.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.3.7**
- **PostgreSQL**
- **Maven**
- **JWT (JSON Web Tokens)** para autenticación
- **Postman** para pruebas

## Requisitos Previos

- **Java 17** instalado
- **PostgreSQL** configurado con una base de datos llamada `foro`
- Configurar las propiedades en el archivo `application.properties` con las credenciales de tu base de datos y el secreto JWT:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/foro
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
jwt.secret=clave_secreta_con_más_de_32_caracteres
