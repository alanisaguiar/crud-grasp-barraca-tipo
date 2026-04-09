package feira.graspcrud.service;

import java.util.List;
import feira.graspcrud.domain.Barraca;
import feira.graspcrud.domain.TipoBarraca;
import feira.graspcrud.dto.TipoBarracaRequest;
import feira.graspcrud.exception.RegraNegocioException;
import feira.graspcrud.repository.BarracaRepository;
import feira.graspcrud.repository.TipoBarracaRepository;

public class TipoBarracaService {

    private final TipoBarracaRepository tipoBarracaRepository;
    private final BarracaRepository barracaRepository;

    public TipoBarracaService(TipoBarracaRepository tipoBarracaRepository, BarracaRepository barracaRepository) {
        this.tipoBarracaRepository = tipoBarracaRepository;
        this.barracaRepository = barracaRepository;
    }

    public TipoBarraca cadastrar(TipoBarracaRequest request) {
        TipoBarraca existente = tipoBarracaRepository.buscarPorNome(request.getNome());
        if (existente != null) {
            throw new RegraNegocioException("Já existe um tipo de barraca com esse nome.");
        }

        TipoBarraca tipoBarraca = new TipoBarraca(
                tipoBarracaRepository.gerarProximoId(),
                request.getNome(),
                request.getDescricao()
        );

        return tipoBarracaRepository.salvar(tipoBarraca);
    }

    public List<TipoBarraca> listar() {
        return tipoBarracaRepository.listar();
    }

 
    public TipoBarraca buscarPorId(Long id) {
        TipoBarraca tipoBarraca = tipoBarracaRepository.buscarPorId(id);
        if (tipoBarraca == null) {
            throw new RegraNegocioException("Tipo de barraca não encontrado.");
        }
        return tipoBarraca;
    }

  
    public void remover(Long id) {
        TipoBarraca tipoBarraca = tipoBarracaRepository.buscarPorId(id);
        if (tipoBarraca == null) {
            throw new RegraNegocioException("Tipo de barraca não encontrado.");
        }

        List<Barraca> barracas = barracaRepository.listar();
        for (Barraca barraca : barracas) {
            if (barraca.getTipoBarraca() != null && barraca.getTipoBarraca().getId().equals(id)) {
                throw new RegraNegocioException("Não é permitido remover TipoBarraca em uso por uma Barraca.");
            }
        }

        tipoBarracaRepository.remover(id);
    }
}