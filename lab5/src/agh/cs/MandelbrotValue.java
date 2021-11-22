package agh.cs;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class MandelbrotValue implements Callable<ArrayList<ValuedPoint>> {
    private final Mandelbrot mandelbrot;
    private final int yStart;
    private final int rowsNumb;


    public MandelbrotValue(Mandelbrot mandelbrot, int yStart, int rowsNumb){
        this.mandelbrot = mandelbrot;
        this.yStart = yStart;
        this.rowsNumb = rowsNumb;
    }

    @Override
    public ArrayList<ValuedPoint> call() throws Exception {
        ArrayList<ValuedPoint> points = new ArrayList<>();
        for(int y=yStart; y<yStart+rowsNumb; y++){
            for(int x=0; x<Main.SCREEN_WIDTH; x++){
                points.add(new ValuedPoint(x,y,mandelbrot.getValue(x,y)));
            }
        }
        return points;
    }
}
