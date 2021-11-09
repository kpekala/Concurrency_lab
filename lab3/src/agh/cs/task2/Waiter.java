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

    private int currentId = -1;

    public Waiter(){
        for (int i=0; i<MAX_PAIRS; i++){
            pairs[i] = new Pair(i);
        }
    }

    public void request(int clientId) throws InterruptedException {
        int otherId = otherClientId(clientId);
        Pair pair = getPair(clientId);
        clients_requesting[clientId] = true;
        pair.lock.lock();
        if (clients_requesting[otherId])
            pair.twoClientsReserving.signalAll();
        pair.lock.unlock();
        while (!clients_requesting[otherId]){
            pair.lock.lock();
            pair.twoClientsReserving.await();
            pair.lock.unlock();
        }
        if (clientId == evenId(clientId)){
            //code for main guest (both guest are reserving)
            mainLock.lock();
            if (!tableOccupied){
                changeTable(clientId);
                mainLock.unlock();
            }else{
                while (tableOccupied){
                    reserved.await();
                    if (!tableOccupied) {
                        changeTable(clientId);
                        mainLock.unlock();
                        break;
                    }
                }
            }

        }else{
            //code for second guest
            //second guest is waiting for a call from main guest to take a table
            while (currentId != pair.id){
                print(currentId + ": " + pair.id);
                pair.lock.lock();
                pair.calling.await();
                pair.lock.unlock();
                if(currentId == pair.id){
                    break;
                }
            }
        }
        clients_eating[clientId] = true;

    }

    private void changeTable(int clientId){
        Pair pair = getPair(clientId);
        tableOccupied = true;
        currentId = pair.id;
        pair.lock.lock();
        pair.calling.signalAll();
        pair.lock.unlock();
    }

    public void leave(int clientId){
        int otherId = otherClientId(clientId);
        Pair pair = getPair(clientId);
        pair.lock.lock();
        clients_eating[clientId] = false;
        if (!clients_eating[otherId]){
            tableOccupied = false;
            mainLock.lock();
            reserved.signalAll();
            mainLock.unlock();
        }
        pair.lock.unlock();

    }
    private Pair getPair(int clientId){
        int pairId = evenId(clientId)/2;
        return pairs[pairId];
    }
    private int otherClientId(int clientId){
        return clientId %2 == 0 ? clientId+1: clientId-1;
    }
    private int evenId(int clientId){
        return clientId - (clientId %2);
    }

    public synchronized void print(String message){
        System.out.println(message + ", time: " + System.nanoTime());
    }
}
