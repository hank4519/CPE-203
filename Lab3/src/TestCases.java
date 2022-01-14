import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.Color;
import java.awt.Point;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class TestCases
{
   public static final double DELTA = 0.00001;

   /* some sample tests but you must write more! see lab write up */

   @Test
   public void testCircleGetArea()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(101.2839543, c.getArea(), DELTA);
   }
   @Test
   public void testRectangleGetArea(){
      Rectangle r = new Rectangle(4.5, 9.13, new Point(-3,-4), Color.black);
      assertEquals(41.085, r.getArea(), DELTA);
   }
   @Test
   public void testTriangleGetArea(){
      Triangle t = new Triangle(new Point(2,3), new Point(-13,-14), new Point(6,-5), Color.green);
      assertEquals(94, t.getArea(), DELTA);
   }
   @Test
   //ConvexPolygon with three points/three sides
   public void testConvexPolygonGetArea3(){
      Point [] pts = {new Point(2,3), new Point(-13, -14), new Point(6, -5)};
      ConvexPolygon cp = new ConvexPolygon(pts, Color.MAGENTA);
      assertEquals(94, cp.getArea(), DELTA);
   }
   @Test
   //ConvexPolygon with four points/four sides
   public void testConvexPolygonGetArea4(){
      Point []pts = {new Point(4,9), new Point(26,7), new Point (-34,-2), new Point(1,87)};
      ConvexPolygon cp = new ConvexPolygon(pts, Color.BLUE);
      assertEquals(1657.5, cp.getArea(), DELTA);
   }
   @Test
   //ConvexPolygon with six points/six sides
   public void testConvexPolygonGetArea6(){
      Point []pts = {new Point(4,9), new Point(26,7), new Point (-34,-2), new Point(1,87),
              new Point (4,-28), new Point (-62, -72)};
      ConvexPolygon cp = new ConvexPolygon(pts,Color.CYAN);
      assertEquals(2823, cp.getArea(), DELTA);
   }

   @Test
   public void testCircleGetPerimeter()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(35.6759261, c.getPerimeter(), DELTA);
   }
   @Test
   public void testRectangleGetPerimeter()
   {
      Rectangle r = new Rectangle(2.5, 6.7, new Point(0,0), Color.BLACK);

      assertEquals(18.4, r.getPerimeter(), DELTA);
   }
   @Test
   public void testTriangleGetPerimeter(){
      Triangle t = new Triangle(new Point(2,5), new Point(3,-2), new Point(-13,-19), Color.GRAY);
      assertEquals(58.71824, t.getPerimeter(), DELTA);
   }
   @Test
   public void testConvexPolygonPerimeter(){
      Point []pts = {new Point(4,9), new Point(26,7), new Point (-34,-2), new Point(1,87)};
      ConvexPolygon cp = new ConvexPolygon(pts, Color.BLUE);
      assertEquals(256.45436, cp.getPerimeter(), DELTA);
   }
   @Test
   public void testConvexPolygonPerimeter2(){
      Point []pts = {new Point(4,9), new Point(26,7), new Point (-34,-2), new Point(1,87),
              new Point (4,-28), new Point (-62, -72)};
      ConvexPolygon cp = new ConvexPolygon(pts,Color.CYAN);
      assertEquals(477.24238, cp.getPerimeter(), DELTA);
   }

   @Test
   public void testCircleTranslate (){
      Circle c = new Circle(2.44, new Point(2,8), Color.BLUE);
      c.translate(new Point(13,14));
      assertEquals(new Point(13,14), c.getCenter());
   }
   @Test
   public void testRectangleTranslate(){
      Rectangle r = new Rectangle(2.5, 6.7, new Point(0,0), Color.BLACK);
      r.translate(new Point(3,4));
      assertEquals(new Point(3,4), r.getTopLeft());
   }
   @Test
   public void testTriangleTranslate(){
      Triangle t = new Triangle(new Point(2,3), new Point(-13,-14), new Point(6,-5), Color.green);
      t.translate(new Point(0, 0));
      assertEquals(new Point(0,0), t.getVertexA());
      assertEquals(new Point(-15, -17), t.getVertexB());
      assertEquals(new Point(4, -8), t.getVertexC());
   }
   @Test
   public void testConvexPolygonTranslate(){
      Point []pts = {new Point(4,9), new Point(26,7), new Point (-34,-2), new Point(1,87),
              new Point (4,-28), new Point (-62, -72)};
      ConvexPolygon cp = new ConvexPolygon(pts,Color.CYAN);
      cp.translate(new Point(0,0));
      assertEquals(new Point(0,0), cp.getVertex(0));
      assertEquals(new Point(-38, -11), cp.getVertex(2));
      assertEquals(new Point(-66, -81), cp.getVertex(5));
   }


   @Test
   public void testWorkSpaceAreaOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), 
                 Color.BLACK));

      assertEquals(114.2906063, ws.getAreaOfAllShapes(), DELTA);
   }

   @Test
   public void testWorkSpaceAreaOfAllShapesWithConvexPolygon()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      Point [] pts = {new Point(4,9), new Point(26,7), new Point (-34,-2), new Point(1,87),
              new Point (4,-28), new Point (-62, -72)};
      ws.add(new ConvexPolygon(pts, Color.green));
      assertEquals(2937.2906063, ws.getAreaOfAllShapes(), DELTA);
   }

   @Test
   public void testWorkSpaceGetCircles()
   {
      WorkSpace ws = new WorkSpace();
      List<Circle> expected = new LinkedList<>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(c1);
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
                 Color.BLACK));
      ws.add(c2);

      expected.add(c1);
      expected.add(c2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getCircles());
   }

   @Test
   public void testWorkSpaceGetRectangle(){
      WorkSpace ws = new WorkSpace();
      List <Rectangle> expected = new LinkedList<>();
      Rectangle r1= new Rectangle(1,2, new Point(1,2), Color.BLUE);
      Rectangle r2 = new Rectangle (3,4,new Point(3,4), Color.RED);
      ws.add(new Circle(3.3, new Point(0,0), Color.green));
      ws.add(r1);
      ws.add(new Triangle(new Point(0,0), new Point(1,2), new Point(2,3), Color.green));
      ws.add(r2);
      expected.add(r1);
      expected.add(r2);
      assertEquals(expected, ws.getRectangles());

   }

   @Test
   public void testWorkSpaceGetTriangle (){
      WorkSpace ws = new WorkSpace();
      List <Triangle> expected = new LinkedList<>();
      Triangle t1 = new Triangle( new Point(1,2), new Point(1,3),new Point(1,4), Color.MAGENTA);
      Triangle t2 = new Triangle (new Point (3,3), new Point(4,4), new Point(5,5), Color.BLACK);
      ws.add(new Circle(3.3, new Point(0,0), Color.green));
      ws.add(t1);
      ws.add(t2);
      Point []pts = {new Point(4,9), new Point(26,7), new Point (-34,-2), new Point(1,87)};
      ws.add(new ConvexPolygon(pts, Color.BLUE));
      expected.add(t1);
      expected.add(t2);
      assertEquals(expected, ws.getTriangles());
   }

   @Test
   public void testWorkSpaceGetConvexPolygon(){
      WorkSpace ws = new WorkSpace();
      List <ConvexPolygon> expected = new LinkedList<>();
      Point []pts = {new Point(4,9), new Point(26,7), new Point (-34,-2), new Point(1,87)};
      ConvexPolygon cp1 = new ConvexPolygon(pts, Color.BLUE);
      ws.add(cp1);
      Point []pts2 = {new Point(2,9), new Point(0,0), new Point (19,0), new Point(1,8)};
      ConvexPolygon cp2 = new ConvexPolygon(pts2, Color.CYAN);
      ws.add(cp2);
      ws.add(new Circle(4, new Point(0,0), Color.PINK));
      expected.add(cp1);
      expected.add(cp2);
      assertEquals(expected, ws.getConvexPolygons());
   }

   @Test
   public void testWorkSpaceColor(){
      WorkSpace ws = new WorkSpace();
      List <Shape> expected = new LinkedList<>();
      Triangle t = new Triangle (new Point(2,3), new Point(1,2), new Point(9,3), Color.black);
      ws.add(t);
      Circle c = new Circle(3, new Point(1,2), Color.green);
      ws.add(c);
      Rectangle r = new Rectangle (1,3, new Point(0,0), Color.black);
      ws.add(r);
      Point []pts = {new Point(4,9), new Point(26,7), new Point (-34,-2), new Point(1,87)};
      ConvexPolygon cp = new ConvexPolygon(pts, Color.green);
      ws.add(cp);
      expected.add(t);
      expected.add(r);
      assertEquals(expected, ws.getShapesByColor(Color.black));
      expected.clear();
      expected.add(c);
      expected.add(cp);
      assertEquals(expected, ws.getShapesByColor(Color.GREEN));


   }
   /* HINT - comment out implementation tests for the classes that you have not 
    * yet implemented */
   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getRadius", "setRadius", "getCenter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeft");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, double.class, void.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testTriangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertexA", "getVertexB", "getVertexC");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class, Point.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[0], new Class[0]);

      verifyImplSpecifics(Triangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testConvexPolygonImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertex");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[] {int.class});

      verifyImplSpecifics(ConvexPolygon.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, clazz.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
}
