package com.alura.foro.ApiForo.controller;



import com.alura.foro.ApiForo.model.Topico;
import com.alura.foro.ApiForo.service.TopicoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<Topico> crearTopico(@RequestBody Map<String, String> request,
                                              @RequestHeader("Authorization") String token) {
        // Validar el token JWT
        String jwt = token.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        Integer usuarioId = claims.get("id", Integer.class);

        // Crear el tópico
        Topico topico = topicoService.crearTopico(
                request.get("titulo"),
                request.get("mensaje"),
                request.get("curso"),
                Long.valueOf(usuarioId)  // Se pasa el id del usuario desde el token
        );

        return ResponseEntity.ok(topico);


    }

    // Nuevo endpoint GET para obtener todos los tópicos
    @GetMapping
    public ResponseEntity<List<Topico>> obtenerTodosLosTopicos() {
        List<Topico> topicos = topicoService.obtenerTodosLosTopicos();
        return ResponseEntity.ok(topicos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id,
                                                   @RequestBody Map<String, String> request,
                                                   @RequestHeader("Authorization") String token) {
        // Validar el token JWT
        String jwt = token.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        Integer usuarioId = claims.get("id", Integer.class);

        // Verificar si el tópico pertenece al usuario que intenta actualizarlo
        Topico topicoExistente = topicoService.obtenerTopicoPorId(id);
        if (topicoExistente == null) {
            return ResponseEntity.notFound().build(); // Si no existe, devolver 404
        }

        if (!topicoExistente.getUsuarioId().equals(Long.valueOf(usuarioId))) {
            return ResponseEntity.status(403).build(); // Si no es el propietario, devolver 403 Forbidden
        }

        // Actualizar el tópico
        Topico topicoActualizado = topicoService.actualizarTopico(id, request.get("titulo"), request.get("mensaje"), request.get("curso"));

        return ResponseEntity.ok(topicoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarTopico(@PathVariable Long id,
                                             @RequestHeader("Authorization") String token) {
        // Validar el token JWT
        String jwt = token.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        Integer usuarioId = claims.get("id", Integer.class); // Extraer el usuario ID del token

        // Verificar que el tópico exista y que pertenezca al usuario autenticado
        boolean eliminado = topicoService.borrarTopico(id, usuarioId.longValue());
        if (!eliminado) {
            return ResponseEntity.status(403).build(); // Retorna 403 Forbidden si no tiene permisos
        }

        return ResponseEntity.noContent().build(); // Retorna 204 No Content si se borra con éxito
    }


}
