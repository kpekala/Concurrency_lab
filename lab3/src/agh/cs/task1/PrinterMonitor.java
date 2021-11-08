package agh.cs.task1;

import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {
    private final int MAX_PRINTERS = 2;
    private int count = 0;

    final  Lock lock = new ReentrantLock();
    final  Condition notFull  = lock.newCondition();
    final  Condition notEmpty = lock.newCondition();
    private final boolean[] reserved = new boolean[MAX_PRINTERS];
    public  int reserve() throws InterruptedException {
        lock.lock();
        int newId = -1;
        try {
            while (count >= MAX_PRINTERS)
                notFull.await();
            for(int i=0; i<MAX_PRINTERS; i++){
                if(!reserved[i]){
                    newId = i;
                    break;
                }
            }
            reserved[newId] = true;
            count++;
        } finally {
            lock.unlock();
        }
        return newId;
    }
    public void release(int printerId){
        lock.lock();
        if(!reserved[printerId]){
            throw new Error("printer not reserved");
        }
        reserved[printerId] = false;
        count--;
        if(count == MAX_PRINTERS - 1)
            notFull.signal();
        lock.unlock();
    }
}
