package br.edu.cafeteria.modelo;

public abstract class Cliente {
    private String cpf;
    private String nome;
    protected double saldoXP; // protected para acesso nas filhas

    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.saldoXP = 0;
    }


    public abstract void calcularXPGanho(double valor);

    // Getters
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getSaldoXP() { return saldoXP; }
    public void abaterXP(double pontos) { this.saldoXP -= pontos; }

    @Override
    public String toString() {
        return String.format("CPF: %s | Nome: %s | XP: %.2f", cpf, nome, saldoXP);
    }
}