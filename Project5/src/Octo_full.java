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
        if (this.position.neighbor(target.getPosition()))
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
        Optional<Entity> volcano = world.findNearest(this.position, new Volcano());
        if(volcano.isPresent() && world.notFarAway(this, volcano.get())){
            Point pos = this.position;
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            Seahorse seahorse = new Seahorse(SEAHORSE_KEY, pos, imageStore.getImageList(SEAHORSE_KEY), Seahorse_action_period, Seahorse_animation_period);
            world.addEntity(seahorse);
            seahorse.scheduleAction(scheduler, world, imageStore);
            return;
        }
        if (fullTarget.isPresent() &&
                moveTo( world, fullTarget.get(), scheduler))
        {
            ((Atlantis)fullTarget.get()).scheduleAction(scheduler, world, imageStore);

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
