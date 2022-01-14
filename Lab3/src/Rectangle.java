import java.awt.Point;
import java.awt.Color;
public class Rectangle implements Shape{
    private double width;
    private double height;
    private Point topLeftCorner;
    private Color color;
    public Rectangle ( double w, double h, Point topLeftCorner, Color color){
        this.width = w;
        this.height = h;
        this.topLeftCorner = topLeftCorner;
        this.color = color;
    }
    public double getWidth(){
        return width;
    }
    public void setWidth(double w){
        this.width = w;
    }
    public double getHeight(){
        return height;
    }
    public void setHeight(double h){
        this.height = h;
    }
    public Point getTopLeft() {
        return topLeftCorner;
    }
    public double getArea() {
        return width * height;
    }
    public double getPerimeter() {
        return 2 * (width + height);
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void translate(Point newP) {
        this.topLeftCorner = newP;
    }


}
