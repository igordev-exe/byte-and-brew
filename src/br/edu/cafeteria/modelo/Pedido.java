package br.edu.cafeteria.modelo;
import java.util.ArrayList;
import java.util.List;
import br.edu.cafeteria.excecao.EstoqueInsuficienteException;

public class Pedido {
    private List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(Produto produto, int quantidade) throws EstoqueInsuficienteException {
        produto.reduzirEstoque(quantidade);
        itens.add(new ItemPedido(produto, quantidade));
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }
}
