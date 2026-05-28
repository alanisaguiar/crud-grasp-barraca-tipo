package feira.graspcrud.barraca_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "barraca")
public class Barraca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private boolean ativo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_barraca_id", nullable = false)
    private TipoBarraca tipoBarraca;

    // Construtor vazio exigido pelo JPA
    protected Barraca() {
    }

    public Barraca(String nome, String descricao, boolean ativo, TipoBarraca tipoBarraca) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
        this.tipoBarraca = tipoBarraca;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public TipoBarraca getTipoBarraca() {
        return tipoBarraca;
    }

    public void setTipoBarraca(TipoBarraca tipoBarraca) {
        this.tipoBarraca = tipoBarraca;
    }
}