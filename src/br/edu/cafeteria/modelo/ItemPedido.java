package br.edu.cafeteria.modelo;

public class ItemPedido {
    // [OO: Associação] - Relacionamento com Produto
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getSubtotal() { return produto.getPrecoBase() * quantidade; }
}/*a*/