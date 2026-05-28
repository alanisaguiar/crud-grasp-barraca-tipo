package feira.graspcrud.barraca_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import feira.graspcrud.barraca_api.domain.TipoBarraca;
import feira.graspcrud.barraca_api.dto.TipoBarracaRequest;
import feira.graspcrud.barraca_api.dto.TipoBarracaResponse;
import feira.graspcrud.barraca_api.exception.RegraNegocioException;
import feira.graspcrud.barraca_api.repository.BarracaRepository;
import feira.graspcrud.barraca_api.repository.TipoBarracaRepository;

@Service
public class TipoBarracaService {

    private final TipoBarracaRepository tipoBarracaRepository;
    private final BarracaRepository barracaRepository;

    public TipoBarracaService(TipoBarracaRepository tipoBarracaRepository,
                              BarracaRepository barracaRepository) {
        this.tipoBarracaRepository = tipoBarracaRepository;
        this.barracaRepository = barracaRepository;
    }

    @Transactional(readOnly = true)
    public List<TipoBarracaResponse> listar() {
        return tipoBarracaRepository.findAll()
                .stream()
                .map(TipoBarracaResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TipoBarracaResponse buscarPorId(Long id) {
        TipoBarraca tipoBarraca = buscarEntidadePorId(id);
        return TipoBarracaResponse.from(tipoBarraca);
    }

    @Transactional
    public TipoBarracaResponse cadastrar(TipoBarracaRequest request) {
        tipoBarracaRepository.findByNome(request.nome())
                .ifPresent(existente -> {
                    throw new RegraNegocioException("Já existe um tipo de barraca com esse nome.");
                });

        TipoBarraca tipoBarraca = new TipoBarraca(request.nome(), request.descricao());
        TipoBarraca salvo = tipoBarracaRepository.save(tipoBarraca);

        return TipoBarracaResponse.from(salvo);
    }

    @Transactional
    public void remover(Long id) {
        TipoBarraca tipoBarraca = buscarEntidadePorId(id);

        if (barracaRepository.existsByTipoBarracaId(tipoBarraca.getId())) {
            throw new RegraNegocioException(
                    "Não é permitido remover TipoBarraca em uso por uma Barraca.");
        }

        tipoBarracaRepository.delete(tipoBarraca);
    }

    // Método auxiliar interno
    private TipoBarraca buscarEntidadePorId(Long id) {
        return tipoBarracaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Tipo de barraca não encontrado."));
    }
}