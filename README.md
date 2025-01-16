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
- **PostgreSQL** configurado con una base de datos llamada `postgres`
- Configurar las propiedades en el archivo `application.properties` con las credenciales de tu base de datos y el secreto JWT:

## properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
jwt.secret=clave_secreta_con_más_de_32_caracteres

## Funcion

En la aplicacion podemos ver que se configuro cada uno de los metodos solicitados para la consulta actualizacion y borrado de la data en la base de datos.

Bases de Datos:

Tenemos dos bases de datos creadas en Postgres:

![image](https://github.com/user-attachments/assets/fad8c385-a695-4df3-9a82-d9dbcde1ae8d)


## Autenticacion

Se realiza la Consulta a la Base de datos llamada Usuarios, se verifica que el usuario exista y se encripta la clave enviada en la solicitud ya que en la base solo se guarda la data encriptada. Se recibe como respueta 200 y el token para poder realizar modificaciones en la base.

![image](https://github.com/user-attachments/assets/113c8c0a-1e1c-49bf-9cbb-d53a7f7b56b7)


![image](https://github.com/user-attachments/assets/39126fe5-7942-4fc5-9fe5-77aa0b034bd5)


## Consulta

Se envia la peticion para solicitar todos los topicos de la registrados en la base de datos, este requerimiento no pide Token.

![image](https://github.com/user-attachments/assets/7aab68b6-0c76-45cf-9d50-4d8f0c24a5e4)

## insertar nuevo tema

Esta solicitud requiere de Token bearer para poder hacer modificaciones en la base, se puede enviar la data para generar un nuevo topico.

![image](https://github.com/user-attachments/assets/3d7cf7a7-c0f9-4038-b735-6d4a160c0860)



## Actualizar y borrar

permite actualizar o eliminar un topico que ya esta registrado en la base, para lo cual hace dos validadiones la primera solicita token de autentiocacion, y la segunda valida que el usuario que creo el topico sea el mismo que lo elimina para evitar el otro usuario lo haga.


![image](https://github.com/user-attachments/assets/31686604-3c06-44b5-995e-e4e9b63af227)


![image](https://github.com/user-attachments/assets/46ca7f85-aab0-4048-b609-e97a2c0230f5)

 






 
