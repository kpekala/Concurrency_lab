package agh.cs.task1;

import java.util.ArrayList;
import java.util.Arrays;

public class NewBuffer {

    private final int[] array;

    public NewBuffer(int n){
        array = new int[n];
        for(int i=0; i<n; i++){
            array[i] = -1;
        }
    }

    public synchronized void update(int index, int oldValue, int value) throws InterruptedException {
        while (array[index] != oldValue){
            wait();
        }
        //System.out.println("Updating index " + index + " from " + array[index] + " to " + value);
        array[index] = value;
        System.out.println(Arrays.toString(array));
        notifyAll();
    }

    public synchronized int getValue(int index){
        //System.out.println(Arrays.toString(array));
        return array[index];
    }
    public int size(){
        return array.length;
    }
}
