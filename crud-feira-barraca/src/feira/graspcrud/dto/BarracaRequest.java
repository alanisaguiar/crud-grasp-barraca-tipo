package feira.graspcrud.dto;

public class BarracaRequest {

    private String nome;
    private String descricao;
    private boolean ativo;
    private Long tipoBarracaId;

    public BarracaRequest(String nome, String descricao, boolean ativo, Long tipoBarracaId) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
        this.tipoBarracaId = tipoBarracaId;
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

    public Long getTipoBarracaId() {
        return tipoBarracaId;
    }
}