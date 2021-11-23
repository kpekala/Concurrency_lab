package agh.cs;

import java.util.concurrent.Callable;

public class SinglePointValue implements Callable<ValuedPoint> {

    private final Mandelbrot mandelbrot;
    private final int x;
    private final int y;

    public SinglePointValue(Mandelbrot mandelbrot, int x, int y){

        this.mandelbrot = mandelbrot;
        this.x = x;
        this.y = y;
    }

    @Override
    public ValuedPoint call() throws Exception {
        return new ValuedPoint(x,y,mandelbrot.getValue(x,y));
    }
}
