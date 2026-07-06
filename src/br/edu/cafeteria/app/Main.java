package br.edu.cafeteria.app;

import br.edu.cafeteria.excecao.*;
import br.edu.cafeteria.modelo.*;
import br.edu.cafeteria.servico.*;
import java.util.Scanner;

public class 1Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GerenciadorCafeteria repo = new GerenciadorCafeteria();
    private static final Atendente atendenteLogado = new Atendente("MAT-1001", "Gordon Freeman");

    // Paleta ANSI (256 cores) - "Byte" = azul bebe (tech/XP)  |  "Brew" = rosa (cafe/dinheiro)
    private static final String RESET   = "\u001B[0m";
    private static final String BOLD    = "\u001B[1m";
    private static final String ROSA    = "\u001B[38;5;212m";
    private static final String AZUL_BB = "\u001B[38;5;159m";
    private static final String CREME   = "\u001B[38;5;230m";
    private static final String MARROM  = "\u001B[38;5;173m";
    private static final String VERDE   = "\u001B[38;5;120m";
    private static final String CORAL   = "\u001B[38;5;210m";

    public static void main(String[] args) {
        carregarDadosIniciais();

        int opcao;
        do {
            limparTela();
            imprimirTitulo("BYTE & BREW");
            System.out.println(AZUL_BB + "     ☕ Sistema de Vendas e Fidelidade ☕" + RESET);
            System.out.println();
            imprimirLogo();
            System.out.println();
            System.out.println(ROSA + "👤 Operador de Caixa: " + RESET + BOLD + CREME + atendenteLogado.getNome() + RESET);
            imprimirLinha();
            item("0", "🧪 Teste de Mesa");
            item("1", "🛒 Modo Cliente (Caixa/Venda)");
            item("2", "🔧 Modo Funcionário (Gestão)");
            item("3", "🚪 Sair do Sistema");
            imprimirLinha();
            System.out.print(AZUL_BB + "Escolha uma opção: " + RESET);
            opcao = lerInteiro();

            switch (opcao) {
                case 0: executarTesteDeMesa(); break;
                case 1: fluxoCliente(); break;
                case 2: fluxoFuncionario(); break;
                case 3: System.out.println("\n" + ROSA + "👋 Encerrando a operação do caixa. Até logo!" + RESET); break;
                default:
                    erro("Opção inválida! Pressione ENTER para continuar.");
                    scanner.nextLine();
            }
        } while (opcao != 3);

        scanner.close();
    }

    private static void fluxoCliente() {
        limparTela();
        imprimirTitulo("ABERTURA DE PEDIDO");
        System.out.print(AZUL_BB + "Informe o CPF do cliente (ou ENTER para Cliente Casual): " + RESET);
        String cpf = scanner.nextLine().trim();

        Cliente cliente = null;
        if (!cpf.isEmpty()) {
            cliente = repo.buscarCliente(cpf);
            if (cliente != null) {
                ok("Bem-vindo de volta, " + cliente.getNome() + "!");
            } else {
                aviso("CPF não encontrado na base.");
                System.out.print(AZUL_BB + "Deseja se cadastrar no programa de fidelidade para ganhar XP? [S/N]: " + RESET);
                String querCadastrar = scanner.nextLine().trim().toUpperCase();

                if (querCadastrar.equalsIgnoreCase("S")) {
                    if (!cpfValido(cpf)) {
                        erro("CPF inválido para cadastro (11 dígitos numéricos). Prosseguindo como Cliente Casual.");
                    } else {
                        System.out.print(AZUL_BB + "Informe o nome do cliente: " + RESET);
                        String nome = scanner.nextLine().trim();
                        cliente = new ClienteStandard(cpf, nome);
                        repo.adicionarCliente(cliente);
                        ok(nome + " cadastrado(a) com sucesso como Aventureiro(a) Iniciante!");
                    }
                } else {
                    System.out.println(CREME + "➡️  Prosseguindo como Cliente Casual." + RESET);
                }
            }
        }

        Pedido pedido = new Pedido(cliente, atendenteLogado);
        System.out.println("\n" + ROSA + "🧾 Pedido #" + pedido.getId() + " aberto." + RESET);

        boolean finalizado = false;
        while (!finalizado) {
            System.out.println();
            imprimirLinha();
            item("a", "📋 Ver Cardápio");
            item("b", "➕ Adicionar Item ao Carrinho");
            item("c", "💳 Finalizar e Pagar");
            imprimirLinha();
            System.out.print(AZUL_BB + "Escolha: " + RESET);
            String op = scanner.nextLine().trim().toLowerCase();

            switch (op) {
                case "a":
                    repo.listarProdutos();
                    break;
                case "b":
                    System.out.print(AZUL_BB + "Código do produto: " + RESET);
                    String cod = scanner.nextLine().trim();
                    Produto p = repo.buscarProduto(cod);

                    if (p == null) {
                        erro("ERRO: Produto não encontrado no catálogo.");
                        break;
                    }

                    System.out.print(AZUL_BB + "Quantidade desejada: " + RESET);
                    int qtd = lerInteiro();

                    try {
                        pedido.adicionarItem(p, qtd);
                        ok("Item(ns) adicionado(s) com sucesso!");
                    } catch (EstoqueInsuficienteException | QuantidadeInvalidaException e) {
                        System.err.println(CORAL + "❌ ERRO: " + e.getMessage() + RESET);
                    }
                    break;
                case "c":
                    finalizarPagamento(pedido);
                    finalizado = true;
                    break;
                default:
                    erro("Opção inválida.");
            }
        }
        System.out.println("\nPressione ENTER para voltar ao Menu Principal...");
        scanner.nextLine();
    }

    private static void finalizarPagamento(Pedido pedido) {
        if (pedido.getItens().isEmpty()) {
            aviso("O pedido está vazio e foi cancelado.");
            return;
        }

        limparTela();
        imprimirTitulo("RESUMO DO PAGAMENTO");
        double total = pedido.calcularTotal();

        System.out.print(AZUL_BB + "Hoje é Dia de Evento Geek? [S/N]: " + RESET);
        String evt = scanner.nextLine().trim().toUpperCase();

        if (evt.equalsIgnoreCase("S")) {
            Promocional promoGeek = new DiaEventoGeek();
            double desconto = promoGeek.aplicarDesconto(pedido.calcularTotalBebidas());
            total -= desconto;
            System.out.println(CREME + "🎉 Desconto Promocional (10% em bebidas): - " + dinheiro(desconto) + RESET);
        }

        imprimirLinha();
        System.out.println(BOLD + "💵 TOTAL A PAGAR: " + dinheiro(total) + RESET);
        imprimirLinha();
        boolean pagoComXP = false;

        Cliente c = pedido.getCliente();
        if (c instanceof ClienteVIP vip) {
            double xpNecessario = total * ClienteVIP.getTaxaConversao();
            System.out.println("\n" + ROSA + "👑 [DADOS VIP] " + RESET + "Saldo: " + xp(vip.getSaldoXP()) + " | Custo da compra: " + xp(xpNecessario));
            System.out.print(AZUL_BB + "Deseja pagar inteiramente utilizando o seu XP? [S/N]: " + RESET);
            if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
                try {
                    vip.pagarComXP(total);
                    ok("SUCESSO: Conta paga inteiramente com pontos de experiência!");
                    pagoComXP = true;
                } catch (PontosInsuficientesException e) {
                    System.err.println(CORAL + "❌ ERRO VIP: " + e.getMessage() + RESET);
                    System.out.println(CREME + "↩️  O pagamento será revertido para método convencional (Dinheiro/Cartão)." + RESET);
                }
            }
        }

        if (!pagoComXP) {
            System.out.println(CREME + "💳 Pagamento convencional (Dinheiro/Cartão) aprovado no sistema." + RESET);
            if (c != null) {
                c.calcularXPGanho(total);
                System.out.println(AZUL_BB + "⭐ Pontos de fidelidade computados! Novo saldo: " + xp(c.getSaldoXP()) + RESET);
            }
        }

        pedido.efetivarSaidaEstoque();
        imprimirLinha();
        System.out.println(ROSA + "☕ Venda finalizada. Obrigado por visitar a Byte & Brew!" + RESET);
    }

    private static void fluxoFuncionario() {
        limparTela();
        imprimirTitulo("PAINEL DE GESTAO");
        item("1", "🍞 Adicionar nova Comida");
        item("2", "🥤 Adicionar nova Bebida");
        item("3", "📋 Listar Produtos / Estoque");
        item("4", "👤 Cadastrar novo Cliente");
        item("5", "🏆 Listar Clientes Fidelidade");
        item("6", "🗑️  Deletar Produto");
        item("7", "✏️  Atualizar Produto (Preço/Estoque)");
        item("8", "✏️  Atualizar Nome do Cliente");
        item("9", "🗑️  Deletar Cliente");
        imprimirLinha();
        System.out.print(AZUL_BB + "Opção: " + RESET);
        int op = lerInteiro();

        switch (op) {
            case 1:
                System.out.print(AZUL_BB + "Código único: " + RESET); String codC = scanner.nextLine().trim();
                if (repo.buscarProduto(codC) != null) {
                    erro("ERRO: Já existe um produto cadastrado com esse código.");
                    break;
                }
                System.out.print(AZUL_BB + "Nome da Comida: " + RESET); String nomeC = scanner.nextLine().trim();
                System.out.print(AZUL_BB + "Preço Base (ex: 15.50): " + RESET); double preC = lerPrecoValido();
                System.out.print(AZUL_BB + "Estoque Inicial: " + RESET); int estC = lerEstoqueValido();
                System.out.print(AZUL_BB + "Tempo de Preparo (min): " + RESET); int tmpC = lerInteiro();
                System.out.print(AZUL_BB + "É vegano/sem glúten? [true/false]: " + RESET); boolean veg = Boolean.parseBoolean(scanner.nextLine().trim());
                repo.adicionarProduto(new Comida(codC, nomeC, preC, estC, tmpC, veg));
                ok("Comida registrada com sucesso!");
                break;
            case 2:
                System.out.print(AZUL_BB + "Código único: " + RESET); String codB = scanner.nextLine().trim();
                if (repo.buscarProduto(codB) != null) {
                    erro("ERRO: Já existe um produto cadastrado com esse código.");
                    break;
                }
                System.out.print(AZUL_BB + "Nome da Bebida: " + RESET); String nomeB = scanner.nextLine().trim();
                System.out.print(AZUL_BB + "Preço Base (ex: 12.00): " + RESET); double preB = lerPrecoValido();
                System.out.print(AZUL_BB + "Estoque Inicial: " + RESET); int estB = lerEstoqueValido();
                Tamanho tam = null;
                while(tam == null) {
                    System.out.print(AZUL_BB + "Tamanho [PEQUENO, MEDIO, GRANDE]: " + RESET);
                    try { tam = Tamanho.valueOf(scanner.nextLine().trim().toUpperCase()); }
                    catch (IllegalArgumentException e) { erro("Tamanho inválido."); }
                }
                Temperatura temp = null;
                while(temp == null) {
                    System.out.print(AZUL_BB + "Temperatura [QUENTE, GELADO]: " + RESET);
                    try { temp = Temperatura.valueOf(scanner.nextLine().trim().toUpperCase()); }
                    catch (IllegalArgumentException e) { erro("Temperatura inválida."); }
                }
                System.out.print(AZUL_BB + "Cafeína (mg): " + RESET); int caf = lerInteiro();
                repo.adicionarProduto(new Bebida(codB, nomeB, preB, estB, tam, temp, caf));
                ok("Bebida registrada com sucesso!");
                break;
            case 3: repo.listarProdutos(); break;
            case 4:
                System.out.print(AZUL_BB + "CPF: " + RESET); String cpf = scanner.nextLine().trim();
                if (!cpfValido(cpf)) {
                    erro("ERRO: CPF inválido. Informe exatamente 11 dígitos numéricos.");
                    break;
                }
                if (repo.buscarCliente(cpf) != null) {
                    erro("ERRO: Já existe um cliente cadastrado com esse CPF.");
                    break;
                }
                System.out.print(AZUL_BB + "Nome do Cliente: " + RESET); String nomeCli = scanner.nextLine().trim();
                System.out.print(AZUL_BB + "O cliente será VIP? [S/N]: " + RESET); String vip = scanner.nextLine().trim();
                if (vip.equalsIgnoreCase("S")) repo.adicionarCliente(new ClienteVIP(cpf, nomeCli));
                else repo.adicionarCliente(new ClienteStandard(cpf, nomeCli));
                ok("Cliente cadastrado no programa de fidelidade!");
                break;
            case 5: repo.listarClientes(); break;
            case 6:
                System.out.print(AZUL_BB + "Código do produto: " + RESET);
                String codDel = scanner.nextLine().trim();
                Produto p = repo.buscarProduto(codDel);

                if (p == null) {
                    erro("ERRO: Produto não encontrado no catálogo.");
                } else {
                    repo.deletarProduto(codDel);
                    ok("Produto removido do sistema com sucesso!");
                }
                break;
            case 7:
                System.out.print(AZUL_BB + "Código do produto a atualizar: " + RESET);
                String codUpd = scanner.nextLine().trim();
                Produto pUpd = repo.buscarProduto(codUpd);

                if (pUpd == null) {
                    erro("ERRO: Produto não encontrado no catálogo.");
                    break;
                }

                System.out.println(CREME + "📦 Produto atual: " + pUpd + RESET);
                System.out.print(AZUL_BB + "Novo preço (ENTER para manter o atual): " + RESET);
                String precoStr = scanner.nextLine().trim();
                if (!precoStr.isEmpty()) {
                    try {
                        double novoPreco = Double.parseDouble(precoStr.replace(",", "."));
                        if (novoPreco <= 0) {
                            erro("ERRO: O preço deve ser maior que zero. Preço não foi alterado.");
                        } else {
                            repo.atualizarPrecoProduto(codUpd, novoPreco);
                            ok("Preço atualizado.");
                        }
                    } catch (NumberFormatException e) {
                        erro("Valor inválido, preço não foi alterado.");
                    }
                }

                System.out.print(AZUL_BB + "Quantidade a adicionar ao estoque (0 para não alterar): " + RESET);
                int qtdRepor = lerInteiro();
                if (qtdRepor > 0) {
                    repo.reporEstoqueProduto(codUpd, qtdRepor);
                    ok("Estoque reposto.");
                }

                ok("Produto atualizado com sucesso!");
                break;
            case 8:
                System.out.print(AZUL_BB + "CPF do cliente a atualizar: " + RESET);
                String cpfUpd = scanner.nextLine().trim();
                Cliente cUpd = repo.buscarCliente(cpfUpd);

                if (cUpd == null) {
                    erro("ERRO: Cliente não encontrado no cadastro.");
                    break;
                }

                System.out.println(CREME + "👤 Cliente atual: " + cUpd + RESET);
                System.out.print(AZUL_BB + "Novo nome: " + RESET);
                String novoNome = scanner.nextLine().trim();
                if (novoNome.isEmpty()) {
                    erro("Nome não pode ser vazio. Nada foi alterado.");
                } else {
                    repo.atualizarNomeCliente(cpfUpd, novoNome);
                    ok("Nome atualizado com sucesso!");
                }
                break;
            case 9:
                System.out.print(AZUL_BB + "CPF do cliente a remover: " + RESET);
                String cpfDel = scanner.nextLine().trim();
                Cliente cDel = repo.buscarCliente(cpfDel);

                if (cDel == null) {
                    erro("ERRO: Cliente não encontrado no cadastro.");
                    break;
                }

                repo.removerCliente(cpfDel);
                ok("Cliente removido do programa de fidelidade com sucesso!");
                break;
            default:
                erro("Opção inválida.");
        }
        System.out.println("\nPressione ENTER para voltar ao Menu Principal...");
        scanner.nextLine();
    }

    private static int lerInteiro() {
        while (true) {
            try { return Integer.parseInt(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print(CORAL + "❌ [ERRO] Digite apenas números inteiros: " + RESET); }
        }
    }

    private static double lerDouble() {
        while (true) {
            try { return Double.parseDouble(scanner.nextLine().replace(",", ".").trim()); }
            catch (NumberFormatException e) { System.out.print(CORAL + "❌ [ERRO] Digite um valor numérico (ex: 15.50): " + RESET); }
        }
    }

    private static double lerPrecoValido() {
        while (true) {
            double valor = lerDouble();
            if (valor > 0) return valor;
            System.out.print(CORAL + "❌ ERRO: O preço deve ser maior que zero. Digite novamente: " + RESET);
        }
    }

    private static int lerEstoqueValido() {
        while (true) {
            int valor = lerInteiro();
            if (valor >= 0) return valor;
            System.out.print(CORAL + "❌ ERRO: O estoque não pode ser negativo. Digite novamente: " + RESET);
        }
    }

    private static boolean cpfValido(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }

    private static void limparTela() { for (int i = 0; i < 50; ++i) System.out.println(); }

    private static void ok(String msg)    { System.out.println(VERDE + "✅ " + msg + RESET); }
    private static void erro(String msg)  { System.out.println(CORAL + "❌ " + msg + RESET); }
    private static void aviso(String msg) { System.out.println(AZUL_BB + "⚠️  " + msg + RESET); }

    private static String dinheiro(double v) { return BOLD + ROSA + String.format("R$ %.2f", v) + RESET; }
    private static String xp(double v)       { return BOLD + AZUL_BB + String.format("%.2f XP", v) + RESET; }

    private static void item(String chave, String texto) {
        System.out.println("  " + BOLD + ROSA + "[" + chave + "]" + RESET + " " + CREME + texto + RESET);
    }

    // Listra "pixelada" alternando blocos de 4 caracteres entre rosa e azul bebê
    private static String listra(int tamanho) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            sb.append((i / 4) % 2 == 0 ? ROSA : AZUL_BB).append('═');
        }
        return sb.toString();
    }

    private static void imprimirLinha() {
        System.out.println(listra(50) + RESET);
    }

    private static void imprimirTitulo(String texto) {
        int largura = Math.max(48, texto.length() + 4);
        int espacos = largura - texto.length();
        int esq = espacos / 2, dir = espacos - esq;
        System.out.println(ROSA + "╔" + listra(largura) + AZUL_BB + "╗" + RESET);
        System.out.println(ROSA + "║" + RESET + BOLD + CREME + " ".repeat(esq) + texto + " ".repeat(dir) + RESET + AZUL_BB + "║" + RESET);
        System.out.println(ROSA + "╚" + listra(largura) + AZUL_BB + "╝" + RESET);
    }

    // Xícara em "pixel art": vapor azul bebê, corpo rosa, café em marrom
    private static void imprimirLogo() {
        System.out.println(AZUL_BB + "        ( (   )" + RESET);
        System.out.println(AZUL_BB + "         )  (" + RESET);
        System.out.println(ROSA + "       .-\"``\"-." + RESET);
        System.out.println(ROSA + "      /  " + MARROM + "░▒▓██" + ROSA + "  \\" + RESET);
        System.out.println(ROSA + "     |  " + MARROM + "▓█████▓" + ROSA + "  |" + RESET);
        System.out.println(ROSA + "     |  " + MARROM + "▓█████▓" + ROSA + "  )" + RESET);
        System.out.println(ROSA + "      \\  " + MARROM + "░▒▓██" + ROSA + "  /" + RESET);
        System.out.println(ROSA + "       `-.....-'" + RESET);
    }

    private static void carregarDadosIniciais() {
        repo.adicionarProduto(new Comida("C01", "Lembas Bread", 15.0, 10, 5, true));
        repo.adicionarProduto(new Bebida("B01", "Poção de Mana", 12.0, 20, Tamanho.MEDIO, Temperatura.GELADO, 0));
        repo.adicionarProduto(new Bebida("B02", "Café do Programador", 8.0, 50, Tamanho.GRANDE, Temperatura.QUENTE, 250));
        repo.adicionarCliente(new ClienteVIP("111", "Ada Lovelace"));
        repo.adicionarCliente(new ClienteStandard("222", "Alan Turing"));
    }

    private static void executarTesteDeMesa(){
        limparTela();
        imprimirTitulo("TESTE DE MESA AUTOMATIZADO");
        System.out.println(AZUL_BB + "🔧 [SETUP] Carregando aventureiros e cardápio..." + RESET);
        ClienteStandard alan = (ClienteStandard) repo.buscarCliente("222");
        ClienteVIP ada = (ClienteVIP) repo.buscarCliente("111");

        Produto lembas = repo.buscarProduto("C01");
        Produto pocaoMana = repo.buscarProduto("B01");
        Produto cafe = repo.buscarProduto("B02");

        System.out.println("\n" + ROSA + BOLD + "🧪 TESTE 1: Polimorfismo por Sobrecarga e Herança" + RESET);
        Pedido pedidoAlan = new Pedido(alan, atendenteLogado);
        try {
            pedidoAlan.adicionarItem(lembas);
            pedidoAlan.adicionarItem(pocaoMana, 2);
            ok("Itens adicionados com sucesso ao pedido do Alan.");
        } catch (EstoqueInsuficienteException | QuantidadeInvalidaException e) {
            erro("Falha inesperada: " + e.getMessage());
        }

        System.out.println("\n" + ROSA + BOLD + "🧪 TESTE 2: Exceção Checked - Estoque Insuficiente" + RESET);
        try {
            System.out.println("Tentando comprar 15 Lembas Bread (Estoque disponível: " + lembas.getQuantidadeEstoque() + ")...");
            pedidoAlan.adicionarItem(lembas, 15);
        } catch (EstoqueInsuficienteException | QuantidadeInvalidaException e) {
            ok("Exceção capturada com sucesso: " + e.getMessage());
        }

        System.out.println("\n" + ROSA + BOLD + "🧪 TESTE 3: Interface Promocional e Cálculo de XP (Sobrescrita)" + RESET);
        Promocional diaGeek = new DiaEventoGeek();
        double totalBebidas = pedidoAlan.calcularTotalBebidas();
        double desconto     = diaGeek.aplicarDesconto(totalBebidas);
        double valorAlan    = pedidoAlan.calcularTotal() - desconto;
        alan.calcularXPGanho(valorAlan);
        pedidoAlan.efetivarSaidaEstoque();
        System.out.println(VERDE + "✅ [OK] Total com desconto: " + dinheiro(valorAlan) + RESET);
        System.out.println(AZUL_BB + "⭐ [OK] Saldo XP Alan: " + xp(alan.getSaldoXP()) + RESET);


        System.out.println("\n" + ROSA + BOLD + "🧪 TESTE 4: Exceção Checked - Pontos Insuficientes (Cliente VIP)" + RESET);
        Pedido pedidoAda = new Pedido(ada, atendenteLogado);
        try {
            pedidoAda.adicionarItem(cafe, 5);
            double totalAda = pedidoAda.calcularTotal();
            System.out.println("Tentando pagar com XP (Saldo: " + ada.getSaldoXP() + " XP)...");
            ada.pagarComXP(totalAda);
        } catch (PontosInsuficientesException e) {
            ok("Exceção capturada: " + e.getMessage());
        } catch (EstoqueInsuficienteException | QuantidadeInvalidaException e) {
            erro("Falha de estoque inesperada.");
        }

        System.out.println("\n" + ROSA + BOLD + "🧪 TESTE 5: Pagamento bem-sucedido com XP (Cliente VIP)" + RESET);
        ada.calcularXPGanho(200.0);
        System.out.println("Ada acumulou XP em compras anteriores. Saldo: " + ada.getSaldoXP() + " XP");

        Pedido pedidoVip = new Pedido(ada, atendenteLogado);
        try {
            pedidoVip.adicionarItem(cafe, 2);
            double totalVip = pedidoVip.calcularTotal();
            ada.pagarComXP(totalVip);
            pedidoVip.efetivarSaidaEstoque();
            System.out.println(VERDE + "✅ [OK] Compra de " + dinheiro(totalVip) + " paga inteiramente com XP! Saldo restante: " + xp(ada.getSaldoXP()) + RESET);
        } catch (PontosInsuficientesException | EstoqueInsuficienteException | QuantidadeInvalidaException e) {
            erro(e.getMessage());
        }

        imprimirLinha();
        System.out.println(ROSA + BOLD + "🏁 FIM DO TESTE DE MESA" + RESET);
        System.out.println("Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
}