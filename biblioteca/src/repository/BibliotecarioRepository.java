package repository;

import model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliotecarioRepository  extends JpaRepository<Bibliotecario, Long> {
    boolean existsByMatricula(String matricula);
}
