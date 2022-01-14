import java.util.List;
import processing.core.PImage;

final class Background implements Image
{
   private String id;
   private List<PImage> images;
   private int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }
   public PImage getCurrentImage() {
      return this.images.get(this.imageIndex);
   }
   public void nextImage()
   {
      this.imageIndex = (this.imageIndex + 1) % this.images.size();
   }
}
