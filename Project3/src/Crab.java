import java.util.List;
import processing.core.PImage;
import java.util.Optional;

public class Crab extends MoveEntity{

    private static final String QUAKE_KEY = "quake";

    public Crab(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public Point nextPosition(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz,
                this.position.y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent()))// && !(occupant.get() instanceof Fish)))
        {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x, this.position.y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get() instanceof Fish)))
            {
                newPos = this.position;
            }
        }
        return newPos;
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> crabTarget = world.findNearest(this.position, new Sgrass());
        long nextPeriod = this.actionPeriod;
        System.out.println("ExecuteActivity");
        if (crabTarget.isPresent())
        {
            Point tgtPos = (crabTarget.get()).getPosition();

            if (moveTo(world, crabTarget.get(), scheduler))
            {
                Quake quake = new Quake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                //this.scheduleActions(scheduler, world, imageStore);
                quake.scheduleAction(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore, 0),
                nextPeriod);
    }
    public boolean moveTo( WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(((Sgrass)target).getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, ((Sgrass)target).getPosition());

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
