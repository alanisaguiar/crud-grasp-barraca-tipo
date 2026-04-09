package feira.graspcrud.service;

import java.util.List;
import feira.graspcrud.domain.Barraca;
import feira.graspcrud.domain.TipoBarraca;
import feira.graspcrud.dto.BarracaRequest;
import feira.graspcrud.exception.RegraNegocioException;
import feira.graspcrud.repository.BarracaRepository;
import feira.graspcrud.repository.TipoBarracaRepository;

public class BarracaService {

    private final BarracaRepository barracaRepository;
    private final TipoBarracaRepository tipoBarracaRepository;

    public BarracaService(BarracaRepository barracaRepository, TipoBarracaRepository tipoBarracaRepository) {
        this.barracaRepository = barracaRepository;
        this.tipoBarracaRepository = tipoBarracaRepository;
    }

    public Barraca cadastrar(BarracaRequest request) {
        Barraca existente = barracaRepository.buscarPorNome(request.getNome());
        if (existente != null) {
            throw new RegraNegocioException("Já existe uma barraca com esse nome.");
        }

        TipoBarraca tipoBarraca = tipoBarracaRepository.buscarPorId(request.getTipoBarracaId());
        if (tipoBarraca == null) {
            throw new RegraNegocioException("Tipo de barraca não encontrado.");
        }

        Barraca barraca = new Barraca(
                barracaRepository.gerarProximoId(),
                request.getNome(),
                request.getDescricao(),
                request.isAtivo(),
                tipoBarraca
        );

        return barracaRepository.salvar(barraca);
    }

    public List<Barraca> listar() {
        return barracaRepository.listar();
    }

    public Barraca buscarPorId(Long id) {
        Barraca barraca = barracaRepository.buscarPorId(id);
        if (barraca == null) {
            throw new RegraNegocioException("Barraca não encontrada.");
        }
        return barraca;
    }

    public Barraca atualizar(Long id, BarracaRequest request) {
        Barraca barraca = barracaRepository.buscarPorId(id);
        if (barraca == null) {
            throw new RegraNegocioException("Barraca não encontrada.");
        }

        Barraca barracaComMesmoNome = barracaRepository.buscarPorNome(request.getNome());
        if (barracaComMesmoNome != null && !barracaComMesmoNome.getId().equals(id)) {
            throw new RegraNegocioException("Já existe outra barraca com esse nome.");
        }

        TipoBarraca tipoBarraca = tipoBarracaRepository.buscarPorId(request.getTipoBarracaId());
        if (tipoBarraca == null) {
            throw new RegraNegocioException("Tipo de barraca não encontrado.");
        }

        barraca.atualizar(
                request.getNome(),
                request.getDescricao(),
                request.isAtivo(),
                tipoBarraca
        );

        return barracaRepository.salvar(barraca);
    }

    public void remover(Long id) {
        Barraca barraca = barracaRepository.buscarPorId(id);
        if (barraca == null) {
            throw new RegraNegocioException("Barraca não encontrada.");
        }

        barracaRepository.remover(id);
    }
}