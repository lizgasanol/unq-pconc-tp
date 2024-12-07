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
    synchronized public void hayWorkersTrabajando() throws InterruptedException {
        while(this.counter != 0) {
            wait();
        }
    }
}
