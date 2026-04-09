package feira.graspcrud.dto;

public class TipoBarracaRequest {

    private String nome;
    private String descricao;

    public TipoBarracaRequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}