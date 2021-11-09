package agh.cs.task1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pair {
    public int id;
    public Lock lock = new ReentrantLock();
    public Condition calling = lock.newCondition();
    public Condition twoClientsReserving = lock.newCondition();
    public Pair(int id){
        this.id = id;
    }
    public Pair(){

    }

}
