package AlmacenadorResultados;

import java.util.HashMap;
import java.util.Map;

public class AlmacenadorResultados {

    private Map<Integer,Integer> resultados;

    public AlmacenadorResultados() {
        this.resultados = new HashMap<>();
    }

    public void agregarResultado(Integer valor, Integer distancia) {
        this.resultados.put(valor,distancia);
    }

    synchronized public Map<Integer,Integer> getResultados() {
        while(hayWorkersLaburando) {
            try {wait();} catch (Exception e) { return resultados; }
        }
        return this.resultados;
    }

}
