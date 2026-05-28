package feira.graspcrud.barraca_api.dto;

import feira.graspcrud.barraca_api.domain.TipoBarraca;

public record TipoBarracaResponse(
        Long id,
        String nome,
        String descricao
) {

    public static TipoBarracaResponse from(TipoBarraca tipoBarraca) {
        return new TipoBarracaResponse(
                tipoBarraca.getId(),
                tipoBarraca.getNome(),
                tipoBarraca.getDescricao()
        );
    }
}