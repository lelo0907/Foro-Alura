package com.alura.foro.ApiForo.service;



import com.alura.foro.ApiForo.model.Usuario;
import com.alura.foro.ApiForo.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final SecretKey secretKey; // Clave segura para firmar tokens JWT


    public AuthService(UsuarioRepository usuarioRepository, @Value("${jwt.secret}") String jwtSecret) {
        this.usuarioRepository = usuarioRepository;

        // Convierte la clave de texto en un SecretKey válido
        if (jwtSecret == null || jwtSecret.length() < 32) {
            throw new IllegalArgumentException("La clave jwt.secret debe tener al menos 32 caracteres");
        }
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }


    public String authenticate(String username, String password) {
        // Busca al usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verifica que la contraseña proporcionada coincida con la almacenada
        if (!BCrypt.checkpw(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Genera y retorna el token JWT
        return Jwts.builder()
                .setSubject(username) // El nombre del usuario autenticado
                .claim("id", usuario.getId())
                .setIssuedAt(new Date()) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Expira en 1 hora
                .signWith(secretKey, SignatureAlgorithm.HS256) // Firma con HS256 y la clave secreta
                .compact();
    }
}
