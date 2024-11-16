package Buffer;

public class Buffer {

    private final Integer tamano_buffer;
    private Object[] current_buffer;
    private int begin = 0, end = 0;

    public Buffer(Integer tamano_buffer) {
        this.tamano_buffer = tamano_buffer;
        this.current_buffer = new Object[tamano_buffer+1];
    }

    synchronized public void write(Object o) {
        while (isFull()) try {wait();} catch (Exception e) { return; }
        this.current_buffer[this.begin] = o;
        this.begin = next(this.begin);
        notifyAll();
    }

    synchronized public Object read() {
        while (isEmpty()) try { wait();} catch (Exception e) { return null; }
        Object result = this.current_buffer[this.end];
        this.end = next(this.end);
        notifyAll();
        return result;
    }

    private boolean isEmpty() { return this.begin == this.end; }
    private boolean isFull() { return next(this.begin) == this.end; }
    private int next(int i) { return (i +1) %( this.tamano_buffer +1); }
}
