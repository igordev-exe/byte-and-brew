package br.edu.cafeteria.app;
/*a*/
import br.edu.cafeteria.excecao.*;
import br.edu.cafeteria.modelo.*;
import br.edu.cafeteria.servico.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GerenciadorCafeteria repo = new GerenciadorCafeteria();
    private static final Atendente atendenteLogado = new Atendente("MAT-1001", "Gordon Freeman");

    public static void main(String[] args) {
        carregarDadosIniciais();

        int opcao;
        do {
            limparTela();
            System.out.println("===== SISTEMA BYTE & BREW =====");
            System.out.println("Operador de Caixa: " + atendenteLogado.getNome());
            System.out.println("-------------------------------");
            System.out.println("[0] Teste de Mesa");
            System.out.println("[1] Modo Cliente (Caixa/Venda)");
            System.out.println("[2] Modo Funcionário (Gestão)");
            System.out.println("[3] Sair do Sistema");
            System.out.print("Escolha uma opção: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 0: executarTesteDeMesa(); break;
                case 1: fluxoCliente(); break;
                case 2: fluxoFuncionario(); break;
                case 3: System.out.println("Encerrando a operação do caixa. Até logo!"); break;
                default:
                    System.out.println("Opção inválida! Pressione ENTER para continuar.");
                    scanner.nextLine();
            }
        } while (opcao != 3);

        scanner.close();
    }

    private static void fluxoCliente() {
        limparTela();
        System.out.println("--- ABERTURA DE PEDIDO ---");
        System.out.print("Informe o CPF do cliente (ou ENTER para Cliente Casual): ");
        String cpf = scanner.nextLine().trim();

        Cliente cliente = null;
        if (!cpf.isEmpty()) {
            cliente = repo.buscarCliente(cpf);
            if (cliente != null) {
                System.out.println("=> Bem-vindo de volta, " + cliente.getNome() + "!");
            } else {
                System.out.println("=> CPF não encontrado na base.");
                System.out.print("Deseja se cadastrar no programa de fidelidade para ganhar XP? [S/N]: ");
                String querCadastrar = scanner.nextLine().trim().toUpperCase();

                if (querCadastrar.equalsIgnoreCase("S")) {
                    System.out.print("Informe o nome do cliente: ");
                    String nome = scanner.nextLine().trim();
                    cliente = new ClienteStandard(cpf, nome);
                    repo.adicionarCliente(cliente);
                    System.out.println("=> " + nome + " cadastrado(a) com sucesso como Aventureiro(a) Iniciante!");
                } else {
                    System.out.println("=> Prosseguindo como Cliente Casual.");
                }
            }
        }

        Pedido pedido = new Pedido(cliente, atendenteLogado);
        System.out.println("\nPedido #" + pedido.getId() + " aberto.");

        boolean finalizado = false;
        while (!finalizado) {
            System.out.println("\n------------------------------");
            System.out.println("[a] Ver Cardápio");
            System.out.println("[b] Adicionar Item ao Carrinho");
            System.out.println("[c] Finalizar e Pagar");
            System.out.print("Escolha: ");
            String op = scanner.nextLine().trim().toLowerCase();

            switch (op) {
                case "a":
                    repo.listarProdutos();
                    break;
                case "b":
                    System.out.print("Código do produto: ");
                    String cod = scanner.nextLine().trim();
                    Produto p = repo.buscarProduto(cod);

                    if (p == null) {
                        System.out.println("=> ERRO: Produto não encontrado no catálogo.");
                        break;
                    }

                    if (p instanceof Bebida) {
                        System.out.println("Produto identificado como Bebida. Personalize:");
                        Tamanho tam = null;
                        while(tam == null) {
                            System.out.print("Tamanho [PEQUENO, MEDIO, GRANDE]: ");
                            try { tam = Tamanho.valueOf(scanner.nextLine().trim().toUpperCase()); }
                            catch (IllegalArgumentException e) { System.out.println("Tamanho inválido."); }
                        }
                        Temperatura temp = null;
                        while(temp == null) {
                            System.out.print("Temperatura [QUENTE, GELADO]: ");
                            try { temp = Temperatura.valueOf(scanner.nextLine().trim().toUpperCase()); }
                            catch (IllegalArgumentException e) { System.out.println("Temperatura inválida."); }
                        }
                    }

                    System.out.print("Quantidade desejada: ");
                    int qtd = lerInteiro();

                    try {
                        pedido.adicionarItem(p, qtd);
                        System.out.println("=> Item(ns) adicionado(s) com sucesso!");
                    } catch (EstoqueInsuficienteException e) {
                        System.err.println("=> ERRO: " + e.getMessage());
                    }
                    break;
                case "c":
                    finalizarPagamento(pedido);
                    finalizado = true;
                    break;
                default:
                    System.out.println("=> Opção inválida.");
            }
        }
        System.out.println("\nPressione ENTER para voltar ao Menu Principal...");
        scanner.nextLine();
    }

    private static void finalizarPagamento(Pedido pedido) {
        if (pedido.getItens().isEmpty()) {
            System.out.println("=> O pedido está vazio e foi cancelado.");
            return;
        }

        limparTela();
        System.out.println("--- RESUMO DO PAGAMENTO ---");
        double total = pedido.calcularTotal();

        System.out.print("Hoje é Dia de Evento Geek? [S/N]: ");
        String evt = scanner.nextLine().trim().toUpperCase();

        if (evt.equalsIgnoreCase("S")) {
            Promocional promoGeek = new DiaEventoGeek();
            double desconto = promoGeek.aplicarDesconto(pedido.calcularTotalBebidas());
            total -= desconto;
            System.out.printf("=> Desconto Promocional (10%% em bebidas): - R$ %.2f\n", desconto);
        }

        System.out.printf("\nTOTAL A PAGAR: R$ %.2f\n", total);
        boolean pagoComXP = false;

        Cliente c = pedido.getCliente();
        if (c instanceof ClienteVIP vip) {
            double xpNecessario = total * ClienteVIP.TAXA_CONVERSAO;
            System.out.printf("\n[DADOS VIP] Saldo: %.2f XP | Custo da compra: %.2f XP\n", vip.getSaldoXP(), xpNecessario);
            System.out.print("Deseja pagar inteiramente utilizando o seu XP? [S/N]: ");
            if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
                try {
                    vip.pagarComXP(total);
                    System.out.println("=> SUCESSO: Conta paga inteiramente com pontos de experiência!");
                    pagoComXP = true;
                } catch (PontosInsuficientesException e) {
                    System.err.println("=> ERRO VIP: " + e.getMessage());
                    System.out.println("=> O pagamento será revertido para método convencional (Dinheiro/Cartão).");
                }
            }
        }

        if (!pagoComXP) {
            System.out.println("=> Pagamento convencional (Dinheiro/Cartão) aprovado no sistema.");
            if (c != null) {
                c.calcularXPGanho(total);
                System.out.println("=> Pontos de fidelidade computados! Novo saldo: " + c.getSaldoXP() + " XP.");
            }
        }

        pedido.efetivarSaidaEstoque();
        System.out.println("\n=> Venda finalizada. Obrigado por visitar a Byte & Brew!");
    }

    private static void fluxoFuncionario() {
        limparTela();
        System.out.println("--- PAINEL DE GESTÃO ---");
        System.out.println("1 - Adicionar nova Comida");
        System.out.println("2 - Adicionar nova Bebida");
        System.out.println("3 - Listar Produtos / Estoque");
        System.out.println("4 - Cadastrar novo Cliente");
        System.out.println("5 - Listar Clientes Fidelidade");
        System.out.println("6 - Deletar Produto");
        System.out.print("Opção: ");
        int op = lerInteiro();

        switch (op) {
            case 1:
                System.out.print("Código único: "); String codC = scanner.nextLine().trim();
                System.out.print("Nome da Comida: "); String nomeC = scanner.nextLine().trim();
                System.out.print("Preço Base (ex: 15.50): "); double preC = lerDouble();
                System.out.print("Estoque Inicial: "); int estC = lerInteiro();
                System.out.print("Tempo de Preparo (min): "); int tmpC = lerInteiro();
                System.out.print("É vegano/sem glúten? [true/false]: "); boolean veg = Boolean.parseBoolean(scanner.nextLine().trim());
                repo.adicionarProduto(new Comida(codC, nomeC, preC, estC, tmpC, veg));
                System.out.println("=> Comida registrada com sucesso!");
                break;
            case 2:
                System.out.print("Código único: "); String codB = scanner.nextLine().trim();
                System.out.print("Nome da Bebida: "); String nomeB = scanner.nextLine().trim();
                System.out.print("Preço Base (ex: 12.00): "); double preB = lerDouble();
                System.out.print("Estoque Inicial: "); int estB = lerInteiro();
                Tamanho tam = null;
                while(tam == null) {
                    System.out.print("Tamanho [PEQUENO, MEDIO, GRANDE]: ");
                    try { tam = Tamanho.valueOf(scanner.nextLine().trim().toUpperCase()); }
                    catch (IllegalArgumentException e) { System.out.println("Tamanho inválido."); }
                }
                Temperatura temp = null;
                while(temp == null) {
                    System.out.print("Temperatura [QUENTE, GELADO]: ");
                    try { temp = Temperatura.valueOf(scanner.nextLine().trim().toUpperCase()); }
                    catch (IllegalArgumentException e) { System.out.println("Temperatura inválida."); }
                }
                System.out.print("Cafeína (mg): "); int caf = lerInteiro();
                repo.adicionarProduto(new Bebida(codB, nomeB, preB, estB, tam, temp, caf));
                System.out.println("=> Bebida registrada com sucesso!");
                break;
            case 3: repo.listarProdutos(); break;
            case 4:
                System.out.print("CPF: "); String cpf = scanner.nextLine().trim();
                System.out.print("Nome do Cliente: "); String nomeCli = scanner.nextLine().trim();
                System.out.print("O cliente será VIP? [S/N]: "); String vip = scanner.nextLine().trim();
                if (vip.equalsIgnoreCase("S")) repo.adicionarCliente(new ClienteVIP(cpf, nomeCli));
                else repo.adicionarCliente(new ClienteStandard(cpf, nomeCli));
                System.out.println("=> Cliente cadastrado no programa de fidelidade!");
                break;
            case 5: repo.listarClientes(); break;
            default: System.out.println("=> Opção inválida.");
            case 6: System.out.println("Código único: "); String codDel = scanner.nextLine().trim();
                repo.deletarProduto(codDel); 
                System.out.println("=> Produto deletado (se existia)."); break;
        }
        System.out.println("\nPressione ENTER para voltar ao Menu Principal...");
        scanner.nextLine();
    }

    private static int lerInteiro() {
        while (true) {
            try { return Integer.parseInt(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print("=> [ERRO] Digite apenas números inteiros: "); }
        }
    }

    private static double lerDouble() {
        while (true) {
            try { return Double.parseDouble(scanner.nextLine().replace(",", ".").trim()); }
            catch (NumberFormatException e) { System.out.print("=> [ERRO] Digite um valor numérico (ex: 15.50): "); }
        }
    }

    private static void limparTela() { for (int i = 0; i < 50; ++i) System.out.println(); }

    private static void carregarDadosIniciais() {
        repo.adicionarProduto(new Comida("C01", "Lembas Bread", 15.0, 10, 5, true));
        repo.adicionarProduto(new Bebida("B01", "Poção de Mana", 12.0, 20, Tamanho.MEDIO, Temperatura.GELADO, 0));
        repo.adicionarProduto(new Bebida("B02", "Café do Programador", 8.0, 50, Tamanho.GRANDE, Temperatura.QUENTE, 250));
        repo.adicionarCliente(new ClienteVIP("111", "Ada Lovelace"));
        repo.adicionarCliente(new ClienteStandard("222", "Alan Turing"));
    }

    //Codigo Para o Teste de Mesa
    private static void executarTesteDeMesa(){
        System.out.println("=== INICIANDO TESTE DE MESA AUTOMATIZADO ===\n");
        System.out.println("[SETUP] Carregando aventureiros e cardápio...");
        ClienteStandard alan = (ClienteStandard) repo.buscarCliente("222"); //Cliente Alan Turing
        ClienteVIP ada = (ClienteVIP) repo.buscarCliente("111"); //Cliente Ada Lovelace

        Produto lembas = repo.buscarProduto("C01");
        Produto pocaoMana = repo.buscarProduto("B01");
        Produto cafe = repo.buscarProduto("B02");

        System.out.println("\n-> TESTE 1: Polimorfismo por Sobrecarga e Herança");
        Pedido pedidoAlan = new Pedido(alan, atendenteLogado);
        try {
            pedidoAlan.adicionarItem(lembas);
            pedidoAlan.adicionarItem(pocaoMana, 2); 
            System.out.println("[OK] Itens adicionados com sucesso ao pedido do Alan.");
        } catch (EstoqueInsuficienteException e) {
            System.out.println("[ERRO] Falha inesperada: " + e.getMessage());
        }

        System.out.println("\n-> TESTE 2: Exceção Checked - Estoque Insuficiente");
        try {
            System.out.println("Tentando comprar 15 Lembas Bread (Estoque disponível: " + lembas.getQuantidadeEstoque() + ")...");
            pedidoAlan.adicionarItem(lembas, 15);
        } catch (EstoqueInsuficienteException e) {
            System.out.println("[OK] Exceção capturada com sucesso: " + e.getMessage());
        }

        System.out.println("\n-> TESTE 3: Interface Promocional e Cálculo de XP (Sobrescrita)");
        Promocional diaGeek = new DiaEventoGeek();
        double totalBebidas = pedidoAlan.calcularTotalBebidas();
        double desconto     = diaGeek.aplicarDesconto(totalBebidas);
        double valorAlan    = pedidoAlan.calcularTotal() - desconto;
        alan.calcularXPGanho(valorAlan);
        pedidoAlan.efetivarSaidaEstoque();
        System.out.printf("[OK] Total com desconto: R$ %.2f%n", valorAlan);
        System.out.println("[OK] Saldo XP Alan: " + alan.getSaldoXP() + " XP");


        System.out.println("\n-> TESTE 4: Exceção Checked - Pontos Insuficientes (Cliente VIP)");
        Pedido pedidoAda = new Pedido(ada, atendenteLogado);
        try {
            pedidoAda.adicionarItem(cafe, 5);
            double totalAda = pedidoAda.calcularTotal(); // R$ 40,00
            System.out.println("Tentando pagar com XP (Saldo: " + ada.getSaldoXP() + " XP)...");
            ada.pagarComXP(totalAda);
        } catch (PontosInsuficientesException e) {
            System.out.println("[OK] Exceção capturada: " + e.getMessage());
        } catch (EstoqueInsuficienteException e) {
            System.out.println("[ERRO] Falha de estoque inesperada.");
        }

        System.out.println("\n-> TESTE 5: Pagamento bem-sucedido com XP (Cliente VIP)");
        ada.calcularXPGanho(200.0); // Ada faz compras e acumula XP: 200 * 2 = 400 XP
        System.out.println("Ada acumulou XP em compras anteriores. Saldo: " + ada.getSaldoXP() + " XP");

        Pedido pedidoVip = new Pedido(ada, atendenteLogado);
        try {
            pedidoVip.adicionarItem(cafe, 2); // R$ 16,00 -> custa 160 XP
            double totalVip = pedidoVip.calcularTotal();
            ada.pagarComXP(totalVip);
            pedidoVip.efetivarSaidaEstoque();
            System.out.printf("[OK] Compra de R$ %.2f paga inteiramente com XP! Saldo restante: %.0f XP%n",
                    totalVip, ada.getSaldoXP());
        } catch (PontosInsuficientesException | EstoqueInsuficienteException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }

        System.out.println("\n=== FIM DO TESTE DE MESA ===");
        System.out.println("Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
}
