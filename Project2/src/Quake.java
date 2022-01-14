import java.util.List;
import processing.core.PImage;
public class Quake implements Entity, ScheduleAction, AnimationPeriod{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    //full/notfull/crab/quake for getAnimationPeriod
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(String id, Point position,
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

    public static Quake createQuake(Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ID, position, images,
                0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
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

    public void scheduleAction( EventScheduler scheduler,
                                 WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT), this.animationPeriod);
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