package agh.cs.task1;

public class Worker extends Thread{
    public final int oldValue;
    private final NewBuffer buffer;
    private final Type type = null;
    public int newValue;

    public enum Type{
        BEFORE, AFTER, PROCESSOR
    }

    public Worker(int oldValue, int newValue, NewBuffer buffer){
        //this.type = type;
        this.oldValue = oldValue;
        this.buffer = buffer;
        this.newValue = newValue;
        System.out.println(oldValue + " " + newValue);
    }


    @Override
    public void run() {
        while (true){
            for (int i=0; i<buffer.size(); i++){
                try {
                    buffer.update(i,oldValue,newValue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public synchronized void wakeUp(){
        notifyAll();
    }
}
