package br.edu.cafeteria.modelo;

public class Bebida extends Produto{
    private int qtdCafeina;
    private String temperatura;
    private String tamanho;
    public int getQtdCafeina() {
        return qtdCafeina;
    }

    public void setQtdCafeina(int qtdCafeina) {
        this.qtdCafeina = qtdCafeina;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
