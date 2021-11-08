package agh.cs.task2;

import agh.cs.task1.Pair;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    public final int MAX_PAIRS = 10;
    private boolean[] clients_requesting = new boolean[MAX_PAIRS*2];
    private boolean[] clients_eating = new boolean[MAX_PAIRS*2];
    private Pair[] pairs = new Pair[MAX_PAIRS];
    private boolean tableOccupied = false;
    private Lock mainLock = new ReentrantLock();
    private Condition reserved = mainLock.newCondition();


    public Waiter(){
        for (int i=0; i<MAX_PAIRS; i++){
            pairs[i] = new Pair();
        }
    }


    public void request(int clientId) throws InterruptedException {
        int pairId = evenId(clientId)/2;
        clients_requesting[clientId] = true;
        mainLock.lock();
        if (clients_requesting[otherClientId(clientId)] )
        {
            pairs[pairId].lock.lock();
            System.out.println("Entering");
            pairs[pairId].occupying.signal();
            tableOccupied = true;
            pairs[pairId].lock.unlock();

        }
        mainLock.unlock();
        pairs[pairId].lock.lock();
        while ((!clients_requesting[otherClientId(clientId)])){
            pairs[pairId].occupying.await();
        }
        pairs[pairId].lock.unlock();
        //clients_requesting[clientId] = false;
        clients_eating[clientId] = true;
    }
    public void leave(int clientId){
        pairs[evenId(clientId)/2].lock.lock();
        clients_eating[clientId] = false;
        if(!clients_eating[otherClientId(clientId)] && tableOccupied)
            tableOccupied = false;
        pairs[evenId(clientId)/2].lock.unlock();

    }

    private int otherClientId(int clientId){
        return clientId %2 == 0 ? clientId+1: clientId-1;
    }
    private int evenId(int clientId){
        return clientId - (clientId %2);
    }
}
