package agh.cs;

import agh.cs.task1.Guest;
import agh.cs.task1.PrintThread;
import agh.cs.task1.PrinterMonitor;
import agh.cs.task2.Waiter;

public class Main {

    private static final int TASKS_NUMBER = 10;
    private static final int GUESTS_NUMBER = 10;

    public static void main(String[] args) {
        task2();
   }



    private static void task1() {
        PrinterMonitor monitor = new PrinterMonitor();
        for (int i=0; i<TASKS_NUMBER; i++){
            PrintThread thread = new PrintThread(monitor);
            thread.start();
        }
    }

    private static void task2() {
        Waiter waiter = new Waiter();
        for (int i=0; i<GUESTS_NUMBER; i++){
            Guest guest = new Guest(waiter,i);
            guest.start();
        }
    }

}
