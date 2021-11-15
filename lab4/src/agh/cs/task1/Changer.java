package agh.cs.task1;

public class Changer extends Thread {
    public final int from;
    public final int to;
    private final Buffer buffer;

    public Changer(int from, int to, Buffer buffer){
        this.from = from;
        this.to = to;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true){
            for (int i=0; i<buffer.size(); i++){
                try {
                    buffer.update(this, i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Changer{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
