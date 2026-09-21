package br.uel.RegistroTreinos.Repository;

import br.uel.RegistroTreinos.Model.Treino;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {

    // Busca treinos cujo usuário tem o nome contendo o texto (ignora maiúsculas/minúsculas)
    List<Treino> findByUsuarioNomeContainingIgnoreCase(String nome, Sort sort);
}