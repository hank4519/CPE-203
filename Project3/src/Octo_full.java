import java.util.List;
import processing.core.PImage;
import java.util.Optional;

public class Octo_full extends Octo{


    public Octo_full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }
    protected boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Octo_not_full octo = new Octo_not_full(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod );
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        octo.scheduleAction(scheduler, world, imageStore);
        return false;
    }
    public boolean moveTo(WorldModel world,
                              Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!this.position.equals(nextPos))
            {
                Optional<Entity> occupant =world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveOctoFull(this, nextPos);
            }
            return false;
        }
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest( this.position, new Atlantis());

        if (fullTarget.isPresent() &&
                moveTo( world, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
            //fullTarget.get().scheduleAction(scheduler, world, imageStore);
            ((Atlantis)fullTarget.get()).scheduleAction(scheduler, world, imageStore);

            //transform to unfull
            this.transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent( this,
                    new Activity(this, world, imageStore,0),
                    this.actionPeriod);
        }
    }
}
