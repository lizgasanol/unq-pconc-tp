package Resultados;

import java.util.Comparator;

public class ResultadoComparator implements Comparator<Resultado> {

    @Override
    public int compare(Resultado o1, Resultado o2) {
        if (o1.getDistancia() > o2.getDistancia()) {
            return 1;
        } else if (o1.getDistancia() < o2.getDistancia()) {
            return -1;
        } else {
            return 0;
        }
    }
}
