package br.edu.cafeteria.modelo;

public class ClienteStandard extends Cliente {

    public ClienteStandard(String cpf, String nome) {
        super(cpf, nome);
    }

    @Override
    public void calcularXPGanho(double valor) {
        int xpGanho = (int) valor;
        this.saldoXP += xpGanho;
    }
}
