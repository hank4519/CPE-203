import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class Octo extends MoveEntity{
    public Octo(){}
    protected int resourceCount;
    protected int resourceLimit;

    protected static final String SEAHORSE_KEY = "seahorse";
    protected static final int Seahorse_action_period = 2000;
    protected static final int Seahorse_animation_period = 1000;
    public Octo(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod, new AStarPathingStrategy());
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }
    public void scheduleAction( EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this, null, null,0), this.animationPeriod);
    }

    protected abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
    public Point nextPosition(WorldModel world, Point destPos){
        List<Point> list = strategy.computePath(this.position, destPos, p -> world.withinBounds(p) && !checkOccupancy(world, p),
                (p1,p2)-> p1.neighbor(p2), PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
        return list.isEmpty()? this.getPosition(): list.get(0);
    }
    public boolean checkOccupancy (WorldModel world, Point pos){
        Optional<Entity> occupant = world.getOccupant(pos);
        return occupant.isPresent();
    }
}
