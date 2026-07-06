package br.edu.cafeteria.modelo;

public class Bebida extends Produto {
    private Tamanho tamanho; // Agora usando o Enum
    private Temperatura temperatura; // Agora usando o Enum
    private int mgCafeina;

    public Bebida(String codigo, String nome, double precoBase, int estoque, Tamanho tamanho, Temperatura temperatura, int mgCafeina) {
        super(codigo, nome, precoBase, estoque);
        this.tamanho = tamanho;
        this.temperatura = temperatura;
        this.mgCafeina = mgCafeina;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" - Tam: %s, Temp: %s, Cafeína: %dmg", tamanho, temperatura, mgCafeina);
    }
}
