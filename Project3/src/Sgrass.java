import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Sgrass extends ActionEntity{
    private final Random rand = new Random();
    private static final String FISH_KEY = "fish";
    private static final String FISH_ID_PREFIX = "fish -- ";
    private static final int FISH_CORRUPT_MIN = 20000;
    private static final int FISH_CORRUPT_MAX = 30000;

    public Sgrass(){}
    public Sgrass(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }
    public void executeActivity( WorldModel world,
                                       ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent())
        {
            Fish fish = new Fish(FISH_ID_PREFIX + this.id,
                    openPt.get(), imageStore.getImageList(FISH_KEY), FISH_CORRUPT_MIN +
                            rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN));
            world.addEntity(fish);
            //this.scheduleActions(scheduler, world, imageStore);
            fish.scheduleAction(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), this.actionPeriod);
    }

    public void scheduleAction( EventScheduler scheduler,
                                 WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), this.actionPeriod);
    }
}
