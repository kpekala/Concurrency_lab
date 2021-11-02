package agh.cs;

public class Main {

    private static final int TASKS_NUMBER = 10;

    public static void main(String[] args) {
        task1();
   }



    private static void task1() {
        PrinterMonitor monitor = new PrinterMonitor();
        for (int i=0; i<TASKS_NUMBER; i++){
            PrintThread thread = new PrintThread(monitor);
            thread.start();
        }
    }

}
