package br.edu.cafeteria.excecao;

public class EstoqueInsuficienteException extends Exception {

    private final String codigoProduto;
    private final int quantidadeSolicitada;
    private final int quantidadeDisponivel;

    public EstoqueInsuficienteException(String codigoProduto,
                                         int quantidadeSolicitada,
                                         int quantidadeDisponivel) {
        super(String.format(
                "Estoque insuficiente para o produto '%s': solicitado %d, disponível %d.",
                codigoProduto, quantidadeSolicitada, quantidadeDisponivel));
        this.codigoProduto = codigoProduto;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
}
