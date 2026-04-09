package feira.graspcrud.repository;

import java.util.List;
import feira.graspcrud.domain.TipoBarraca;

public interface TipoBarracaRepository {

    TipoBarraca salvar(TipoBarraca tipoBarraca);

    List<TipoBarraca> listar();

    TipoBarraca buscarPorId(Long id);

    TipoBarraca buscarPorNome(String nome);

    void remover(Long id);

    Long gerarProximoId();
}