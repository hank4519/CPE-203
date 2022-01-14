import java.util.List;
import processing.core.PImage;
import java.util.Optional;

public class Crab implements Move, Entity, ScheduleAction, AnimationPeriod{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    private static final String QUAKE_KEY = "quake";

    public Crab(String id, Point position,
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

    public void executeActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> crabTarget = world.findNearest(this.position, new Sgrass());
        long nextPeriod = this.actionPeriod;

        if (crabTarget.isPresent())
        {
            Point tgtPos = (crabTarget.get()).getPosition();

            if (moveTo(world, crabTarget.get(), scheduler))
            {
                Quake quake = Quake.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                //this.scheduleActions(scheduler, world, imageStore);
                quake.scheduleAction(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    @Override
    public int getActionPeriod() {
        return actionPeriod;
    }

    public boolean moveTo( WorldModel world,
                                Entity target, EventScheduler scheduler)
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

    public void scheduleAction( EventScheduler scheduler,
                                 WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0), this.animationPeriod);
    }

    public static Crab createCrab(String id, Point position,
                             int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Crab(id, position, images,
                0, 0, actionPeriod, animationPeriod);
    }

    public Point nextPosition(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz,
                this.position.y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get() instanceof Fish)))
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
    public Point getPosition(){
        return position;
    }
    public void setPosition(Point pt){
        this.position = pt;
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
