package Task;

import AlmacenadorResultados.AlmacenadorResultados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MNISTask extends Task {

    private List<List<String>> lineas;
    private List<String> muestra;
    private AlmacenadorResultados almacenadorResultados;

    public MNISTask(List<List<String>> lineas, List<String> muestra, AlmacenadorResultados almacenadorResultados){
        this.lineas = lineas;
        this.muestra = muestra;
        this.almacenadorResultados = almacenadorResultados;
    }

    @Override
    public void run() {
        for (List<String> linea : lineas) {
            List<Double> resultados_linea = new ArrayList<Double>();
            int valor_linea = 0;
            for (int i = 0; i < 785; i++) {
                if (i == 0) {
                    valor_linea = Integer.parseInt(linea.get(i));
                } else {
                    Integer x = Integer.parseInt(linea.get(i));
                    Integer y = Integer.parseInt(this.muestra.get(i));
                    resultados_linea.add(Math.pow(x - y, 2));
                }
            }
            Double distancia_linea = 0.0;
            for (Double x : resultados_linea) {
                distancia_linea += x;
            }
            this.almacenadorResultados.agregarResultado(valor_linea,distancia_linea);
        }
    }

}
