import java.util.List;
import processing.core.PImage;
public class Atlantis implements Entity, ScheduleAction, AnimationPeriod{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

    public Atlantis(){

    }
    public Atlantis(String id, Point position,
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

    public void executeActivity(WorldModel world,
                                        ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    @Override
    public int getActionPeriod() {
        return actionPeriod;
    }

    public void scheduleAction(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, ATLANTIS_ANIMATION_REPEAT_COUNT), this.animationPeriod);
    }

    public static Atlantis createAtlantis(String id, Point position,
                                  List<PImage> images)
    {
        return new Atlantis(id, position, images,
                0, 0, 0, 0);
    }

    public Point getPosition(){
        return position;
    }
    public void setPosition(Point position){
        this.position = position;
    }
    public PImage getCurrentImage() {
        return this.images.get(imageIndex);
    }
    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }
    public int getAnimationPeriod(){
        return this.animationPeriod;
    }

}