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

    private static final int threadsNumber = 5;
    private static final int tasksNumber = 100;
    public static int SCREEN_WIDTH = 800;
    public static int SCREEN_HEIGHT = 600;
    private BufferedImage image;

    public Main() throws ExecutionException, InterruptedException {
        prepareView();
        startSimulation();
        //pixelTask();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Main().setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    private void startSimulation() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);
        Set<Future<ArrayList<ValuedPoint>>> tasksSet = new HashSet<Future<ArrayList<ValuedPoint>>>();

        Mandelbrot mandelbrot = new Mandelbrot(SCREEN_WIDTH, SCREEN_HEIGHT);

        int rowsPerTask = SCREEN_HEIGHT / tasksNumber;
        for (int i = 0; i < tasksNumber; i++) {
            int yStart = i*rowsPerTask;
            Future<ArrayList<ValuedPoint>> future = executor.submit(
                    new MandelbrotValue(mandelbrot,yStart,rowsPerTask));
            tasksSet.add(future);
        }
        for (Future<ArrayList<ValuedPoint>> future : tasksSet) {
            List<ValuedPoint> points = future.get();
            for(ValuedPoint point : points){
                System.out.println(point.value);
                image.setRGB(point.x,point.y,point.value);
            }
        }
    }

    private void pixelTask() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);
        Set<Future<ValuedPoint>> tasksSet = new HashSet<>();

        Mandelbrot mandelbrot = new Mandelbrot(SCREEN_WIDTH, SCREEN_HEIGHT);

        for(int x=0; x<SCREEN_WIDTH; x++){
            for(int y=0; y<SCREEN_HEIGHT; y++){
                Future<ValuedPoint> future = executor.submit(new SinglePointValue(mandelbrot,x,y));
                tasksSet.add(future);
            }
        }
        for (Future<ValuedPoint> future : tasksSet) {
            ValuedPoint point = future.get();
            image.setRGB(point.x,point.y,point.value);
        }
    }

    private void prepareView() {
        setBounds(100, 100, SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
    }
}
