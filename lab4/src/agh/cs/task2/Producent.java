package agh.cs.task2;

import java.util.Random;

public class Producent extends Thread{

    private final Buffer buffer;

    public Producent(Buffer buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int n = 10;
        Random r = new Random();
        for(int i=0; i<n; i++){
            try {
                buffer.add(r.nextInt(Buffer.M+1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
