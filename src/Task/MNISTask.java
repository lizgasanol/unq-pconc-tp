package Task;

import java.util.ArrayList;

public class MNISTask extends Task {

    private ArrayList<ArrayList<String>> lineas;
    private ArrayList<String> muestra;
    private ArrayList<Double> distancias;

    public MNISTask(ArrayList<ArrayList<String>> lineas, ArrayList<String> muestra){
        this.lineas = lineas;
        this.muestra = muestra;
    }

    @Override
    public void run() {
        for (ArrayList<String> linea : lineas) {
            ArrayList<Double> resultados_linea = new ArrayList<Double>();
            for (int i = 0; i < 785; i++) {
                if (i == 0) {
                    continue;
                }
                Integer x = Integer.parseInt(linea.get(i));
                Integer y = Integer.parseInt(this.muestra.get(i));
                resultados_linea.add(Math.pow(x - y, 2));
            }
            Double distancia_linea = 0.0;
            for (Double i : resultados_linea) {
                distancia_linea += i;
            }
            this.distancias.add(distancia_linea);
        }
    }

}
