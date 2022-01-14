public class Activity extends Action{
    public Activity(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
        super(entity, world, imageStore, repeatCount);
    }
    public void executeSpecificAction(EventScheduler scheduler) {
        if (entity instanceof Octo_full) {
            ((Octo_full) entity).executeActivity(this.world, this.imageStore, scheduler);
        } else if (entity instanceof Octo_not_full) {
            ((Octo_not_full) entity).executeActivity(this.world, this.imageStore, scheduler);
        } else if (entity instanceof Fish) {
            ((Fish) entity).executeActivity(this.world, this.imageStore, scheduler);
        } else if (entity instanceof Crab) {
            ((Crab) entity).executeActivity(this.world, this.imageStore, scheduler);
        } else if (entity instanceof Quake) {
            ((Quake) entity).executeActivity(this.world, this.imageStore, scheduler);
        } else if (entity instanceof Sgrass) {
            ((Sgrass) entity).executeActivity(this.world, this.imageStore, scheduler);
        } else if (entity instanceof Atlantis) {
            ((Atlantis) entity).executeActivity(this.world, this.imageStore, scheduler);
        } else if (entity instanceof Volcano) {
            ((Volcano) entity).executeActivity(this.world, this.imageStore, scheduler);
        } else if(entity instanceof Seahorse) {
            ((Seahorse)entity).executeActivity(this.world, this.imageStore, scheduler);
        }
        else if (entity instanceof Pirate){
            ((Pirate)entity).executeActivity(this.world, this.imageStore, scheduler);
        }
        else {
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            this.entity.getClass()));
        }
    }

}
