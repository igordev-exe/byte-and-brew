package br.edu.cafeteria.modelo;

public class Cliente {
    private String nome;
    private String cpf;
    private int saldoXP;
    
    public String getNome() {
        return nome;
    }
    
    public String getCpf() {
        return cpf;
    }

    public int getSaldoXP() {
        return saldoXP;
    }

    public int setSaldoXP(int saldoXP) {
        this.saldoXP = saldoXP;
        return saldoXP;
    }
}   
