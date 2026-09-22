package br.uel.RegistroTreinos.Repository;

import br.uel.RegistroTreinos.Model.ItemTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemTreinoRepository extends JpaRepository<ItemTreino, Long> {

    // Busca todos os exercícios de um treino
    List<ItemTreino> findByTreinoId(Long treinoId);

    // Remove todos os exercícios de um treino
    void deleteByTreinoId(Long treinoId);
}