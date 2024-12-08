package Workers;

public class WorkerCounter {
    private int counter;

    public WorkerCounter(int counter) {
        this.counter = counter;
    }

    synchronized public void decrementar(){
        this.counter--;
        if(this.counter == 0){
            notifyAll();
        }
    }

    synchronized public void incrementar(){
        this.counter++;
    }

    synchronized public void esperarWorkersTrabajando() {
        while(this.counter != 0) {
            try {wait();} catch (Exception e) { return; }
        }
    }
}
