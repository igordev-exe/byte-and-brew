package br.edu.cafeteria.excecao;

public class PontosInsuficientesException extends Exception {

    private final String cpfCliente;
    private final double saldoAtualXp;
    private final double saldoNecessarioXp;

    public PontosInsuficientesException(String cpfCliente,
                                         double saldoAtualXp,
                                         double saldoNecessarioXp) {
        super(String.format(
                "Pontos de XP insuficientes para o cliente '%s': saldo atual %.1f, necessário %.1f.",
                cpfCliente, saldoAtualXp, saldoNecessarioXp));
        this.cpfCliente = cpfCliente;
        this.saldoAtualXp = saldoAtualXp;
        this.saldoNecessarioXp = saldoNecessarioXp;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public double getSaldoAtualXp() {
        return saldoAtualXp;
    }

    public double getSaldoNecessarioXp() {
        return saldoNecessarioXp;
    }
}
