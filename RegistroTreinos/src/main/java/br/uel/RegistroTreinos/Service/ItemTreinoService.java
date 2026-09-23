package br.uel.RegistroTreinos.Service;

import br.uel.RegistroTreinos.Model.ItemTreino;
import br.uel.RegistroTreinos.Model.Treino;
import br.uel.RegistroTreinos.Repository.ItemTreinoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemTreinoService {

    private final ItemTreinoRepository itemTreinoRepository;
    private final TreinoService treinoService;

    public ItemTreinoService(ItemTreinoRepository itemTreinoRepository,
                             TreinoService treinoService) {
        this.itemTreinoRepository = itemTreinoRepository;
        this.treinoService = treinoService;
    }

    public List<ItemTreino> listarPorTreino(Long treinoId) {
        return itemTreinoRepository.findByTreinoId(treinoId);
    }

    public ItemTreino buscarPorId(Long id) {
        return itemTreinoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Exercício não encontrado com id: " + id));
    }

    public ItemTreino salvar(ItemTreino item) {
        validarDadosBasicos(item);

        Treino treino = treinoService.buscarPorId(item.getTreino().getId());
        item.setTreino(treino);

        return itemTreinoRepository.save(item);
    }

    public ItemTreino atualizar(Long id, ItemTreino dadosAtualizados) {
        ItemTreino item = buscarPorId(id);
        validarDadosBasicos(dadosAtualizados);

        item.setNomeExercicio(dadosAtualizados.getNomeExercicio());
        item.setCarga(dadosAtualizados.getCarga());
        item.setRepeticoes(dadosAtualizados.getRepeticoes());
        item.setTotalSeries(dadosAtualizados.getTotalSeries());

        return itemTreinoRepository.save(item);
    }

    public void excluir(Long id) {
        buscarPorId(id); // garante que existe antes de tentar excluir
        itemTreinoRepository.deleteById(id);
    }

    private void validarDadosBasicos(ItemTreino item) {
        if (item.getTreino() == null || item.getTreino().getId() == null) {
            throw new IllegalArgumentException("O treino do exercício é obrigatório.");
        }
        if (item.getNomeExercicio() == null || item.getNomeExercicio().isBlank()) {
            throw new IllegalArgumentException("O nome do exercício é obrigatório.");
        }
        if (item.getCarga() == null || item.getCarga().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A carga deve ser maior que zero.");
        }
        if (item.getRepeticoes() == null || item.getRepeticoes() <= 0) {
            throw new IllegalArgumentException("O número de repetições deve ser maior que zero.");
        }
        if (item.getTotalSeries() == null || item.getTotalSeries() <= 0) {
            throw new IllegalArgumentException("O número de séries deve ser maior que zero.");
        }
    }
}