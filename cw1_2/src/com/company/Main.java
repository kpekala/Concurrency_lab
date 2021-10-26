package com.company;

public class Main {

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread decr = new MyThread(counter, false);
        Thread incr = new MyThread(counter, true);
        decr.start();
        incr.start();
        try {
            incr.join();
            decr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter.value);
    }
}
