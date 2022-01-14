import java.awt.Point;
import java.awt.Color;
public class ConvexPolygon implements  Shape{
    private Point [] pts;
    private Color color;
    public ConvexPolygon(Point [] pts, Color color ) {
        this.pts = pts;
        this.color = color;
    }
    public Point getVertex (int index){
        return pts[index];
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public double getPerimeter() {
        double perimeter = 0;
        for (int i = 0; i < pts.length - 1; i++ ){
            perimeter += pts[i].distance(pts[i+1]);
        }
        return perimeter + pts[pts.length -1].distance(pts[0]);
    }

    @Override
    public double getArea() {
        //Formula: 1/2 * [( x0y1 + x1y2 + ... + xny0) - (y0x1 + y1x2 + ... + ynx0)]
        double first_part = 0;
        double second_part = 0;
        for (int i = 0 ; i < pts.length - 1; i++ ){
            first_part += (pts[i].x * pts[i+1].y);
            second_part += (pts[i].y * pts[i+1].x);
        }
        first_part += (pts[pts.length-1].x * pts[0].y);
        second_part += (pts[pts.length-1].y * pts[0].x);
        return Math.abs(0.5 * (first_part - second_part));
    }

    @Override
    public void translate(Point newP) {
        int dx = newP.x - pts[0].x;
        int dy = newP.y - pts[0].y;
        pts[0] = newP;
        for(int i = 1; i <pts.length; i++ ){
            pts[i].x += dx;
            pts[i].y += dy;
        }
    }
}
