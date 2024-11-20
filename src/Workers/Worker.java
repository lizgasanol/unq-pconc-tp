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
            System.out.println("FIN DEL WORKER");
            contador.decrementar();
            interrupt();
        }
    }

    private void resolveTask(Task task) {
        task.run();
    }
}