package com.alura.foro.ApiForo.repository;



import com.alura.foro.ApiForo.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
}
