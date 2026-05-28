package feira.graspcrud.barraca_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BarracaRequest(

        @NotBlank(message = "O nome da barraca é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String nome,

        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
        String descricao,

        boolean ativo,

        @NotNull(message = "O tipo da barraca é obrigatório.")
        Long tipoBarracaId
) {
}