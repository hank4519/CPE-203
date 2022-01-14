import processing.core.PImage;

import java.util.List;

public abstract class MoveEntity extends AnimationActionEntity {
    protected PathingStrategy strategy;
    public MoveEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, PathingStrategy strategy){
        super(id, position, images, actionPeriod, animationPeriod);
        this.strategy = strategy;
    }
    public MoveEntity(){}
    abstract Point nextPosition(WorldModel world, Point destPos);
    abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

}
