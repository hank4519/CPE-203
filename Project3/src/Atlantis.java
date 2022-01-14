import java.util.List;
import processing.core.PImage;
public class Atlantis extends AnimationActionEntity{
    private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;
    public Atlantis(){}
    public Atlantis(String id, Point position, List<PImage> images)
    {
        super(id, position, images,0, 0);
    }

    public void scheduleAction(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new Animation(this, null, null, ATLANTIS_ANIMATION_REPEAT_COUNT), this.animationPeriod);
    }
    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }
}