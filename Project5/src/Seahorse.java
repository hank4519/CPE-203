import processing.core.PImage;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class Seahorse extends MoveEntity{
    private static final int Seahorse_repeat_count = 500;
    private static final String Crystal_key = "crystal";
    public Seahorse(){}
    public Seahorse(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod, new AStarPathingStrategy());
    }
    public Point nextPosition(WorldModel world, Point destPos){
        List<Point> list = strategy.computePath(this.position, destPos, p -> world.withinBounds(p) && !world.getOccupant(p).isPresent(),
                (p1,p2)-> p1.neighbor(p2), PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
        return list.isEmpty()? this.getPosition(): list.get(0);
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> traget = world.findNearest(this.position, new FemaleSeahorse());
        long nextPeriod = this.actionPeriod;
        if (traget.isPresent())
        {
            Point tgtPos = (traget.get()).getPosition();

            if (moveTo(world, traget.get(), scheduler))
            {
                Crystal cr = new Crystal(Crystal_key, tgtPos, imageStore.getImageList(Crystal_key));
                world.addEntity(cr);
                nextPeriod += this.actionPeriod;
            }
        }
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore, 0),
                nextPeriod);
    }
    public boolean moveTo( WorldModel world, Entity target, EventScheduler scheduler)
    {
        if(this.position.neighbor((target).getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!this.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                world.moveSeahorse(this, nextPos);
            }
            return false;
        }
    }
    public void scheduleAction( EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore,0), this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this,null,null, 0), this.animationPeriod);
    }
}
