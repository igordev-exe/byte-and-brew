package br.edu.cafeteria.modelo;

public class Atendente {
    private String nome;
    private String matricula;

    public Atendente(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }

    @Override
    public String toString() {
        return "[" + matricula + "] " + nome;
    }
}
