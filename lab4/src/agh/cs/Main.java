package agh.cs;

import agh.cs.task1.NewBuffer;
import agh.cs.task1.Worker;
import agh.cs.task2.Buffer;
import agh.cs.task2.Consumer;
import agh.cs.task2.Producent;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        task2();
    }

    private static void task1() {
        int PROCESSORS_NUMB = 2;
        //-1 0 1 2 3
        int N = 5;
        NewBuffer buffer = new NewBuffer(N);
        ArrayList<Worker> workers = new ArrayList<>();
        for (int i=0; i<PROCESSORS_NUMB+2; i++){
            Worker worker = new Worker(i-1,i,buffer);
            if (i==PROCESSORS_NUMB+1)
                worker.newValue = -1;
            workers.add(worker);
        }
        for(Worker worker: workers){
            worker.start();
        }
    }

    private static void task2(){
        Buffer buffer = new Buffer();

        int WORKERS_NUMB = 1;
        for(int i=0; i<WORKERS_NUMB; i++){
            new Producent(buffer).start();
            new Consumer(buffer).start();
        }
    }
}
