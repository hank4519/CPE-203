final class Point
{
   private final int x;
   private final int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }
   public int getX(){
      return x;
   }
   public int getY(){
      return y;
}
   public boolean adjacent( Point other )
   {
      return (this.x == other.x && Math.abs(this.y - other.y) == 1) ||
              (this.y == other.y && Math.abs(this.x - other.x) == 1);
   }
   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
         ((Point)other).x == this.x &&
         ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }
   //WorldModel
   public int distanceSquared(Point other)
   {
      int deltaX = this.x - other.x;
      int deltaY = this.y - other.y;

      return deltaX * deltaX + deltaY * deltaY;
   }
   public boolean neighbor(Point p2)
   {
      return this.x+1 == p2.x && this.y == p2.y ||
              this.x-1 == p2.x && this.y == p2.y ||
              this.x == p2.x && this.y+1 == p2.y ||
              this.x == p2.x && this.y-1 == p2.y||
              this.x+1 ==p2.x && this.y+1 == p2.y||
              this.x+1 == p2.x && this.y-1 == p2.y||
              this.x-1 == p2.x && this.y-1 == p2.y||
              this.x -1 == p2.x && this.y+1 == p2.y;
   }
}
