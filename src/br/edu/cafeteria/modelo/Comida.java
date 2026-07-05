package br.edu.cafeteria.modelo;

// [OO: Herança Simples] - Comida herda de Produto
public class Comida extends Produto {
    private int tempoPreparo;
    private boolean veganoOuSemGluten;

    public Comida(String codigo, String nome, double precoBase, int estoque, int tempoPreparo, boolean veganoOuSemGluten) {
        super(codigo, nome, precoBase, estoque);
        this.tempoPreparo = tempoPreparo;
        this.veganoOuSemGluten = veganoOuSemGluten;
    }

    @Override
    public String toString() {
        String info = veganoOuSemGluten ? "(Especial/Vegano)" : "";
        return super.toString() + String.format(" - Preparo: %d min %s", tempoPreparo, info);
    }
}
