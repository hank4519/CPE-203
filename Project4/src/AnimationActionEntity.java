import processing.core.PImage;

import java.util.List;

public abstract class AnimationActionEntity extends ActionEntity{
    protected int animationPeriod;
    public AnimationActionEntity(){}
    public AnimationActionEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }
    public int getAnimationPeriod(){
        return this.animationPeriod;
    }
}
