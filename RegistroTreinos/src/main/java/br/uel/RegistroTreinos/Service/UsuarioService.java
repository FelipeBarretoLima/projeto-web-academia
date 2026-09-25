package br.uel.RegistroTreinos.Service;

import br.uel.RegistroTreinos.Model.Usuario;
import br.uel.RegistroTreinos.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public  UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorID(Long id){
        return usuarioRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Usuário Não encontradoo com id: " + id));
    }

    public Usuario salvar(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw  new IllegalArgumentException("O nome do usuário é obrigatório");
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> pesquisarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }
}
