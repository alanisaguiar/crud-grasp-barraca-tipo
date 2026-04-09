package feira.graspcrud.repository;

import java.util.List;
import feira.graspcrud.domain.Barraca;

public interface BarracaRepository {

    Barraca salvar(Barraca barraca);

    List<Barraca> listar();

    Barraca buscarPorId(Long id);

    Barraca buscarPorNome(String nome);

    void remover(Long id);

    Long gerarProximoId();
}