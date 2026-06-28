package br.edu.cafeteria.modelo;

import br.edu.cafeteria.excecao.PontosInsuficientesException;

public class ClienteVIP extends Cliente {

    private static final int TAXA_CONV = 10;

    public ClienteVIP(String nome, String cpf) {
        super(nome, cpf);
    }

  
    @Override
    public int getTaxaXP() {
        return 2;
    }

    @Override
    public int calcularXP(double valor) {
        return (int) (valor * getTaxaXP());
    }

    public void pagarComXP(Pedido pedido) throws PontosInsuficientesException {
        double total         = pedido.calcularTotal();
        
        int    xpNecessario  = (int) Math.ceil(total * TAXA_CONV);

        if (this.saldoXP < xpNecessario) {
            throw new PontosInsuficientesException(this.saldoXP, xpNecessario);
        }

      
        this.saldoXP -= xpNecessario;

        pedido.finalizarSemXP();

        System.out.printf("  [VIP] Pagamento com XP: -%d XP debitados (Total: R$ %.2f)%n",
                xpNecessario, total);
    }

    public static int getTaxaConv() {
        return TAXA_CONV;
    }

    @Override
    public String toString() {
        return "[VIP ★] " + super.toString();
    }
}
