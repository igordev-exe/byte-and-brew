package br.edu.cafeteria.modelo;

public class ClienteStandard extends Cliente {

    public ClienteStandard(String nome, String cpf) {
        super(nome, cpf);
    }

    @Override
    public int getTaxaXP() {
        return 1;
    }

    @Override
    public int calcularXP(double valor) {
      
        return (int) (valor * getTaxaXP());
    }


    @Override
    public String toString() {
        return "[Standard] " + super.toString();
    }
}
