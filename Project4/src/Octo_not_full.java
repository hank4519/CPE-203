import java.util.List;
import processing.core.PImage;
import java.util.Optional;

public class Octo_not_full extends Octo {

    public Octo_not_full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }
    protected boolean transform(WorldModel world,
                                     EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.resourceCount >= this.resourceLimit)
        {
            Octo_full octo = new Octo_full(id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(octo);
            octo.scheduleAction(scheduler, world, imageStore);

            return true;
        }
        return false;
    }
    public boolean moveTo(WorldModel world,
                          Entity target, EventScheduler scheduler)
    {
        if (this.position.neighbor(target.getPosition()))
        {
            this.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = this.nextPosition( world, target.getPosition());
            if (!this.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveOctoNotFull(this, nextPos);
            }
            return false;
        }
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.position, new Fish());
        if (!notFullTarget.isPresent() ||
                !this.moveTo(world, notFullTarget.get(), scheduler) ||
                !this.transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent( this,
                    new Activity(this, world, imageStore, 0),
                    this.actionPeriod);
        }
    }
}
