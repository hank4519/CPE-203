import java.util.List;
import processing.core.PImage;
import java.util.Optional;

public class Octo_full implements Entity, Move, ScheduleAction, AnimationPeriod{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    public Octo_full(String id, Point position,
                  List<PImage> images, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
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
            this.transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent( this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    @Override
    public int getActionPeriod() {
        return actionPeriod;
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
    public void scheduleAction( EventScheduler scheduler,
                                 WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0), this.animationPeriod);
    }

    private void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Octo_not_full octo = Octo_not_full.createOctoNotFull(this.id, this.resourceLimit,
                this.position, this.actionPeriod, this.animationPeriod,
                this.images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        //this.scheduleActions(scheduler, world, imageStore);
        octo.scheduleAction(scheduler, world, imageStore);
    }

    public Point nextPosition( WorldModel world,
                                    Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz,
                this.position.y);

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x,
                    this.position.y + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public static Octo_full createOctoFull(String id, int resourceLimit,
                                  Point position, int actionPeriod, int animationPeriod,
                                  List<PImage> images)
    {
        return new Octo_full(id, position, images,
                resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }
    public Point getPosition(){
        return position;
    }
    public void setPosition(Point position){
        this.position = position;
    }
    public PImage getCurrentImage() {
        return this.images.get(imageIndex);
    }
    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }
    public int getAnimationPeriod(){
        return this.animationPeriod;
    }



}
