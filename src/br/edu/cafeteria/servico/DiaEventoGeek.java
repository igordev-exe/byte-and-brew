package br.edu.cafeteria.servico;

public class DiaEventoGeek implements Promocional {

    @Override
    public double aplicarDesconto(double valorCalculado) {
        // Aplica o desconto de 10% exigido no enunciado para bebidas
        return valorCalculado * 0.10;
    }
}
