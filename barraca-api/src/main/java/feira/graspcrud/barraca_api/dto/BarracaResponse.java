package feira.graspcrud.barraca_api.dto;

import feira.graspcrud.barraca_api.domain.Barraca;

public record BarracaResponse(
        Long id,
        String nome,
        String descricao,
        boolean ativo,
        TipoBarracaResponse tipoBarraca
) {

    public static BarracaResponse from(Barraca barraca) {
        return new BarracaResponse(
                barraca.getId(),
                barraca.getNome(),
                barraca.getDescricao(),
                barraca.isAtivo(),
                TipoBarracaResponse.from(barraca.getTipoBarraca())
        );
    }
}