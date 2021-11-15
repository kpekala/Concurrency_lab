package agh.cs.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Buffer {
    private final int max_val;
    private int[] array;
    private Condition[] conditions;

    //0 - proc(-1 ->0)
    //(n-1) - cons(n-1)

    private Lock[] locks;
    private Lock mainLock = new ReentrantLock();
    private Condition cond = mainLock.newCondition();


    public Buffer(int n, int proc_numb){
        array = new int[n];
        for(int i=0; i<n; i++){
            array[i] = -1;
        }
        max_val = proc_numb;
        conditions = new Condition[proc_numb+2];
        locks = new Lock[proc_numb+2];
        initConditions();
    }

    private void initConditions() {
        for(int i=0; i<conditions.length; i++){
            locks[i] = new ReentrantLock();
            conditions[i] = locks[i].newCondition();
        }
    }

    public int size(){return array.length;}

    private boolean fullBuffer(){
        return IntStream.of(array).allMatch(value -> value==max_val);
    }

    public void update(Changer changer, int pos) throws InterruptedException {
        int changerId = changerId(changer.from);
        locks[changerId].lock();
        while (changer.to == -1 && !fullBuffer()){
            conditions[changerId].await();
        }
        System.out.println(changer.toString() + "is trying to update, pos = " + pos);
        System.out.println("Before: " + Arrays.toString(array) + changer);
        while (array[pos] != changer.from) {
            conditions[changerId].await();
        }
        array[pos] = changer.to;
        int prevId = (changerId+ conditions.length+1)%conditions.length;
        wakeAll();
        wakeUp(prevId);
        wakeUp(max_val-1);
        System.out.println(Arrays.toString(array));
        locks[changerId].unlock();

    }

    private void wakeAll() {
        for(int i=0; i<locks.length; i++){
            wakeUp(i);
        }
    }

    private void wakeUp(int id){
        locks[id].lock();
        conditions[id].signal();
        locks[id].unlock();
    }
    private int changerId(int from){
        return from+1;
    }
}
