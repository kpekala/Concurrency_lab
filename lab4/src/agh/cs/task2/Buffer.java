package agh.cs.task2;

public class Buffer {
    int value = 0;
    public static final int M = 5;
    private final int N = 2*M;
    private int addedNumb = 0;
    private int poppedNumb = 0;

    public synchronized void add(int amount) throws InterruptedException {
        while (value + amount > N){
            wait();
        }
        value += amount;
        addedNumb++;
        notifyAll();
        System.out.println("Added. Value: " + value);
        System.out.println("Added " + addedNumb + " times");
    }

    public synchronized void pop(int amount) throws InterruptedException {
        while (value - amount < 0){
            wait();
        }
        value -= amount;
        poppedNumb++;
        notifyAll();
        System.out.println("Popped. Value: " + value);
        System.out.println("Popped " + poppedNumb + " times");
    }
}
