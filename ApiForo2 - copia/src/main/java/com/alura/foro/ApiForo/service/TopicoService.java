package com.alura.foro.ApiForo.service;



import com.alura.foro.ApiForo.model.Topico;
import com.alura.foro.ApiForo.repository.TopicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;

    public TopicoService(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    public Topico crearTopico(String titulo, String mensaje, String curso, Long usuarioId) {
        Topico topico = new Topico();
        topico.setTitulo(titulo);
        topico.setMensaje(mensaje);
        topico.setCurso(curso);
        topico.setFechaCreacion(LocalDateTime.now());
        topico.setUsuarioId(usuarioId);

        return topicoRepository.save(topico);
    }

    public List<Topico> obtenerTodosLosTopicos() {
        return topicoRepository.findAll(); // Esto devuelve todos los registros de la tabla 'topicos'
    }

    public Topico obtenerTopicoPorId(Long id) {
        return topicoRepository.findById(id).orElse(null);
    }

    // Método para actualizar un tópico
    public Topico actualizarTopico(Long id, String titulo, String mensaje, String curso) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            topico.setTitulo(titulo);
            topico.setMensaje(mensaje);
            topico.setCurso(curso);

            return topicoRepository.save(topico); // Guardamos los cambios
        }

        return null; // Si no se encuentra el tópico, devuelve null
    }

    public boolean borrarTopico(Long topicoId, Long usuarioId) {
        // Buscar el tópico por ID
        Topico topico = topicoRepository.findById(topicoId).orElse(null);
        if (topico == null || !topico.getUsuarioId().equals(usuarioId)) {
            return false; // No se puede eliminar si el tópico no existe o no pertenece al usuario
        }

        // Eliminar el tópico
        topicoRepository.delete(topico);
        return true;
    }

}
