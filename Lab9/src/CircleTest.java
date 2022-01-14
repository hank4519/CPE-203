public class CircleTest {
    public static void main(String[] args)
    {
        //Circle c2 = new Circle(0);
        try
        {
            Circle c1 = new Circle(-7);
            System.out.println(c1);
            System.out.println("HANKHANK");
        }
//        catch (CircleException e) {
//            System.out.println(e.getMessage());
//            return;
//        }
        catch(ZeroRadiusException e){
            System.out.println(e.getMessage());
        }
        catch(NegativeRadiusException e){
            System.out.println(e.getMessage() + ": " + e.radius());
            return;
        }
        finally {
            System.out.println("In finally.");
        }
        System.out.println("Done.");
    }
}
