import java.util.List;
import processing.core.PImage;
import java.util.Random;
public class Fish implements Entity, ScheduleAction {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;
    private final Random rand = new Random();

    private static final String CRAB_KEY = "crab";
    private static final String CRAB_ID_SUFFIX = " -- crab";
    private static final int CRAB_PERIOD_SCALE = 4;
    private static final int CRAB_ANIMATION_MIN = 50;
    private static final int CRAB_ANIMATION_MAX = 150;

    public Fish(){

    }
    public Fish(String id, Point position,
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

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.position;  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Crab crab = Crab.createCrab(this.id + CRAB_ID_SUFFIX,
                pos, this.actionPeriod / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN +
                        rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
                imageStore.getImageList(CRAB_KEY));

        world.addEntity(crab);
        crab.scheduleAction(scheduler, world, imageStore);
        //this.scheduleActions(scheduler, world, imageStore);
    }

    @Override
    public int getActionPeriod() {
        return actionPeriod;
    }

    public static Fish createFish(String id, Point position, int actionPeriod,
                                    List<PImage> images)
    {
        return new Fish( id, position, images, 0, 0,
                actionPeriod, 0);
    }

    public void scheduleAction( EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
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
}
