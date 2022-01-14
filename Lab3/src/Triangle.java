import java.awt.Color;
import java.awt.Point;
public class Triangle implements  Shape{
    private Point vertexA;
    private Point vertexB;
    private Point vertexC;
    private Color color;
    public Triangle(Point v1, Point v2, Point v3, Color color){
        this.vertexA = v1;
        this.vertexB = v2;
        this.vertexC = v3;
        this.color = color;
    }
    public Point getVertexA (){
        return vertexA;
    }
    public Point getVertexB (){
        return vertexB;
    }
    public Point getVertexC(){
        return vertexC;
    }

    @Override
    public double getPerimeter() {
        return vertexA.distance(vertexB) + vertexB.distance(vertexC) + vertexC.distance(vertexA);
    }
    public Color getColor (){
        return color;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public double getArea() {
        return Math.abs(0.5 * (vertexA.x * (vertexB.y - vertexC.y) + vertexB.x * (vertexC.y - vertexA.y) + vertexC.x * (vertexA.y-vertexB.y)));
    }
    public void translate(Point newP) {
        int dx = newP.x - vertexA.x;
        int dy = newP.y - vertexA.y;
        vertexA = newP;
        vertexB.x += dx;
        vertexB.y += dy;
        vertexC.x += dx;
        vertexC.y += dy;
    }
}
