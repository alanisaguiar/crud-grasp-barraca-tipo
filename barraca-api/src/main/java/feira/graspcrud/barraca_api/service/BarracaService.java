package feira.graspcrud.barraca_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import feira.graspcrud.barraca_api.domain.Barraca;
import feira.graspcrud.barraca_api.domain.TipoBarraca;
import feira.graspcrud.barraca_api.dto.BarracaRequest;
import feira.graspcrud.barraca_api.dto.BarracaResponse;
import feira.graspcrud.barraca_api.exception.RegraNegocioException;
import feira.graspcrud.barraca_api.repository.BarracaRepository;
import feira.graspcrud.barraca_api.repository.TipoBarracaRepository;

@Service
public class BarracaService {

    private final BarracaRepository barracaRepository;
    private final TipoBarracaRepository tipoBarracaRepository;

    public BarracaService(BarracaRepository barracaRepository,
                          TipoBarracaRepository tipoBarracaRepository) {
        this.barracaRepository = barracaRepository;
        this.tipoBarracaRepository = tipoBarracaRepository;
    }

    @Transactional(readOnly = true)
    public List<BarracaResponse> listar() {
        return barracaRepository.findAll()
                .stream()
                .map(BarracaResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public BarracaResponse buscarPorId(Long id) {
        Barraca barraca = buscarEntidadePorId(id);
        return BarracaResponse.from(barraca);
    }

    @Transactional
    public BarracaResponse cadastrar(BarracaRequest request) {
        barracaRepository.findByNome(request.nome())
                .ifPresent(existente -> {
                    throw new RegraNegocioException("Já existe uma barraca com esse nome.");
                });

        TipoBarraca tipoBarraca = tipoBarracaRepository.findById(request.tipoBarracaId())
                .orElseThrow(() -> new RegraNegocioException("Tipo de barraca não encontrado."));

        Barraca barraca = new Barraca(
                request.nome(),
                request.descricao(),
                request.ativo(),
                tipoBarraca
        );

        Barraca salva = barracaRepository.save(barraca);

        return BarracaResponse.from(salva);
    }

    @Transactional
    public void remover(Long id) {
        Barraca barraca = buscarEntidadePorId(id);
        barracaRepository.delete(barraca);
    }

    // Método auxiliar interno
    private Barraca buscarEntidadePorId(Long id) {
        return barracaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Barraca não encontrada."));
    }
}