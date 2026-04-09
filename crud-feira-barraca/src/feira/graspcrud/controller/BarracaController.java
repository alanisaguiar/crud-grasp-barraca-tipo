package feira.graspcrud.controller;

import java.util.List;
import java.util.Scanner;
import feira.graspcrud.domain.Barraca;
import feira.graspcrud.domain.TipoBarraca;
import feira.graspcrud.dto.BarracaRequest;
import feira.graspcrud.dto.TipoBarracaRequest;
import feira.graspcrud.exception.RegraNegocioException;
import feira.graspcrud.service.BarracaService;
import feira.graspcrud.service.TipoBarracaService;

public class BarracaController {

    private final Scanner scanner;
    private final BarracaService barracaService;
    private final TipoBarracaService tipoBarracaService;

    public BarracaController(Scanner scanner, BarracaService barracaService, TipoBarracaService tipoBarracaService) {
        this.scanner = scanner;
        this.barracaService = barracaService;
        this.tipoBarracaService = tipoBarracaService;
    }

    public void iniciarMenu() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1:
                        cadastrarTipoBarraca();
                        break;
                    case 2:
                        listarTiposBarraca();
                        break;
                    case 3:
                        cadastrarBarraca();
                        break;
                    case 4:
                        listarBarracas();
                        break;
                    case 5:
                        buscarBarracaPorId();
                        break;
                    case 6:
                        atualizarBarraca();
                        break;
                    case 7:
                        removerBarraca();
                        break;
                    case 8:
                        removerTipoBarraca();
                        break;
                    case 9:
                        System.out.println("Encerrando o sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (RegraNegocioException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }

            System.out.println();
        } while (opcao != 9);
    }

    private void exibirMenu() {
        System.out.println("===== SISTEMA FEIRA LIVRE =====");
        System.out.println("1. Cadastrar TipoBarraca");
        System.out.println("2. Listar TipoBarraca");
        System.out.println("3. Cadastrar Barraca");
        System.out.println("4. Listar Barraca");
        System.out.println("5. Buscar Barraca por id");
        System.out.println("6. Atualizar Barraca");
        System.out.println("7. Excluir Barraca");
        System.out.println("8. Excluir TipoBarraca");
        System.out.println("9. Sair");
    }

    private void cadastrarTipoBarraca() {
        System.out.println("=== Cadastro de TipoBarraca ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        TipoBarracaRequest request = new TipoBarracaRequest(nome, descricao);
        TipoBarraca tipoBarraca = tipoBarracaService.cadastrar(request);

        System.out.println("TipoBarraca cadastrado com sucesso. ID: " + tipoBarraca.getId());
    }

    private void listarTiposBarraca() {
        System.out.println("=== Lista de TipoBarraca ===");
        List<TipoBarraca> tipos = tipoBarracaService.listar();

        if (tipos.isEmpty()) {
            System.out.println("Nenhum tipo de barraca cadastrado.");
            return;
        }

        for (TipoBarraca tipo : tipos) {
            System.out.println("ID: " + tipo.getId()
                    + " | Nome: " + tipo.getNome()
                    + " | Descrição: " + tipo.getDescricao());
        }
    }

    private void cadastrarBarraca() {
        System.out.println("=== Cadastro de Barraca ===");
        listarTiposBarraca();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        boolean ativo = lerBoolean("Ativa? (true/false): ");
        Long tipoId = lerLong("ID do TipoBarraca: ");

        BarracaRequest request = new BarracaRequest(nome, descricao, ativo, tipoId);
        Barraca barraca = barracaService.cadastrar(request);

        System.out.println("Barraca cadastrada com sucesso. ID: " + barraca.getId());
    }

    private void listarBarracas() {
        System.out.println("=== Lista de Barracas ===");
        List<Barraca> barracas = barracaService.listar();

        if (barracas.isEmpty()) {
            System.out.println("Nenhuma barraca cadastrada.");
            return;
        }

        for (Barraca barraca : barracas) {
            System.out.println("ID: " + barraca.getId()
                    + " | Nome: " + barraca.getNome()
                    + " | Descrição: " + barraca.getDescricao()
                    + " | Ativa: " + barraca.isAtivo()
                    + " | Tipo: " + barraca.getTipoBarraca().getNome());
        }
    }

    private void buscarBarracaPorId() {
        System.out.println("=== Buscar Barraca por ID ===");
        Long id = lerLong("Informe o ID: ");

        Barraca barraca = barracaService.buscarPorId(id);

        System.out.println("ID: " + barraca.getId());
        System.out.println("Nome: " + barraca.getNome());
        System.out.println("Descrição: " + barraca.getDescricao());
        System.out.println("Ativa: " + barraca.isAtivo());
        System.out.println("Tipo: " + barraca.getTipoBarraca().getNome());
    }

    private void atualizarBarraca() {
        System.out.println("=== Atualizar Barraca ===");
        Long id = lerLong("ID da Barraca: ");

        listarTiposBarraca();

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Nova descrição: ");
        String descricao = scanner.nextLine();
        boolean ativo = lerBoolean("Ativa? (true/false): ");
        Long tipoId = lerLong("Novo ID do TipoBarraca: ");

        BarracaRequest request = new BarracaRequest(nome, descricao, ativo, tipoId);
        Barraca barraca = barracaService.atualizar(id, request);

        System.out.println("Barraca atualizada com sucesso. Nome atual: " + barraca.getNome());
    }

    private void removerBarraca() {
        System.out.println("=== Remover Barraca ===");
        Long id = lerLong("ID da Barraca: ");

        barracaService.remover(id);
        System.out.println("Barraca removida com sucesso.");
    }

    private void removerTipoBarraca() {
        System.out.println("=== Remover TipoBarraca ===");
        Long id = lerLong("ID do TipoBarraca: ");

        tipoBarracaService.remover(id);
        System.out.println("TipoBarraca removido com sucesso.");
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        int valor = Integer.parseInt(scanner.nextLine());
        return valor;
    }

    private Long lerLong(String mensagem) {
        System.out.print(mensagem);
        Long valor = Long.parseLong(scanner.nextLine());
        return valor;
    }

    private boolean lerBoolean(String mensagem) {
        System.out.print(mensagem);
        return Boolean.parseBoolean(scanner.nextLine());
    }
}