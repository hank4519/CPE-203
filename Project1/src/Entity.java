import java.util.List;
import java.util.Optional;
import java.util.Random;
import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */


final class Entity
{

   private EntityKind kind;
   private String id;
   private Point position;
   private List<PImage> images;
   private int imageIndex;
   private int resourceLimit;
   private int resourceCount;
   private int actionPeriod;
   private int animationPeriod;
   private final Random rand = new Random();

   private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
   private static final String QUAKE_KEY = "quake";
   private static final String QUAKE_ID = "quake";
   private static final int QUAKE_ACTION_PERIOD = 1100;
   private static final int QUAKE_ANIMATION_PERIOD = 100;

   private static final String CRAB_KEY = "crab";
   private static final String CRAB_ID_SUFFIX = " -- crab";
   private static final int CRAB_PERIOD_SCALE = 4;
   private static final int CRAB_ANIMATION_MIN = 50;
   private static final int CRAB_ANIMATION_MAX = 150;

   private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

   private static final String FISH_KEY = "fish";
   private static final String FISH_ID_PREFIX = "fish -- ";
   private static final int FISH_CORRUPT_MIN = 20000;
   private static final int FISH_CORRUPT_MAX = 30000;

   public EntityKind getKind(){
      return this.kind;
   }
   public Entity(EntityKind kind, String id, Point position,
      List<PImage> images, int resourceLimit, int resourceCount,
      int actionPeriod, int animationPeriod)
   {
      this.kind = kind;
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;
   }
   public Point getPosition(){
      return position;
   }
   public void setPosition(Point pt){
      this.position = pt;
   }
   public PImage getCurrentImage()
   {
         return this.images.get(this.imageIndex);
   }



   public void executeOctoFullActivity(WorldModel world,
                                              ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> fullTarget = world.findNearest( this.position,
              EntityKind.ATLANTIS);

      if (fullTarget.isPresent() &&
              moveToFull( world, fullTarget.get(), scheduler))
      {
         //at atlantis trigger animation
         fullTarget.get().scheduleActions(scheduler, world, imageStore);

         //transform to unfull
         this.transformFull(world, scheduler, imageStore);
      }
      else
      {
         scheduler.scheduleEvent( this,
                 Action.createActivityAction(this, world, imageStore),
                 this.actionPeriod);
      }
   }

   public void executeOctoNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> notFullTarget = world.findNearest(this.position,
              EntityKind.FISH);

