package Task;


public class PoisonPill extends Task{

    public void run(){

        throw new RuntimeException();
    }
}