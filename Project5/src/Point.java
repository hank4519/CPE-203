import java.nio.channels.WritePendingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

final class Point {
   private int x;
   private int y;

   public Point(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public boolean adjacent(Point other) {
      return (this.x == other.x && Math.abs(this.y - other.y) == 1) ||
              (this.y == other.y && Math.abs(this.x - other.x) == 1);
   }

   public String toString() {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other) {
      return other instanceof Point &&
              ((Point) other).x == this.x &&
              ((Point) other).y == this.y;
   }

   public int hashCode() {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   //WorldModel
   public int distanceSquared(Point other) {
      int deltaX = this.x - other.x;
      int deltaY = this.y - other.y;

      return deltaX * deltaX + deltaY * deltaY;
   }

   public boolean neighbor(Point p2) {
      return this.x + 1 == p2.x && this.y == p2.y ||
              this.x - 1 == p2.x && this.y == p2.y ||
              this.x == p2.x && this.y + 1 == p2.y ||
              this.x == p2.x && this.y - 1 == p2.y ||
              this.x + 1 == p2.x && this.y + 1 == p2.y ||
              this.x + 1 == p2.x && this.y - 1 == p2.y ||
              this.x - 1 == p2.x && this.y - 1 == p2.y ||
              this.x - 1 == p2.x && this.y + 1 == p2.y;
   }

   public Point getRandomNeighbor(WorldModel world) {
      Point res = null;
      HashSet<Point> set = new HashSet<>();
      Random rand = new Random();
      int r = rand.nextInt(8);
      while (res == null || set.size() != 8) {
         Point potential = getPoint(r);
         if (!world.getOccupant(potential).isPresent() && world.withinBounds(potential)) {
            res = potential;
            break;
         }
         set.add(potential);
         r = rand.nextInt(8);
      }
      return res;
   }
   public Point getRandomNeighbor(WorldModel world, HashSet<Point> set) {

      Point res = null;
      Random rand = new Random();
      Map<Integer, Point> dict = getDict();
      int r = rand.nextInt(dict.size());
      int control=0;
      while (control <8 && dict.size()!=0) {
         System.out.println("LOOP ");
         Point potential = dict.get(r);
         dict.remove(r);
         if (!world.getOccupant(potential).isPresent() && world.withinBounds(potential) && !set.contains(potential)) {
            res = potential;
            break;
         }
         r = rand.nextInt(dict.size());
         control++;
      }
      return res;
   }
   public Point getPoint(int map){
      if(map == 0) return new Point(this.x-1, this.y-1);
      else if(map ==1) return new Point(this.x, this.y-1);
      else if(map==2) return new Point(this.x+1, this.y-1);
      else if(map==3) return new Point(this.x-1, this.y);
      else if(map ==4) return new Point(this.x+1, this.y);
      else if(map==5) return new Point(this.x-1, this.y+1);
      else if(map ==6) return new Point(this.x, this.y-1);
      else return new Point(this.x+1, this.y+1);
   }
   public Map<Integer, Point> getDict (){
      HashMap<Integer, Point> dict = new HashMap<>();
      dict.put(0, new Point(this.x-1, this.y-1));
      dict.put(1, new Point(this.x, this.y-1));
      dict.put(2, new Point(this.x+1, this.y-1));
      dict.put(3, new Point(this.x-1, this.y));
      dict.put(4, new Point(this.x+1, this.y));
      dict.put(5, new Point(this.x-1, this.y+1));
      dict.put(6, new Point(this.x, this.y-1));
      dict.put(7, new Point(this.x+1, this.y+1));
      return dict;
   }
   public Point translate(int dx, int dy){
      return new Point(this.x + dx, this.y + dy);
   }
   public void setX(int x){
      this.x = x;
   }
   public void setY(int y){
      this.y = y;
   }
   public boolean notFarAway (Point other){
      double dis = Math.sqrt(Math.pow(this.x-other.x,2)+Math.pow(this.y-other.y, 2));
      if(dis <= 2) return true;
      return false;
   }
   public boolean isFarAway(Point other){
      double dis = Math.sqrt(Math.pow(this.x-other.x,2)+Math.pow(this.y-other.y, 2));
      if(dis >= 3) return true;
      return false;
   }
}
