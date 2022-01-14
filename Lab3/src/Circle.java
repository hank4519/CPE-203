import java.awt.Color;
import java.awt.Point;
public class Circle implements Shape{
    private double radius;
    private Color color;
    private  Point center;
    public Circle (double radius, Point center, Color color ){
        this.radius = radius;
        this.color = color;
        this.center = center;
    }
    public double getRadius (){
        return this.radius;
    }
    public void setRadius (double r){
        this.radius = r;
    }
    public Point getCenter(){
        return this.center;
    }
    public Color getColor(){
        return color;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public double getArea(){
        return Math.PI * Math.pow(this.radius, 2);
    }
    public double getPerimeter() {
        return 2 * Math.PI * this.radius;
    }

    @Override
    public void translate(Point newP) {
        this.center = newP;
    }
}
