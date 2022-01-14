import processing.core.PImage;

import java.util.List;

public abstract class ActionEntity extends Entity{
    protected int actionPeriod;
    public ActionEntity(){}
    public ActionEntity(String id, Point position, List<PImage> images, int actionPeriod){
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }
    public int getActionPeriod() {
        return actionPeriod;
    }
    abstract void scheduleAction( EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
