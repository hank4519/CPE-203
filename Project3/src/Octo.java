import processing.core.PImage;

import java.util.List;

public abstract class Octo extends MoveEntity{

    protected int resourceCount;
    protected int resourceLimit;

    public Octo(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }
    public void scheduleAction( EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this, null, null,0), this.animationPeriod);
    }

    protected abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}
