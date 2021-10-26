package com.company;


public class Consumer extends Thread {

    public static int ILOSC = 4;

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < ILOSC;   i++) {
            String message = buffer.take();
            System.out.println(message);
        }

    }
}
