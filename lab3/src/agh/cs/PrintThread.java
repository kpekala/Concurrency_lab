package agh.cs;

import java.util.Random;

public class PrintThread extends Thread{
    private final PrinterMonitor monitor;

    public PrintThread(PrinterMonitor monitor) {
        this.monitor = monitor;
    }

    public void run(){
        prepareTask();
        int printerId = -1;
        try {
            printerId = monitor.reserve();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
            System.exit(1);
        }
        print(printerId);
        monitor.release(printerId);

    }
    private void print(int printerId) {
        Random r = new Random();
        try {
            System.out.println("Printer " + Integer.toString(printerId) + " is starting a task");
            sleep(r.nextInt(500) + 100);
            System.out.println("Printer " + Integer.toString(printerId) + " is finishing a task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void prepareTask(){

    }
}
