public class Animation extends Action{
    public Animation(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount)
    {
        super(entity, world, imageStore, repeatCount);
    }
    public void executeSpecificAction(EventScheduler scheduler)
    {

        this.entity.nextImage();
        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent( this.entity,
                    new Animation(this.entity, null, null, Math.max(this.repeatCount - 1, 0)),
                    ((AnimationActionEntity)(this.entity)).getAnimationPeriod());
        }
    }
}
