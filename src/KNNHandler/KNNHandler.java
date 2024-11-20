package KNNHandler;

import AlmacenadorResultados.AlmacenadorResultados;

import java.util.*;

public class KNNHandler {

    private PriorityQueue<Integer> resultados;

    public KNNHandler(AlmacenadorResultados almacenadorResultados) {
        this.resultados = new PriorityQueue<>(Comparator.comparingDouble(almacenadorResultados.getResultados()::get));
        resultados.addAll(almacenadorResultados.getResultados().keySet());
    }

    public List<Integer> getKNN(int k) {
        List<Integer> knn = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            knn.add(this.resultados.poll());
        }
        return knn;
    }
}
