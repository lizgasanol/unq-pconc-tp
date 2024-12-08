package KNNHandler;

import Resultados.Resultado;
import Resultados.ResultadoComparator;

import java.util.*;

public class KNNHandler {

    private final int k;

    public KNNHandler(int k) {
        this.k = k;
    }

    public List<Resultado> getKNN(List<Resultado> distancias) {
        PriorityQueue<Resultado> resultados = new PriorityQueue<>(new ResultadoComparator());
        resultados.addAll(distancias);

        List<Resultado> knn = new ArrayList<>();
        for (int i = 0; i < this.k && !resultados.isEmpty(); i++) {
            knn.add(resultados.poll());
        }
        return knn;
    }
}
