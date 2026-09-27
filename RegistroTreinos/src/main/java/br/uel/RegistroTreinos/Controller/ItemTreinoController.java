package br.uel.RegistroTreinos.Controller;

import br.uel.RegistroTreinos.Model.ItemTreino;
import br.uel.RegistroTreinos.Model.Treino;
import br.uel.RegistroTreinos.Service.ItemTreinoService;
import br.uel.RegistroTreinos.Service.TreinoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/novo")
    public String novoForm(@PathVariable Long treinoId, Model model) {
        model.addAttribute("treino", treinoService.buscarPorId(treinoId));
        model.addAttribute("item", new ItemTreino());
        return "itens/form";
    }

    @PostMapping
    public String salvar(@PathVariable Long treinoId, @ModelAttribute ItemTreino item,
                         RedirectAttributes redirectAttributes) {
        try {
            item.setTreino(treinoService.buscarPorId(treinoId));
            itemTreinoService.salvar(item);
            redirectAttributes.addFlashAttribute("sucesso", "Exercício cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/treinos/" + treinoId + "/itens";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long treinoId, @PathVariable Long id, Model model) {
        model.addAttribute("treino", treinoService.buscarPorId(treinoId));
        model.addAttribute("item", itemTreinoService.buscarPorId(id));
        return "itens/form";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long treinoId, @PathVariable Long id,
                            @ModelAttribute ItemTreino item, RedirectAttributes redirectAttributes) {
        try {
            itemTreinoService.atualizar(id, item);
            redirectAttributes.addFlashAttribute("sucesso", "Exercício atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/treinos/" + treinoId + "/itens";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long treinoId, @PathVariable Long id,
                          RedirectAttributes redirectAttributes) {
        itemTreinoService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Exercício excluído com sucesso!");
        return "redirect:/treinos/" + treinoId + "/itens";
    }
}