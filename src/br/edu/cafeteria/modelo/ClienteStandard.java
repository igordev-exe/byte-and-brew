package br.edu.cafeteria.modelo;

public class ClienteStandard extends Cliente {

    public ClienteStandard(String cpf, String nome) {
        super(cpf, nome);
    }

    @Override
    public void calcularXPGanho(double valor) {
        // [OO: Polimorfismo por Coerção] - Convertendo double (valor) para int implicitamente através da regra de negócio (1 por real inteiro)
        int xpGanho = (int) valor;
        this.saldoXP += xpGanho;
    }
}/*a*/