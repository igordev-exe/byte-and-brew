package br.edu.cafeteria.modelo;

public abstract class Produto {
    // [OO: Encapsulamento estrito] - Atributos privados
    private String codigo;
    private String nome;
    private double precoBase;
    private int quantidadeEstoque;

    public Produto(String codigo, String nome, double precoBase, int quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.precoBase = precoBase;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void abaterEstoque(int quantidade) {
        this.quantidadeEstoque -= quantidade;
    }

    public void reporEstoque(int quantidade) {
        this.quantidadeEstoque += quantidade;
    }

    // Getters e Setters
    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public double getPrecoBase() { return precoBase; }
    public void setPrecoBase(double precoBase) { this.precoBase = precoBase; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f (Estoque: %d)", codigo, nome, precoBase, quantidadeEstoque);
    }
}
