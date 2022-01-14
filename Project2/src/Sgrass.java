import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Sgrass implements Entity, ScheduleAction {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;
    private final Random rand = new Random();

    private static final String FISH_KEY = "fish";
    private static final String FISH_ID_PREFIX = "fish -- ";
    private static final int FISH_CORRUPT_MIN = 20000;
    private static final int FISH_CORRUPT_MAX = 30000;

    public Sgrass(){}

    public Sgrass(String id, Point position,
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

    public void executeActivity( WorldModel world,
                                       ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent())
        {
            Fish fish = Fish.createFish(FISH_ID_PREFIX + this.id,
                    openPt.get(), FISH_CORRUPT_MIN +
                            rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
                    imageStore.getImageList(FISH_KEY));
            world.addEntity(fish);
            //this.scheduleActions(scheduler, world, imageStore);
            fish.scheduleAction(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
    }

    @Override
    public int getActionPeriod() {
        return actionPeriod;
    }

    public void scheduleAction( EventScheduler scheduler,
                                 WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
    }

    public Point getPosition(){
        return position;
    }
    public void setPosition(Point position){this.position=position;}
    public static Sgrass createSgrass(String id, Point position, int actionPeriod,
                                      List<PImage> images)
    {
        return new Sgrass (id, position, images, 0, 0,
                actionPeriod, 0);
    }
    public PImage getCurrentImage() {
        return this.images.get(imageIndex);
    }
    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }
}
