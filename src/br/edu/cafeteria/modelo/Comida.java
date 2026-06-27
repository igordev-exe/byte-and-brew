package br.edu.cafeteria.modelo;

public class Comida extends Produto {
    private int tempoPreparo;
    private boolean semGluten;
    private boolean vegano;

    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public boolean isSemGluten() {
        return semGluten;
    }

    public void setSemGluten(boolean semGluten) {
        this.semGluten = semGluten;
    }

    public boolean isVegano() {
        return vegano;
    }

    public void setVegano(boolean vegano) {
        this.vegano = vegano;
    }
}
