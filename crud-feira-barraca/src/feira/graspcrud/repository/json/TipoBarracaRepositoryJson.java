package feira.graspcrud.repository.json;

import java.util.ArrayList;
import java.util.List;
import feira.graspcrud.domain.TipoBarraca;
import feira.graspcrud.repository.TipoBarracaRepository;
import feira.graspcrud.util.JsonMini;

public class TipoBarracaRepositoryJson implements TipoBarracaRepository {

    private static final String ARQUIVO = "data/tipos-barraca.json";

    private final List<TipoBarraca> tiposBarraca;
    private final JsonMini jsonMini;

    public TipoBarracaRepositoryJson() {
        this.tiposBarraca = new ArrayList<>();
        this.jsonMini = new JsonMini();
        carregar();
    }

    @Override
    public TipoBarraca salvar(TipoBarraca tipoBarraca) {
        TipoBarraca existente = buscarPorId(tipoBarraca.getId());

        if (existente == null) {
            tiposBarraca.add(tipoBarraca);
        } else {
            tiposBarraca.remove(existente);
            tiposBarraca.add(tipoBarraca);
        }

        persistir();
        return tipoBarraca;
    }

    @Override
    public List<TipoBarraca> listar() {
        return new ArrayList<>(tiposBarraca);
    }

    @Override
    public TipoBarraca buscarPorId(Long id) {
        for (TipoBarraca tipo : tiposBarraca) {
            if (tipo.getId().equals(id)) {
                return tipo;
            }
        }
        return null;
    }

    @Override
    public TipoBarraca buscarPorNome(String nome) {
        if (nome == null) {
            return null;
        }

        for (TipoBarraca tipo : tiposBarraca) {
            if (tipo.getNome().equalsIgnoreCase(nome.trim())) {
                return tipo;
            }
        }
        return null;
    }

    @Override
    public void remover(Long id) {
        TipoBarraca tipo = buscarPorId(id);
        if (tipo != null) {
            tiposBarraca.remove(tipo);
            persistir();
        }
    }

    @Override
    public Long gerarProximoId() {
        long maior = 0L;
        for (TipoBarraca tipo : tiposBarraca) {
            if (tipo.getId() > maior) {
                maior = tipo.getId();
            }
        }
        return maior + 1;
    }

    private void persistir() {
        StringBuilder sb = new StringBuilder();
        for (TipoBarraca tipo : tiposBarraca) {
            sb.append(tipo.getId()).append(";")
              .append(valorSeguro(tipo.getNome())).append(";")
              .append(valorSeguro(tipo.getDescricao()))
              .append("\n");
        }
        jsonMini.escrever(ARQUIVO, sb.toString());
    }

    private void carregar() {
        String conteudo = jsonMini.ler(ARQUIVO);
        if (conteudo == null || conteudo.isBlank()) {
            return;
        }

        String[] linhas = conteudo.split("\n");
        for (String linha : linhas) {
            if (linha.isBlank()) {
                continue;
            }

            String[] partes = linha.split(";", -1);
            Long id = Long.parseLong(partes[0]);
            String nome = partes[1];
            String descricao = partes.length > 2 ? partes[2] : "";

            tiposBarraca.add(new TipoBarraca(id, nome, descricao));
        }
    }

    private String valorSeguro(String valor) {
        return valor == null ? "" : valor.replace(";", ",");
    }
}