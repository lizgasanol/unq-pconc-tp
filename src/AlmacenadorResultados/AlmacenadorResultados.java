package AlmacenadorResultados;

import java.util.HashMap;
import java.util.Map;

public class AlmacenadorResultados {

    private Map<Integer,Double> resultados;

    public AlmacenadorResultados() {
        this.resultados = new HashMap<>();
    }

    public void agregarResultado(Integer valor, Double distancia) {
        this.resultados.put(valor,distancia);
    }

    synchronized public Map<Integer,Double> getResultados() {
        while(hayWorkersLaburando) {
            try {wait();} catch (Exception e) { return resultados; }
        }
        return this.resultados;
    }

}
