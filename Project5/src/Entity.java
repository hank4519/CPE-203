import java.util.List;
import processing.core.PImage;

public abstract class Entity {
   protected String id;
   protected Point position;
   protected List<PImage> images;
   protected int imageIndex;

   public Entity(String id, Point position, List<PImage> images) {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
   }

   public Entity() {
   }

   public Point getPosition() {
      return this.position;
   }

   public void setPosition(Point position) {
      this.position = position;
   }



   public PImage getCurrentImage() {
      return this.images.get(imageIndex);
   }

   public void nextImage() {
      this.imageIndex = (this.imageIndex + 1) % this.images.size();
   }
}
