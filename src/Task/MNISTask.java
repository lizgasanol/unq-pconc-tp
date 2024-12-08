package Task;

import Resultados.AlmacenadorResultados;
import KNNHandler.KNNHandler;
import Resultados.Resultado;

import java.util.ArrayList;
import java.util.List;

public class MNISTask extends Task {

    private List<List<String>> lineas;
    private List<String> muestra;
    private List<Resultado> resultadosTask = new ArrayList<>();
    private AlmacenadorResultados almacenadorResultados;
    private KNNHandler knnHandler;

    public MNISTask(List<List<String>> lineas, List<String> muestra, AlmacenadorResultados almacenadorResultados, KNNHandler knnHandler){
        this.lineas = lineas;
        this.muestra = muestra;
        this.almacenadorResultados = almacenadorResultados;
        this.knnHandler = knnHandler;
    }

    @Override
    public void run() {
        for (List<String> linea : lineas) {
            List<Integer> resultados_linea = new ArrayList<>();
            int valor_linea = 0;
            for (int i = 0; i < 785; i++) {
                if (i == 0) {
                    valor_linea = Integer.parseInt(linea.get(i));
                } else {
                    Integer x = Integer.parseInt(linea.get(i));
                    Integer y = Integer.parseInt(this.muestra.get(i));
                    resultados_linea.add((x - y) * (x - y));
                }
            }
            Integer distancia_linea = 0;
            for (Integer x : resultados_linea) {
                distancia_linea += x;
            }
            this.resultadosTask.add(new Resultado(valor_linea,distancia_linea));
        }
        almacenadorResultados.agregarResultados(knnHandler.getKNN(this.resultadosTask));
    }

}
