package ps2.titular_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TitularDao {

    @Autowired
    private TitularRepo repo;

    public Iterable<Titular> listarTodos() {
        return repo.findAll();
    }

    public Titular buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Titular salvar(Titular titular) {
        return repo.save(titular);
    }

    public Titular alterar(Long id, String nome, String cpf) {
        Titular t = buscarPorId(id);
        if (t != null) {
            t.setNome(nome);
            t.setCpf(cpf);
            return repo.save(t);
        }
        return null;
    }

    public boolean apagar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
