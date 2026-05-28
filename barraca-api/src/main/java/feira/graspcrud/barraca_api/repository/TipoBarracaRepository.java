package feira.graspcrud.barraca_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import feira.graspcrud.barraca_api.domain.TipoBarraca;

public interface TipoBarracaRepository extends JpaRepository<TipoBarraca, Long> {

    Optional<TipoBarraca> findByNome(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}