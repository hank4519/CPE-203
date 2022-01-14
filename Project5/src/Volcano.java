import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Volcano extends AnimationActionEntity{
    private static final String pirate_key = "pirate";
    Random rand=new Random();
    private static final int pirate_action_period= 2200;
    private static final int pirate_animation_period = 500;
    private static int volcano_action_period = 34000;
    private static final int volcano_animation_period = 2000;
    private boolean alive;
    private int num_of_pirates = 0;
    private static final int VOLCANO_ANIMATION_REPEAT_COUNT = 400;

    public Volcano(){}
    public Volcano(String id, Point position, List<PImage> images, boolean alive)
    {
        super(id, position, images,volcano_action_period, volcano_animation_period);
        this.alive = alive;
    }

    public void scheduleAction(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new Animation(this, world, imageStore, VOLCANO_ANIMATION_REPEAT_COUNT), this.animationPeriod);
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), volcano_action_period);
    }
    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        if(alive && num_of_pirates < 3){
            Optional<Point> openPt = world.findOpenAround(this.position);
            if(openPt.isPresent()){
                int aggressive = rand.nextInt(2);
                Pirate pi = null;
                if(aggressive == 0) {
                    pi = new Pirate(pirate_key, openPt.get(), imageStore.getImageList(pirate_key), pirate_action_period, pirate_animation_period, false);
                }else if(aggressive == 1){
                    pi = new Pirate(pirate_key, openPt.get(), imageStore.getImageList(pirate_key), pirate_action_period, pirate_animation_period, true);
                }
            world.addEntity(pi);
            pi.scheduleAction(scheduler, world, imageStore);
            num_of_pirates++;}
        }
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), this.actionPeriod);
    }
}
