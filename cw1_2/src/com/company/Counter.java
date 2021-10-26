package com.company;

public class Counter {
    public int value;
    public synchronized void increment(){
        value++;
    }
    public synchronized void decrement(){
        value--;
    }
}
