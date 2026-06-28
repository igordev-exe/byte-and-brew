package br.edu.cafeteria.modelo;

import br.edu.cafeteria.excecao.EstoqueInsuficienteException;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    // [OO: Modificador de Escopo Estático] - Contador sequencial automático
    private static int contadorSequencial = 1;

    private int id;
    private Cliente cliente; // Pode ser null para cliente casual
    private Atendente atendente; // Associado na abertura

    // [OO: Polimorfismo por Inclusão] - Lista de Produto lida genericamente com Comidas e Bebidas
    private List<ItemPedido> itens;

    public Pedido(Cliente cliente, Atendente atendente) {
        this.id = contadorSequencial++;
        this.cliente = cliente;
        this.atendente = atendente;
        this.itens = new ArrayList<>();
    }

    // [OO: Polimorfismo por Sobrecarga] - Assinatura 1
    public void adicionarItem(Produto p) throws EstoqueInsuficienteException {
        adicionarItem(p, 1);
    }

    // [OO: Polimorfismo por Sobrecarga] - Assinatura 2
    public void adicionarItem(Produto p, int quantidade) throws EstoqueInsuficienteException {
        if (quantidade > p.getQuantidadeEstoque()) {
            throw new EstoqueInsuficienteException(
                    p.getCodigo(), quantidade, p.getQuantidadeEstoque());
        }
        itens.add(new ItemPedido(p, quantidade));
    }

    public double calcularTotalBebidas() {
        double total = 0;
        for (ItemPedido item : itens) {
            if (item.getProduto() instanceof Bebida) {
                total += item.getSubtotal();
            }
        }
        return total;
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void efetivarSaidaEstoque() {
        for (ItemPedido item : itens) {
            item.getProduto().abaterEstoque(item.getQuantidade());
        }
    }

    // [OO: Encapsulamento Defensivo] - Retorna uma cópia da lista, protegendo a original
    public List<ItemPedido> getItens() { return new ArrayList<>(itens); }
    public Cliente getCliente() { return cliente; }
    public Atendente getAtendente() { return atendente; }
    public int getId() { return id; }
}/*a*/