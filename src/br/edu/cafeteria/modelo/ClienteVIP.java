package br.edu.cafeteria.modelo;

import br.edu.cafeteria.excecao.PontosInsuficientesException;

public class ClienteVIP extends Cliente {
    // [OO: Modificador de Escopo Estático] - Constante de classe para conversão
    private static final int TAXA_CONVERSAO = 10;

    public ClienteVIP(String cpf, String nome) {
        super(cpf, nome);
    }

    public static int getTaxaConversao() {
        return TAXA_CONVERSAO;
    }

    @Override
    public void calcularXPGanho(double valor) {
        // [OO: Polimorfismo por Coerção] - (int) força a conversão do cálculo double
        int xpGanho = (int) (valor * 2);
        this.saldoXP += xpGanho;
    }

    public void pagarComXP(double valorTotal) throws PontosInsuficientesException {
        double xpNecessario = valorTotal * TAXA_CONVERSAO;
        if (this.saldoXP >= xpNecessario) {
            abaterXP(xpNecessario);
        } else {
            throw new PontosInsuficientesException(getCpf(), getSaldoXP(), xpNecessario);
        }
    }
}
