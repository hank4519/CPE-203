public class Activity implements Action{
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;
    public Activity(Entity entity, WorldModel world,
                  ImageStore imageStore, int repeatCount)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }
    public void executeAction(EventScheduler scheduler)
    {
        executeActivityAction(scheduler);
    }
    private void executeActivityAction(EventScheduler scheduler)
    {
        if (entity instanceof Octo_full){
            ((Octo_full)entity).executeActivity(this.world, this.imageStore, scheduler);
        }
        else if(entity instanceof  Octo_not_full){
            ((Octo_not_full)entity).executeActivity(this.world, this.imageStore, scheduler);
        }
        else if(entity instanceof Fish){
            ((Fish)entity).executeActivity(this.world, this.imageStore, scheduler);
        }
        else if(entity instanceof Crab){
            ((Crab)entity).executeActivity(this.world, this.imageStore, scheduler);
        }
        else if (entity instanceof Quake){
            ((Quake)entity).executeActivity(this.world, this.imageStore, scheduler);
        }
        else if (entity instanceof Sgrass){
            ((Sgrass)entity).executeActivity(this.world, this.imageStore, scheduler);
        }
        else if (entity instanceof Atlantis){
            ((Atlantis)entity).executeActivity(this.world, this.imageStore, scheduler);
        }
        else{
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            this.entity.getClass()));
        }
    }
    public static Activity createActivityAction(Entity entity, WorldModel world,
                                              ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }
}
