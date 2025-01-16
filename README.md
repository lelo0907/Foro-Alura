Foro API

Este proyecto es una API REST para la gestión de tópicos en un foro. Proporciona funcionalidades como autenticación de usuarios mediante tokens JWT, creación, lectura, actualización y eliminación de tópicos. Está desarrollado en Java utilizando Spring Boot y usa PostgreSQL como base de datos.

Tecnologías Utilizadas

Java 17

Spring Boot 3.3.7

Maven

PostgreSQL

JWT (Json Web Tokens) para autenticación

Lombok para reducir código repetitivo

Postman para pruebas de API

Estructura del Proyecto

com.alura.foro
|-- ApiForoApplication.java
|-- controller
|   |-- TopicoController.java
|-- model
|   |-- Usuario.java
|   |-- Topico.java
|-- repository
|   |-- UsuarioRepository.java
|   |-- TopicoRepository.java
|-- service
|   |-- AuthService.java
|   |-- TopicoService.java

Configuración Inicial

Requisitos Previos

Java 17 instalado

PostgreSQL configurado

Maven instalado

Configuración de la Base de Datos

Crear una base de datos en PostgreSQL:

CREATE DATABASE foro_api;

Crear las tablas necesarias:

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20)
);

CREATE TABLE topicos (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    curso VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL,
    usuario_id INT REFERENCES usuarios(id)
);

Configurar las credenciales de la base de datos en application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/foro_api
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

jwt.secret=clave_secreta_jwt_con_32_caracteres_o_más

Endpoints Disponibles

Autenticación

1. Login

POST /auth/login

Body:

{
    "username": "usuario1",
    "password": "contraseña1"
}

Respuesta Exitosa (200):

{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}

Tópicos

2. Crear Tópico

POST /topicos

Headers:

Authorization: Bearer <tu_token>

Body:

{
    "titulo": "Nuevo Tópico",
    "mensaje": "Este es el mensaje del tópico",
    "curso": "Java"
}

Respuesta Exitosa (200):

{
    "id": 1,
    "titulo": "Nuevo Tópico",
    "mensaje": "Este es el mensaje del tópico",
    "curso": "Java",
    "fechaCreacion": "2025-01-16T20:00:00",
    "usuarioId": 1
}

3. Listar Tópicos

GET /topicos

Headers:

Authorization: Bearer <tu_token>

Respuesta Exitosa (200):

[
    {
        "id": 1,
        "titulo": "Nuevo Tópico",
        "mensaje": "Este es el mensaje del tópico",
        "curso": "Java",
        "fechaCreacion": "2025-01-16T20:00:00",
        "usuarioId": 1
    }
]

4. Actualizar Tópico

PUT /topicos/{id}

Headers:

Authorization: Bearer <tu_token>

Body:

{
    "titulo": "Tópico Actualizado",
    "mensaje": "Mensaje actualizado",
    "curso": "Spring Boot"
}

Respuesta Exitosa (200):

{
    "id": 1,
    "titulo": "Tópico Actualizado",
    "mensaje": "Mensaje actualizado",
    "curso": "Spring Boot",
    "fechaCreacion": "2025-01-16T20:00:00",
    "usuarioId": 1
}

5. Eliminar Tópico

DELETE /topicos/{id}

Headers:

Authorization: Bearer <tu_token>

Respuesta Exitosa (204): Sin contenido.

Seguridad

Esta API utiliza JWT para autenticar y autorizar las solicitudes. Cada usuario debe iniciar sesión para obtener un token, que debe incluirse en las cabeceras de las peticiones protegidas.
