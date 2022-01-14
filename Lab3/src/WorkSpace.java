import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
public class WorkSpace {
    //use instanceof
    private List<Shape> list;
    public WorkSpace(){
        list = new ArrayList<>();
    }
    public void add(Shape shape){
        list.add(shape);
    }
    public Shape get (int index) {
        return list.get(index);
    }
    public int size(){
        return list.size();
    }
    public List<Circle> getCircles(){
        List<Circle> circles = new ArrayList<>();
        for( Shape shape: list){
            if (shape instanceof Circle){
                circles.add((Circle)shape);
            }
        }
        return circles;
    }
    public List<Rectangle> getRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (Shape shape: list){
            if (shape instanceof Rectangle) {
                rectangles.add((Rectangle) shape);
            }
        }
        return rectangles;
    }

    public List<Triangle> getTriangles(){
        List<Triangle> triangles = new ArrayList<>();
        for (Shape shape: list){
            if (shape instanceof Triangle) {
                triangles.add((Triangle) shape);
            }
        }
        return triangles;
    }

    public List<ConvexPolygon> getConvexPolygons(){
        List <ConvexPolygon> convexPolygons = new ArrayList<>();
        for (Shape shape: list){
            if (shape instanceof ConvexPolygon){
                convexPolygons.add((ConvexPolygon) shape);
            }
        }
        return convexPolygons;
    }
    public List<Shape> getShapesByColor(Color color){
        List <Shape> list = new ArrayList<>();
        for (Shape shape: this.list ){
            if (shape.getColor().equals(color)){
                list.add(shape);
            }
        }
        return list;
    }
    public double getAreaOfAllShapes(){
        double total_area = 0;
        for (Shape shape: list){
            total_area += shape.getArea();
        }
        return total_area;
    }
    public double getPerimeterOfAllShapes(){
        double total_perimeter = 0;
        for (Shape shape: list){
            total_perimeter += shape.getPerimeter();
        }
        return total_perimeter;
    }


}
