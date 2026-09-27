package br.uel.RegistroTreinos.Service;

import br.uel.RegistroTreinos.Model.Usuario;
import br.uel.RegistroTreinos.Repository.TreinoRepository;
import br.uel.RegistroTreinos.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TreinoRepository treinoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, TreinoRepository treinoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.treinoRepository = treinoRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> pesquisarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Usuario buscarPorID(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));
    }

    public Usuario salvar(Usuario usuario) {
        validarNome(usuario);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dadosAtualizados) {
        Usuario usuario = buscarPorID(id);
        validarNome(dadosAtualizados);
        usuario.setNome(dadosAtualizados.getNome());
        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        Usuario usuario = buscarPorID(id);

        if (treinoRepository.existsByUsuarioId(id)) {
            throw new IllegalStateException(
                    "Não é possível excluir um usuário que possui treinos cadastrados.");
        }

        usuarioRepository.delete(usuario);
    }

    private void validarNome(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório");
        }
    }
}