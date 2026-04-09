package feira.graspcrud.domain;

import feira.graspcrud.exception.RegraNegocioException;

public class TipoBarraca {

    private Long id;
    private String nome;
    private String descricao;

    public TipoBarraca(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        validar();
    }

    public void validar() {
        if (nome == null || nome.isBlank()) {
            throw new RegraNegocioException("O nome do tipo de barraca é obrigatório.");
        }
    }

    public void atualizar(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        validar();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}