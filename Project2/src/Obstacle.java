import java.util.List;
import processing.core.PImage;
public class Obstacle implements Entity{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    public Obstacle(String id, Point position,
                  List<PImage> images, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public static Obstacle createObstacle(String id, Point position,
                                        List<PImage> images)
    {
        return new Obstacle(id, position, images,
                0, 0, 0, 0);
    }

    public Point getPosition(){
        return this.position;
    }
    public void setPosition(Point position){this.position = position;}
    public PImage getCurrentImage() {
        return this.images.get(imageIndex);
    }
    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){}

    @Override
    public int getActionPeriod() {
        return actionPeriod;
    }
}
