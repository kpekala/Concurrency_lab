package com.company;

import static com.company.Consumer.ILOSC;

public class Producer extends Thread{
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < ILOSC;   i++) {
            buffer.put("message "+i);
        }

    }
}
