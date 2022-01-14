public class Point {
    private double x;
    private double y;
    private double z;
    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public String toString(){
        return super.toString();
    }
    public double getX(){return this.x;}
    public double getY(){return this.y;}
    public double getZ(){
        return this.z;
    }
    public Point scaleDown(double scale){
        return new Point(this.x * scale, this.y * scale, this.z * scale);
    }
    public Point translate (int [] trans ){
        int var = 0;
        for (int tran: trans){
            if (var == 0) this.x += tran;
            else if (var == 1) this.y += tran;
            else this.z += tran;
            var++;
        }
        return new Point(this.x, this.y, this.z);
    }
}
