package Resultados;

public class Resultado {
    private final int valor;
    private final int distancia;

    public Resultado(int valor, int distancia) {
        this.valor = valor;
        this.distancia = distancia;
    }

    public int getValor() {
        return this.valor;
    }

    public int getDistancia() {
        return this.distancia;
    }
}
