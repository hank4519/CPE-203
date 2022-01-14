import java.util.List;
import processing.core.PImage;
import java.util.Random;
public class Fish extends ActionEntity{

    private final Random rand = new Random();
    private static final String CRAB_KEY = "crab";
    private static final String CRAB_ID_SUFFIX = " -- crab";
    private static final int CRAB_PERIOD_SCALE = 4;
    private static final int CRAB_ANIMATION_MIN = 50;
    private static final int CRAB_ANIMATION_MAX = 150;

    public Fish(){}
    public Fish(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.position;  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Crab crab = new Crab(this.id + CRAB_ID_SUFFIX,
                pos, imageStore.getImageList(CRAB_KEY),this.actionPeriod / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN + rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN));

        world.addEntity(crab);
        crab.scheduleAction(scheduler, world, imageStore);
        //this.scheduleActions(scheduler, world, imageStore);
    }

    public void scheduleAction( EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), this.actionPeriod);
    }
}
