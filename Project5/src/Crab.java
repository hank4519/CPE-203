import java.util.List;
import processing.core.PImage;
import java.util.Optional;

public class Crab extends MoveEntity{

    private static final String QUAKE_KEY = "quake";
    public Crab(){}
    public Crab(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod, new AStarPathingStrategy());
    }
    public Point nextPosition(WorldModel world, Point destPos){
        List<Point> list = strategy.computePath(this.position, destPos, p -> world.withinBounds(p) && !checkOccupancy(world, p),
                (p1,p2)-> p1.neighbor(p2), PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
        return list.isEmpty()? this.getPosition(): list.get(0);
    }
    public boolean checkOccupancy(WorldModel world, Point pos){
        Optional<Entity> occupant = world.getOccupant(pos);
        return occupant.isPresent() && ! (occupant.get() instanceof Fish);
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> crabTarget = world.findNearest(this.position, new Sgrass());
        long nextPeriod = this.actionPeriod;
        if (crabTarget.isPresent())
        {
            Point tgtPos = (crabTarget.get()).getPosition();

            if (moveTo(world, crabTarget.get(), scheduler))
            {
                Quake quake = new Quake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                quake.scheduleAction(scheduler, world, imageStore);
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
                world.moveCrab(this, nextPos);
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
