package feira.graspcrud.repository.json;

import java.util.ArrayList;
import java.util.List;
import feira.graspcrud.domain.Barraca;
import feira.graspcrud.domain.TipoBarraca;
import feira.graspcrud.repository.BarracaRepository;
import feira.graspcrud.util.JsonMini;

public class BarracaRepositoryJson implements BarracaRepository {

    private static final String ARQUIVO = "data/barracas.json";

    private final List<Barraca> barracas;
    private final JsonMini jsonMini;

    public BarracaRepositoryJson() {
        this.barracas = new ArrayList<>();
        this.jsonMini = new JsonMini();
        carregar();
    }

    @Override
    public Barraca salvar(Barraca barraca) {
        Barraca existente = buscarPorId(barraca.getId());

        if (existente == null) {
            barracas.add(barraca);
        } else {
            barracas.remove(existente);
            barracas.add(barraca);
        }

        persistir();
        return barraca;
    }

    @Override
    public List<Barraca> listar() {
        return new ArrayList<>(barracas);
    }

    @Override
    public Barraca buscarPorId(Long id) {
        for (Barraca barraca : barracas) {
            if (barraca.getId().equals(id)) {
                return barraca;
            }
        }
        return null;
    }

    @Override
    public Barraca buscarPorNome(String nome) {
        if (nome == null) {
            return null;
        }

        for (Barraca barraca : barracas) {
            if (barraca.getNome().equalsIgnoreCase(nome.trim())) {
                return barraca;
            }
        }
        return null;
    }

    @Override
    public void remover(Long id) {
        Barraca barraca = buscarPorId(id);
        if (barraca != null) {
            barracas.remove(barraca);
            persistir();
        }
    }

    @Override
    public Long gerarProximoId() {
        long maior = 0L;
        for (Barraca barraca : barracas) {
            if (barraca.getId() > maior) {
                maior = barraca.getId();
            }
        }
        return maior + 1;
    }

    private void persistir() {
        StringBuilder sb = new StringBuilder();
        for (Barraca barraca : barracas) {
            sb.append(barraca.getId()).append(";")
              .append(valorSeguro(barraca.getNome())).append(";")
              .append(valorSeguro(barraca.getDescricao())).append(";")
              .append(barraca.isAtivo()).append(";")
              .append(barraca.getTipoBarraca().getId()).append(";")
              .append(valorSeguro(barraca.getTipoBarraca().getNome())).append(";")
              .append(valorSeguro(barraca.getTipoBarraca().getDescricao()))
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

            Long barracaId = Long.parseLong(partes[0]);
            String nome = partes[1];
            String descricao = partes[2];
            boolean ativo = Boolean.parseBoolean(partes[3]);

            Long tipoId = Long.parseLong(partes[4]);
            String tipoNome = partes[5];
            String tipoDescricao = partes.length > 6 ? partes[6] : "";

            TipoBarraca tipoBarraca = new TipoBarraca(tipoId, tipoNome, tipoDescricao);
            Barraca barraca = new Barraca(barracaId, nome, descricao, ativo, tipoBarraca);
            barracas.add(barraca);
        }
    }

    private String valorSeguro(String valor) {
        return valor == null ? "" : valor.replace(";", ",");
    }
}