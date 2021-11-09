package agh.cs.task1;

import agh.cs.task2.Waiter;

import java.util.Random;

public class Guest extends Thread {

    private final Waiter waiter;
    private final int id;

    public Guest(Waiter waiter, int id){
        this.waiter = waiter;
        this.id = id;
    }

    @Override
    public void run() {
        doStuff();
        try {
            waiter.print("Client " + id + " is stating requesting");
            waiter.request(id);
            waiter.print("Client " + id + " is finishing requesting");
            eat();
            waiter.leave(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat() throws InterruptedException {
        waiter.print("Client " + id + " is starting eating");
        Random r = new Random();
        sleep(r.nextInt(500) + 100);
        waiter.print("Client " + id + " is finishing eating");
    }

    private void doStuff() {

    }
}
