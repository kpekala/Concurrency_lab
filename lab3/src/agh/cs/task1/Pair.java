package agh.cs.task1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pair {
    private int evenId;
    public Lock lock = new ReentrantLock();
    public Condition occupying = lock.newCondition();
    public Pair(int evenId){
        //this.evenId = evenId;
    }
    public Pair(){

    }

}
