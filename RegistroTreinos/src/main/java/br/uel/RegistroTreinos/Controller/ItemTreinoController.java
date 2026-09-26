package br.uel.RegistroTreinos.Controller;

import br.uel.RegistroTreinos.Model.ItemTreino;
import br.uel.RegistroTreinos.Model.Treino;
import br.uel.RegistroTreinos.Service.ItemTreinoService;
import br.uel.RegistroTreinos.Service.TreinoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/treinos/{treinoId}/itens")
public class ItemTreinoController {

    private final ItemTreinoService itemTreinoService;
    private final TreinoService treinoService;

    public ItemTreinoController(ItemTreinoService itemTreinoService, TreinoService treinoService) {
        this.itemTreinoService = itemTreinoService;
        this.treinoService = treinoService;
    }

    @GetMapping
    public String listarPorTreino(@PathVariable Long treinoId, Model model) {
        Treino treino = treinoService.buscarPorId(treinoId);
        List<ItemTreino> itens = itemTreinoService.listarPorTreino(treinoId);

        model.addAttribute("treino", treino);
        model.addAttribute("itens", itens);
        return "itens/lista";
    }
}