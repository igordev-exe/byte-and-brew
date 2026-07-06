package br.edu.cafeteria.excecao;

public class QuantidadeInvalidaException extends Exception {

    private final int quantidadeInformada;

    public QuantidadeInvalidaException(int quantidadeInformada) {
        super(String.format(
                "Quantidade inválida: %d. A quantidade deve ser maior que zero.",
                quantidadeInformada));
        this.quantidadeInformada = quantidadeInformada;
    }

    public int getQuantidadeInformada() {
        return quantidadeInformada;
    }
}
