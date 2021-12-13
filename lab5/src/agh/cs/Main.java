package agh.cs;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Main extends JFrame {

    private static final int threadsNumber = 4;
    private static final int tasksNumber = 4;
    public static int SCREEN_WIDTH = 1400;
    public static int SCREEN_HEIGHT = 600;
    private BufferedImage image;

    public Main() throws ExecutionException, InterruptedException {
        prepareView();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Main main = new Main();
        main.setVisible(true);
        main.pixelTask();
        //main.startSimulation();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    private void startSimulation() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);
        Set<Future<ArrayList<ValuedPoint>>> tasksSet = new HashSet<>();

        Mandelbrot mandelbrot = new Mandelbrot(SCREEN_WIDTH, SCREEN_HEIGHT);

        int rowsPerTask = SCREEN_HEIGHT / tasksNumber;
        long st = System.currentTimeMillis();
        for (int i = 0; i < tasksNumber; i++) {
            int yStart = i*rowsPerTask;
            Future<ArrayList<ValuedPoint>> future = executor.submit(
                    new MandelbrotValue(mandelbrot,yStart,rowsPerTask));
            tasksSet.add(future);
        }
        for (Future<ArrayList<ValuedPoint>> future : tasksSet) {
            List<ValuedPoint> points = future.get();
            for(ValuedPoint point : points){
                image.setRGB(point.x,point.y,point.value);
            }
            repaint();
        }
        long et = System.currentTimeMillis();
        System.out.println("Total execution time: " + (et - st));
    }

    private void pixelTask() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);
        Set<Future<ValuedPoint>> tasksSet = new HashSet<>();

        Mandelbrot mandelbrot = new Mandelbrot(SCREEN_WIDTH, SCREEN_HEIGHT);
        long st = System.currentTimeMillis();

        for(int x=0; x<SCREEN_WIDTH; x++){
            for(int y=0; y<SCREEN_HEIGHT; y++){
                Future<ValuedPoint> future = executor.submit(new SinglePointValue(mandelbrot,x,y));
                tasksSet.add(future);
            }
        }
        for (Future<ValuedPoint> future : tasksSet) {
            ValuedPoint point = future.get();
            image.setRGB(point.x,point.y,point.value);
            repaint();
        }
        long et = System.currentTimeMillis();
        System.out.println("Total execution time: " + (et - st));
    }

    private void prepareView() {
        setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
    }
}
