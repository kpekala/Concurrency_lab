package agh.cs;

public class Mandelbrot {
    private static final int MAX_ITER = 570;
    private static final double ZOOM = 150;
    private final int width;
    private final int height;

    public Mandelbrot(int width, int height){
        this.width = width;
        this.height = height;
    }

    public  int getValue(int x, int y){
        double zx,zy,cX,cY,tmp;
        zx = zy = 0;
        cX = (x - width/2) / ZOOM;
        cY = (y - height/2) / ZOOM;
        int iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return iter | (iter << 8);
    }
}
