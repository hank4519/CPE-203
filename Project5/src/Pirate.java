import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Pirate extends MoveEntity {

    private static final String gold_keyword = "gold";
    private int gold_box = 0;
    private static final String gold_square_key = "goldsquare";
    private static final String quake_key = "quake";
    private static final String tomb_key = "tomb";
    private boolean aggressive;
    private int rip = 0;
    private static final int rip_limit = 2;
    private static final Random rand = new Random();
    public Pirate(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, boolean aggressive) {
        super(id, position, images, actionPeriod, animationPeriod, new AStarPathingStrategy());
        this.aggressive = aggressive;
    }

    public void scheduleAction(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, 0), this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this, null, null, 0), this.animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        long nextPeriod = this.actionPeriod;
        if (gold_box < 2) {
            Optional<Entity> traget = world.findNearest(this.position, new Gold());

            if (traget.isPresent()) {
                Point tgtPos = (traget.get()).getPosition();
                if (moveTo(world, traget.get(), scheduler)) {
                    Quake quake = new Quake(tgtPos,
                            imageStore.getImageList(quake_key));
                    world.addEntity(quake);
                    nextPeriod += this.actionPeriod;
                    quake.scheduleAction(scheduler, world, imageStore);
                    gold_box++;
                }
            }
        } else {
            if(!aggressive) {
                Optional<Entity> target = world.findNearest(this.position, new Obstacle());
                this.actionPeriod = 1000;
                if (target.isPresent()) {
                    Point tgtPos = (target.get()).getPosition();
                    if (moveTo(world, target.get(), scheduler)) {
                        GoldObstacle updated = new GoldObstacle(gold_square_key, tgtPos, imageStore.getImageList(gold_square_key));
                        Quake quake = new Quake(this.position, imageStore.getImageList(quake_key));
                        world.addEntity(updated);
                        scheduler.unscheduleAllEvents(this);
                        world.removeEntity(this);
                        world.addEntity(quake);
                        quake.scheduleAction(scheduler, world, imageStore);
                        return;
                    }
                }
            }else if(aggressive){
//                int attacktarget = rand.nextInt(3);
//                Optional<Entity> target = null;
//                if(attacktarget == 0){target = world.findNearest(this.position, new Crab());}
//                else if(attacktarget == 1){target = world.findNearest(this.position, new Seahorse());}
//                else if(attacktarget == 2){target = world.findNearest(this.position, new Octo_not_full());}
                Optional<Entity> target = world.findNearest(this.position, new Crab());
                this.actionPeriod = 1000;
                if (target.isPresent()) {
                    Point tgtPos = (target.get()).getPosition();
                    if (moveTo(world, target.get(), scheduler)) {
                        Tomb tomb = new Tomb(tomb_key, tgtPos, imageStore.getImageList(tomb_key));
                        world.addEntity(tomb);
                        rip++;
                        if(rip ==rip_limit) aggressive = !aggressive;
                    }
                }
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
                world.movePirate(this, nextPos);
            }
            return false;
        }
    }
    public Point nextPosition(WorldModel world, Point destPos){
        List<Point> list = strategy.computePath(this.position, destPos, p -> world.withinBounds(p) && !world.getOccupant(p).isPresent(),
                (p1,p2)-> p1.neighbor(p2), PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
        return list.isEmpty()? this.getPosition(): list.get(0);
    }
}
