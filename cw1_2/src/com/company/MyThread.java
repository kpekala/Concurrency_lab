package com.company;

public class MyThread extends Thread {
    private final int TIMES = 100000;
    private boolean increment = false;
    private Counter counter;
    public MyThread(Counter counter, boolean increment){
        this.counter = counter;
        this.increment = increment;
    }
    public void run(){
        for(int i=0; i<TIMES; i++){
            if(this.increment)
                counter.increment();
            else
                counter.decrement();
        }
    }
}
