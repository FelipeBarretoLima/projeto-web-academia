package br.uel.RegistroTreinos.Controller;

import br.uel.RegistroTreinos.Model.Treino;
import br.uel.RegistroTreinos.Model.Usuario;
import br.uel.RegistroTreinos.Service.TreinoService;
import br.uel.RegistroTreinos.Service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}