      if (!notFullTarget.isPresent() ||
              !this.moveToNotFull(world, notFullTarget.get(), scheduler) ||
              !this.transformNotFull(world, scheduler, imageStore))
      {
         scheduler.scheduleEvent( this,
                 Action.createActivityAction(this, world, imageStore),
                 this.actionPeriod);
      }
   }
   public void executeFishActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Point pos = this.position;  // store current position before removing

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      Entity crab = createCrab(this.id + CRAB_ID_SUFFIX,
              pos, this.actionPeriod / CRAB_PERIOD_SCALE,
              CRAB_ANIMATION_MIN +
                      rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
              imageStore.getImageList(CRAB_KEY));

      world.addEntity(crab);
      crab.scheduleActions(scheduler, world, imageStore);
      //this.scheduleActions(scheduler, world, imageStore);
   }

   public void executeCrabActivity(WorldModel world,
                                          ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> crabTarget = world.findNearest(this.position, EntityKind.SGRASS);
      long nextPeriod = this.actionPeriod;

      if (crabTarget.isPresent())
      {
         Point tgtPos = crabTarget.get().position;

         if (moveToCrab(world, crabTarget.get(), scheduler))
         {
            Entity quake = createQuake(tgtPos,
                    imageStore.getImageList(QUAKE_KEY));

            world.addEntity(quake);
            nextPeriod += this.actionPeriod;
            //this.scheduleActions(scheduler, world, imageStore);
            quake.scheduleActions(scheduler, world, imageStore);
         }
      }
      scheduler.scheduleEvent(this,
              Action.createActivityAction(this, world, imageStore),
              nextPeriod);
   }
   public void executeQuakeActivity(WorldModel world,
                                           ImageStore imageStore, EventScheduler scheduler)
   {
      scheduler.unscheduleAllEvents(this);
      world.removeEntity(this);
   }

   public void executeAtlantisActivity(WorldModel world,
                                              ImageStore imageStore, EventScheduler scheduler)
   {
      scheduler.unscheduleAllEvents(this);
      world.removeEntity(this);
   }

   public void executeSgrassActivity( WorldModel world,
                                      ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Point> openPt = world.findOpenAround(this.position);

      if (openPt.isPresent())
      {
         Entity fish = createFish(FISH_ID_PREFIX + this.id,
                 openPt.get(), FISH_CORRUPT_MIN +
                         rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
                 imageStore.getImageList(FISH_KEY));
         world.addEntity(fish);
         //this.scheduleActions(scheduler, world, imageStore);
         fish.scheduleActions(scheduler, world, imageStore);
      }

      scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
   }

   public static Entity createQuake(Point position, List<PImage> images)
   {
      return new Entity(EntityKind.QUAKE, QUAKE_ID, position, images,
              0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
   }

   public Entity createCrab(String id, Point position,
                                   int actionPeriod, int animationPeriod, List<PImage> images)
   {
      return new Entity(EntityKind.CRAB, id, position, images,
              0, 0, actionPeriod, animationPeriod);
   }
   //action class


   private void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
   {
      Entity octo = createOctoNotFull(this.id, this.resourceLimit,
              this.position, this.actionPeriod, this.animationPeriod,
              this.images);

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      world.addEntity(octo);
      //this.scheduleActions(scheduler, world, imageStore);
      octo.scheduleActions(scheduler, world, imageStore);
   }

   private boolean transformNotFull(WorldModel world,
                                          EventScheduler scheduler, ImageStore imageStore)
   {
      if (this.resourceCount >= this.resourceLimit)
      {
         Entity octo = createOctoFull(this.id, this.resourceLimit,
                 this.position, this.actionPeriod, this.animationPeriod,
                 this.images);

         world.removeEntity(this);
         scheduler.unscheduleAllEvents(this);

         world.addEntity(octo);
         octo.scheduleActions(scheduler, world, imageStore);

         return true;
      }
      return false;
   }

   private Entity createOctoFull(String id, int resourceLimit,
                                       Point position, int actionPeriod, int animationPeriod,
                                       List<PImage> images)
   {
      return new Entity(EntityKind.OCTO_FULL, id, position, images,
              resourceLimit, resourceLimit, actionPeriod, animationPeriod);
   }

   public static Entity createOctoNotFull(String id, int resourceLimit,
                                          Point position, int actionPeriod, int animationPeriod,
                                          List<PImage> images)
   {
      return new Entity(EntityKind.OCTO_NOT_FULL, id, position, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }
   public static Entity createAtlantis(String id, Point position,
                                       List<PImage> images)
   {
      return new Entity(EntityKind.ATLANTIS, id, position, images,
              0, 0, 0, 0);
   }
   public static Entity createObstacle(String id, Point position,
                                       List<PImage> images)
   {
      return new Entity(EntityKind.OBSTACLE, id, position, images,
              0, 0, 0, 0);
   }
   public static Entity createSgrass(String id, Point position, int actionPeriod,
                                     List<PImage> images)
   {
      return new Entity(EntityKind.SGRASS, id, position, images, 0, 0,
              actionPeriod, 0);
   }

   private Point nextPositionOcto( WorldModel world,
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
   public boolean moveToFull(WorldModel world,
                                    Entity target, EventScheduler scheduler)
   {
      if (this.position.adjacent(target.position))
      {
         return true;
      }
      else
      {
         Point nextPos = nextPositionOcto(world, target.position);

         if (!this.position.equals(nextPos))
         {
            Optional<Entity> occupant =world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity( this, nextPos);
         }
         return false;
      }
   }
   public boolean moveToNotFull(WorldModel world,
                                       Entity target, EventScheduler scheduler)
   {
      if (this.position.adjacent(target.position))
      {
         this.resourceCount += 1;
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);

         return true;
      }
      else
      {
         Point nextPos =
                 this.nextPositionOcto( world, target.position);

         if (!this.position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }

   public void scheduleActions( EventScheduler scheduler,
                                      WorldModel world, ImageStore imageStore)
   {
      switch (this.kind)
      {
         case OCTO_FULL:
         case OCTO_NOT_FULL:
         case CRAB:
            scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
            scheduler.scheduleEvent(this, createAnimationAction(this, 0), this.getAnimationPeriod());
            break;

         case SGRASS:
         case FISH:
            scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
            break;

         case QUAKE:
            scheduler.scheduleEvent(this, Action.createActivityAction(this, world, imageStore), this.actionPeriod);
            scheduler.scheduleEvent(this, createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
            break;

         case ATLANTIS:
            scheduler.scheduleEvent(this, createAnimationAction(this, ATLANTIS_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
            break;

         default:
      }
   }

   public Action createAnimationAction(Entity entity, int repeatCount)
   {
      return new Action(ActionKind.ANIMATION, entity, null, null, repeatCount);
   }

   public int getAnimationPeriod()
   {
      switch (this.kind)
      {
         case OCTO_FULL:
         case OCTO_NOT_FULL:
         case CRAB:
         case QUAKE:
         case ATLANTIS:
            return this.animationPeriod;
         default:
            throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.kind));
      }
   }

   private boolean moveToCrab( WorldModel world,
                                    Entity target, EventScheduler scheduler)
   {
      if (this.position.adjacent(target.position))
      {
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);
         return true;
      }
      else
      {
         Point nextPos = this.nextPositionCrab(world, target.position);

         if (!this.position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }
            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }

   public Point nextPositionCrab(WorldModel world,
                                        Point destPos)
   {
      int horiz = Integer.signum(destPos.x - this.position.x);
      Point newPos = new Point(this.position.x + horiz,
              this.position.y);

      Optional<Entity> occupant = world.getOccupant(newPos);

      if (horiz == 0 ||
              (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
      {
         int vert = Integer.signum(destPos.y - this.position.y);
         newPos = new Point(this.position.x, this.position.y + vert);
         occupant = world.getOccupant(newPos);

         if (vert == 0 ||
                 (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
         {
            newPos = this.position;
         }
      }
      return newPos;
   }

   public static Entity createFish(String id, Point position, int actionPeriod,
                                   List<PImage> images)
   {
      return new Entity(EntityKind.FISH, id, position, images, 0, 0,
              actionPeriod, 0);
   }

   public void nextImage()
   {
      this.imageIndex = (this.imageIndex + 1) % this.images.size();
   }



}
