package br.edu.cafeteria.servico;
import br.edu.cafeteria.modelo.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorCafeteria {
    private List<Produto> catalogo = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public void adicionarProduto(Produto p) { catalogo.add(p); }
    public void adicionarCliente(Cliente c) { clientes.add(c); }

    public void atualizarPrecoProduto(String codigo, double novoPreco) {
        Produto p = buscarProduto(codigo);
        if (p != null) p.setPrecoBase(novoPreco);
    }

    public void reporEstoqueProduto(String codigo, int quantidade) {
        Produto p = buscarProduto(codigo);
        if (p != null) p.reporEstoque(quantidade);
    }

    public void atualizarNomeCliente(String cpf, String novoNome) {
        Cliente c = buscarCliente(cpf);
        if (c != null) c.setNome(novoNome);
    }

    public void removerCliente(String cpf) {
        clientes.removeIf(c -> c.getCpf().equals(cpf));
    }

    public Produto buscarProduto(String codigo) {
        for (Produto p : catalogo) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) return p;
        }
        return null;
    }

    public Cliente buscarCliente(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) return c;
        }
        return null;
    }

    public void listarProdutos() {
        System.out.println("\n--- Cardápio / Estoque ---");
        for (Produto p : catalogo) {
            System.out.println(p.toString());
        }
    }

    public void listarClientes() {
        System.out.println("\n--- Clientes Cadastrados ---");
        for (Cliente c : clientes) {
            String tipo = (c instanceof ClienteVIP) ? "[VIP]" : "[STD]";
            System.out.println(tipo + " " + c.toString());
        }
    }

    public void deletarProduto(String codigo) {
        catalogo.removeIf(p -> p.getCodigo().equalsIgnoreCase(codigo));
    }
}
