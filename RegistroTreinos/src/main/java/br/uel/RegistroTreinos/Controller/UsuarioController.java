package br.uel.RegistroTreinos.Controller;

import br.uel.RegistroTreinos.Model.Usuario;
import br.uel.RegistroTreinos.Service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // Exibe o formulário de cadastro (objeto vazio)
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    // Processa o cadastro (POST = criar)
    @PostMapping
    public String salvar(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.salvar(usuario);
            redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    // Exibe o formulário de edição, já preenchido
    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorID(id));
        return "usuarios/form";
    }

    // Processa a edição (POST = atualizar, sem PUT de verdade)
    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long id, @ModelAttribute Usuario usuario,
                            RedirectAttributes redirectAttributes) {
        try {
            usuarioService.atualizar(id, usuario);
            redirectAttributes.addFlashAttribute("sucesso", "Usuário atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    // Processa a exclusão (POST = excluir, sem DELETE de verdade)
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Usuário excluído com sucesso!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/usuarios";
    }
}