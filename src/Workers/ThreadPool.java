package Workers;

import Buffer.Buffer;

public class ThreadPool {

    private Integer cantidadDeThreads;
    private Buffer buffer;
    private Worker[] workers;
    private WorkerCounter counter;

    public ThreadPool(Integer cantidadDeThreads, Buffer buffer, WorkerCounter counter) {
        this.cantidadDeThreads = cantidadDeThreads;
        this.buffer = buffer;
        this.counter = counter;
        this.workers = new Worker[cantidadDeThreads];
    }

    public void start() {
        /*
        No se startean los workers en este for, ya que al no estar todos creados aun se inicializarian secuencialmente
        lo que impactaría el aprovechamiento de la concurrencia
         */
        for(int i = 0; i < cantidadDeThreads; i++) {
            workers[i] = new Worker(buffer, counter);
        }
        for(Worker worker : this.workers) {
            worker.start();
        }
    }
}
