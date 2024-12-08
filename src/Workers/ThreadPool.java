package Workers;

import Buffer.Buffer;

public class ThreadPool {

    private Integer cantidadDeThreads;
    private Buffer buffer;
    private WorkerCounter counter;

    public ThreadPool(Integer cantidadDeThreads, Buffer buffer, WorkerCounter counter) {
        this.cantidadDeThreads = cantidadDeThreads;
        this.buffer = buffer;
        this.counter = counter;
    }

    public void start() {
        for(int i = 0; i < cantidadDeThreads; i++) {
            Worker newWorker = new Worker(buffer, counter);
            counter.incrementar();
            newWorker.start();
        }
    }
}
