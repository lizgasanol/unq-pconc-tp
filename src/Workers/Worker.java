package Workers;

import Buffer.Buffer;
import Task.Task;

public class Worker extends Thread {
    private final Buffer buffer;
    private WorkerCounter contador;

    public Worker(Buffer buffer, WorkerCounter contador) {
        this.buffer = buffer;
        this.contador = contador;
    }

    public void run() {
        try {
            Task task;
            while (true) {
                task = (Task) buffer.read();
                this.resolveTask(task);
            }
        } catch (Exception ex) {
            contador.decrementar();
        }
    }

    private void resolveTask(Task task) {
        task.run();
    }
}