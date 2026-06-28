package br.edu.cafeteria.modelo;

public abstract class Cliente {

    
    protected String nome;
    protected String cpf;
    protected int    saldoXP;


    public Cliente(String nome, String cpf) {
        this.nome    = nome;
        this.cpf     = cpf;
        this.saldoXP = 0; // Saldo inicial zerado
    }

    public abstract int getTaxaXP();

    public int calcularXP(double valor) {
     
        return (int) (valor * getTaxaXP());
    }

    public void adicionarXP(int xp) {
        this.saldoXP += xp;
    }


    public String getNome() { return nome; }
    public void   setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void   setCpf(String cpf) { this.cpf = cpf; }

    public int  getSaldoXP() { return saldoXP; }
    public void setSaldoXP(int saldoXP) { this.saldoXP = saldoXP; }


    @Override
    public String toString() {
        return String.format("%-25s | CPF: %-15s | XP: %d", nome, cpf, saldoXP);
    }
}
