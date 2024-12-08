package Resultados;

import Workers.WorkerCounter;

import java.util.ArrayList;
import java.util.List;

public class AlmacenadorResultados {

    private List<Resultado> resultados;
    private WorkerCounter counter;

    public AlmacenadorResultados(WorkerCounter counter) {
        this.resultados = new ArrayList<>();
        this.counter = counter;
    }

    public void agregarResultados(List<Resultado> resultados) {
        this.resultados.addAll(resultados);
    }

    synchronized public List<Resultado> getResultados() {
        counter.esperarWorkersTrabajando();
        return this.resultados;
    }
    public void vaciar() {
        this.resultados.clear();
    }

}
