package feira.graspcrud.barraca_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import feira.graspcrud.barraca_api.domain.Barraca;

public interface BarracaRepository extends JpaRepository<Barraca, Long> {

    Optional<Barraca> findByNome(String nome);

    boolean existsByTipoBarracaId(Long tipoBarracaId);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}