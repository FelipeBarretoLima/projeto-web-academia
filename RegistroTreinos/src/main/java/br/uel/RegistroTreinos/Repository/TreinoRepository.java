package br.uel.RegistroTreinos.Repository;

import br.uel.RegistroTreinos.Model.Treino;
import br.uel.RegistroTreinos.Model.Usuario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {

    // Busca treinos cujo usuário tem o nome contendo o texto (ignora maiúsculas/minúsculas)
    List<Treino> findByUsuarioNomeContainingIgnoreCase(String nome, Sort sort);

    // Verifica se já existe um treino com esse usuário, data e nome de treino
    boolean existsByUsuarioAndDateAndNomeTreino(Usuario usuario, LocalDate date, String nomeTreino);

    // Mesma verificação, mas ignorando um id específico (usado na edição, pra não comparar o registro com ele mesmo)
    boolean existsByUsuarioAndDateAndNomeTreinoAndIdNot(Usuario usuario, LocalDate date, String nomeTreino, Long id);
}