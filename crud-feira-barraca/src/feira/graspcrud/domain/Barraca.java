package feira.graspcrud.domain;

import feira.graspcrud.exception.RegraNegocioException;

public class Barraca {

    private Long id;
    private String nome;
    private String descricao;
    private boolean ativo;
    private TipoBarraca tipoBarraca;

    public Barraca(Long id, String nome, String descricao, boolean ativo, TipoBarraca tipoBarraca) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
        this.tipoBarraca = tipoBarraca;
        validar();
    }

    public void validar() {
        if (nome == null || nome.isBlank() || nome.trim().length() < 3) {
            throw new RegraNegocioException("O nome da barraca é obrigatório e deve ter ao menos 3 caracteres.");
        }

        if (tipoBarraca == null) {
            throw new RegraNegocioException("O tipo da barraca é obrigatório.");
        }
    }

    public void atualizar(String nome, String descricao, boolean ativo, TipoBarraca tipoBarraca) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
        this.tipoBarraca = tipoBarraca;
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

    public boolean isAtivo() {
        return ativo;
    }

    public TipoBarraca getTipoBarraca() {
        return tipoBarraca;
    }
}