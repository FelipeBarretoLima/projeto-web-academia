package br.uel.RegistroTreinos.Controller;

import br.uel.RegistroTreinos.Model.Usuario;
import br.uel.RegistroTreinos.Service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String nome, Model model) {
        List<Usuario> usuarios = (nome == null || nome.isBlank())
                ? usuarioService.listarTodos()
                : usuarioService.pesquisarPorNome(nome);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("nome", nome);
        return "usuarios/lista";
    }
}