package br.uel.RegistroTreinos.Controller;

import br.uel.RegistroTreinos.Model.Treino;
import br.uel.RegistroTreinos.Model.Usuario;
import br.uel.RegistroTreinos.Service.TreinoService;
import br.uel.RegistroTreinos.Service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios/{usuarioId}/treinos")
public class TreinoController {

    private final TreinoService treinoService;
    private final UsuarioService usuarioService;

    public TreinoController(TreinoService treinoService, UsuarioService usuarioService) {
        this.treinoService = treinoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarPorUsuario(@PathVariable Long usuarioId,
                                   @RequestParam(defaultValue = "asc") String direcao,
                                   Model model) {
        Usuario usuario = usuarioService.buscarPorID(usuarioId);
        List<Treino> treinos = treinoService.listarPorUsuario(usuarioId, direcao);

        model.addAttribute("usuario", usuario);
        model.addAttribute("treinos", treinos);
        model.addAttribute("direcao", direcao);
        return "treinos/lista";
    }

    @GetMapping("/novo")
    public String novoForm(@PathVariable Long usuarioId, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorID(usuarioId));
        model.addAttribute("treino", new Treino());
        return "treinos/form";
    }

    @PostMapping
    public String salvar(@PathVariable Long usuarioId, @ModelAttribute Treino treino,
                         RedirectAttributes redirectAttributes) {
        try {
            treino.setUsuario(usuarioService.buscarPorID(usuarioId));
            treinoService.salvar(treino);
            redirectAttributes.addFlashAttribute("sucesso", "Treino cadastrado com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/usuarios/" + usuarioId + "/treinos";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long usuarioId, @PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorID(usuarioId));
        model.addAttribute("treino", treinoService.buscarPorId(id));
        return "treinos/form";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long usuarioId, @PathVariable Long id,
                            @ModelAttribute Treino treino, RedirectAttributes redirectAttributes) {
        try {
            treino.setUsuario(usuarioService.buscarPorID(usuarioId));
            treinoService.atualizar(id, treino);
            redirectAttributes.addFlashAttribute("sucesso", "Treino atualizado com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/usuarios/" + usuarioId + "/treinos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long usuarioId, @PathVariable Long id,
                          RedirectAttributes redirectAttributes) {
        treinoService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Treino excluído com sucesso!");
        return "redirect:/usuarios/" + usuarioId + "/treinos";
    }
}