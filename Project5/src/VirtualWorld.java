import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.w3c.dom.html.HTMLAreaElement;
import processing.core.*;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.border.EmptyBorder;


/*
VirtualWorld is our main wrapper
It keeps track of data necessary to use Processing for drawing but also keeps track of the necessary
components to make our world run (eventScheduler), the data in our world (WorldModel) and our
current view (think virtual camera) into that world (WorldView)
 */

public final class VirtualWorld
   extends PApplet
{
   private static final int PROPERTY_KEY = 0;
   private static final int TIMER_ACTION_PERIOD = 100;
   private static final String OCTO_KEY = "octo";
   private static final int OCTO_NUM_PROPERTIES = 7;
   private static final int OCTO_ID = 1;
   private static final int OCTO_COL = 2;
   private static final int OCTO_ROW = 3;
   private static final int OCTO_LIMIT = 4;
   private static final int OCTO_ACTION_PERIOD = 5;
   private static final int OCTO_ANIMATION_PERIOD = 6;

   private static final int VIEW_WIDTH = 640;
   private static final int VIEW_HEIGHT = 480;
   private static final int TILE_WIDTH = 32;
   private static final int TILE_HEIGHT = 32;
   private static final int WORLD_WIDTH_SCALE = 2;
   private static final int WORLD_HEIGHT_SCALE = 2;

   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_ID = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;

   private static final String f_seahorse = "female_seahorse";
   private static final int F_SEAHORSE_NUM_PROPERTIES = 4;
   private static final int F_SEAHORSE_ID = 1;
   private static final int F_SEAHORSE_COL = 2;
   private static final int F_SEAHORSE_ROW = 3;

   private static final String FISH_KEY = "fish";
   private static final int FISH_NUM_PROPERTIES = 5;
   private static final int FISH_ID = 1;
   private static final int FISH_COL = 2;
   private static final int FISH_ROW = 3;
   private static final int FISH_ACTION_PERIOD = 4;

   private static final String VOLCANO_KEY = "volcano";
   private static final String SEAHORSE_KEY = "seahorse";
   private static final String F_SEAHORSE_KEY = "female_seahorse";
   private static final String GOLD_KEY = "gold";
   private static final String ATLANTIS_KEY = "atlantis";
   private static final int ATLANTIS_NUM_PROPERTIES = 4;
   private static final int ATLANTIS_ID = 1;
   private static final int ATLANTIS_COL = 2;
   private static final int ATLANTIS_ROW = 3;
   private static final int ATLANTIS_ANIMATION_PERIOD = 70;

   private static final String SGRASS_KEY = "seaGrass";
   private static final int SGRASS_NUM_PROPERTIES = 5;
   private static final int SGRASS_ID = 1;
   private static final int SGRASS_COL = 2;
   private static final int SGRASS_ROW = 3;
   private static final int SGRASS_ACTION_PERIOD = 4;

   private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

   private static final String IMAGE_LIST_FILE_NAME = "imagelist";
   private static final String DEFAULT_IMAGE_NAME = "background_default";
   private static final int DEFAULT_IMAGE_COLOR = 0x808080;

   private static final String LOAD_FILE_NAME = "world.sav";

   private static final String FAST_FLAG = "-fast";
   private static final String FASTER_FLAG = "-faster";
   private static final String FASTEST_FLAG = "-fastest";
   private static final double FAST_SCALE = 0.5;
   private static final double FASTER_SCALE = 0.25;
   private static final double FASTEST_SCALE = 0.10;

   private static final String BGND_KEY = "background";
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_ID = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;

   private static double timeScale = 1.0;

   private ImageStore imageStore;
   private WorldModel world;
   private WorldView view;
   private EventScheduler scheduler;
   private long next_time;

   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */
   public void setup()
   {
      this.imageStore = new ImageStore(
         createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
         createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
         TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         this.scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
      }
      view.drawViewport();
   }
   private int d_x;
   private int d_y;
   public void keyPressed()
   {
      if (key == CODED)
      {
         int dx = 0;
         int dy = 0;

         switch (keyCode)
         {
            case UP:
               dy = -1;
               if(d_y > 0)d_y -= 1;
               break;
            case DOWN:
               dy = 1;
               if(d_y <= 30)d_y += 1;
               break;
            case LEFT:
               dx = -1;
               if(d_x > 0) d_x -=1;
               break;
            case RIGHT:
               dx = 1;
               if(d_x <= 40) d_x +=1;
               break;
         }
         view.shiftView(dx, dy);
      }
   }
   private static int max = 7;
   private static int min = 4;
   private static final int half_world = 200;
   private int occupied = 0;
   public void mousePressed() {
      Point pressed = mouseToPoint(mouseX, mouseY);
      pressed = pressed.translate(d_x, d_y);
      //System.out.println(pressed);
      if (!world.getOccupant(pressed).isPresent() && occupied < half_world) {
         Random rand = new Random();
         int tiles = rand.nextInt(max - min) + min;
         ArrayList<Point> list = new ArrayList<>();
         list.add(pressed);
         while (tiles > 0) {
            Point start_point = getRandomElement(list);
            Point neighbor = start_point.getRandomNeighbor(world);
            if (neighbor != null) {
               list.add(neighbor);
               tiles--;
            }
         }
         int rand_alive_volcano =rand.nextInt(list.size());
         int index = 0;
         for (Point pt : list) {
            if(index == rand_alive_volcano){
               Volcano vol = new Volcano(VOLCANO_KEY, pt, imageStore.getImageList(VOLCANO_KEY), true);
               world.addEntity(vol);
               vol.scheduleAction(scheduler, world, imageStore);
            }else{
               Volcano vol = new Volcano(VOLCANO_KEY, pt, imageStore.getImageList(VOLCANO_KEY), false);
               world.addEntity(vol);
               vol.scheduleAction(scheduler, world, imageStore);
            }
            index++;
         }
         occupied+=list.size();
      }
   }
   public Point getRandomElement(ArrayList<Point> list)
   {
      Random rand = new Random();
      return list.get(rand.nextInt(list.size()));
   }

   private Point mouseToPoint(int x, int y)
   {
      return new Point(mouseX/32, mouseY/32);
   }

   public static boolean parseAtlantis(String [] properties, WorldModel world,
                                       ImageStore imageStore)
   {
      if (properties.length == ATLANTIS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                 Integer.parseInt(properties[ATLANTIS_ROW]));
         Entity entity = new Atlantis(properties[ATLANTIS_ID], pt, imageStore.getImageList(ATLANTIS_KEY));
         world.tryAddEntity(entity);
      }
      return properties.length == ATLANTIS_NUM_PROPERTIES;
   }
   public static Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
         imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   public static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
      PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         loadImages(in, imageStore, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public static void loadWorld(WorldModel world, String filename,
      ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         load(in, world, imageStore);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private static void scheduleActions(WorldModel world,
      EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.getEntities())
      {
         //Only start actions for entities that include action (not those with just animations)
         if (entity instanceof ActionEntity && ((ActionEntity)entity).getActionPeriod()> 0) {

               ((ActionEntity)entity).scheduleAction(scheduler, world, imageStore);}

      }
   }

   public static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }
   public static void loadImages(Scanner in, ImageStore imageStore,
                                 PApplet screen)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            imageStore.processImageLine(in.nextLine(), screen);
         }
         catch (NumberFormatException e)
         {
            System.out.println(String.format("Image format error on line %d",
                    lineNumber));
         }
         lineNumber++;
      }
   }


   public static void load (Scanner in, WorldModel world, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), world, imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }
   public static boolean processLine(String line, WorldModel world,
                              ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return parseBackground(properties, world, imageStore);
            case OCTO_KEY:
               return parseOcto(properties, world, imageStore);
            case OBSTACLE_KEY:
               return parseObstacle(properties, world, imageStore);
            case FISH_KEY:
               return parseFish(properties, world, imageStore);
            case ATLANTIS_KEY:
               return parseAtlantis(properties, world, imageStore);
            case SGRASS_KEY:
               return parseSgrass(properties, world, imageStore);
            case F_SEAHORSE_KEY:
               return parseFemaleSeahorse(properties, world, imageStore);
            case GOLD_KEY:
               return parseGold(properties, world, imageStore);
         }
      }
      return false;
   }
   public static boolean parseBackground(String [] properties,
                                         WorldModel world, ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         world.setBackground(pt, new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }
   public static boolean parseFemaleSeahorse(String [] properties, WorldModel world, ImageStore imageStore){
      if(properties.length == F_SEAHORSE_NUM_PROPERTIES){
         Point pt = new Point(Integer.parseInt(properties[F_SEAHORSE_COL]), Integer.parseInt(properties[F_SEAHORSE_ROW]));
         String id = properties[F_SEAHORSE_ID];
         FemaleSeahorse f_sea = new FemaleSeahorse(id, pt, imageStore.getImageList(id));
         world.tryAddEntity(f_sea);
      }
      return properties.length == F_SEAHORSE_NUM_PROPERTIES;
   }
   private static final int GOLD_PROPERTY =3 ;
   public static boolean parseGold(String []properties, WorldModel world, ImageStore imageStore){
      if(properties.length == GOLD_PROPERTY) {
         Point pt = new Point(Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
         String id = properties[0];
         Gold gold = new Gold(id, pt, imageStore.getImageList(id));
         world.tryAddEntity(gold);
      }
      return properties.length == GOLD_PROPERTY;
   }
   public static boolean parseOcto(String [] properties, WorldModel world,
                                   ImageStore imageStore)
   {
      if (properties.length == OCTO_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[OCTO_COL]),
                 Integer.parseInt(properties[OCTO_ROW]));
         Entity entity = new Octo_not_full(properties[OCTO_ID],
                 pt,
                 imageStore.getImageList(OCTO_KEY),
                 Integer.parseInt(properties[OCTO_LIMIT]),
                 0,
                 Integer.parseInt(properties[OCTO_ACTION_PERIOD]),
                 Integer.parseInt(properties[OCTO_ANIMATION_PERIOD]))
                 ;
         world.tryAddEntity(entity);
      }

      return properties.length == OCTO_NUM_PROPERTIES;
   }

   public static boolean parseObstacle(String [] properties, WorldModel world,
                                       ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Entity entity = new Obstacle(properties[OBSTACLE_ID],
                 pt, imageStore.getImageList(OBSTACLE_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public static boolean parseFish(String [] properties, WorldModel world,
                                   ImageStore imageStore)
   {
      if (properties.length == FISH_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[FISH_COL]),
                 Integer.parseInt(properties[FISH_ROW]));
         Entity entity = new Fish(properties[FISH_ID],
                 pt, imageStore.getImageList(FISH_KEY),Integer.parseInt(properties[FISH_ACTION_PERIOD]));
         world.tryAddEntity(entity);
      }

      return properties.length == FISH_NUM_PROPERTIES;
   }


   public static boolean parseSgrass(String [] properties, WorldModel world,
                                     ImageStore imageStore)
   {
      if (properties.length == SGRASS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[SGRASS_COL]),
                 Integer.parseInt(properties[SGRASS_ROW]));
         Entity entity = new Sgrass(properties[SGRASS_ID],
                 pt, imageStore.getImageList(SGRASS_KEY),
                 Integer.parseInt(properties[SGRASS_ACTION_PERIOD]));
         world.tryAddEntity(entity);
      }

      return properties.length == SGRASS_NUM_PROPERTIES;
   }

   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }
}
