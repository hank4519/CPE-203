import processing.core.PImage;

import java.util.*;

/*
WorldModel ideally keeps track of the actual size of our grid world and what is in that world
in terms of entities and background elements
 */

final class WorldModel
{
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;

   private final int FISH_REACH = 1;

   public int getNumCols(){
      return numCols;
   }
   public int getNumRows(){
      return numRows;
   }
   public Set<Entity> getEntities(){
      return entities;
   }
   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }
   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (withinBounds(pos))
      {
         //modi
         return Optional.of(getBackgroundCell(pos).getCurrentImage());
      }
      else
      {
         return Optional.empty();
      }
   }
   private Background getBackgroundCell(Point pos)
   {
      return this.background[pos.y][pos.x];
   }

   private boolean withinBounds( Point pos)
   {
      return pos.y >= 0 && pos.y < this.numRows &&
              pos.x >= 0 && pos.x < this.numCols;
   }
   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -FISH_REACH; dy <= FISH_REACH; dy++)
      {
         for (int dx = -FISH_REACH; dx <= FISH_REACH; dx++)
         {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (withinBounds(newPt) &&
                    !isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }
      return Optional.empty();
   }

/*
   public void moveEntity(Entity entity, Point pos)
   {
      //crab octo full/notfull
      //if (entity instanceof Crab)

      Point oldPos = entity.getPosition();
      if (withinBounds(pos) && !pos.equals(oldPos))
      {
         setOccupancyCell( oldPos, null);
         removeEntityAt( pos);
         setOccupancyCell( pos, entity);
         //modi
         entity.setPosition(pos);
      }
   }

 */
   public void moveCrab (Crab crab, Point pos){
      Point oldPos = crab.getPosition();
      if(withinBounds(pos) && !pos.equals(oldPos)){
         setOccupancyCell( oldPos, null);
         removeEntityAt( pos);
         setOccupancyCell(pos, crab);
         crab.setPosition(pos);
      }
   }
   public void moveOctoFull(Octo_full octo, Point pos){
      Point oldPos = octo.getPosition();
      if(withinBounds(pos) && !pos.equals(oldPos)){
         setOccupancyCell( oldPos, null);
         removeEntityAt( pos);
         setOccupancyCell(pos, octo);
         octo.setPosition(pos);
      }
   }
   public void moveOctoNotFull(Octo_not_full octo, Point pos){
      Point oldPos = octo.getPosition();
      if(withinBounds(pos) && !pos.equals(oldPos)){
         setOccupancyCell( oldPos, null);
         removeEntityAt( pos);
         setOccupancyCell(pos, octo);
         octo.setPosition(pos);
      }
   }
   public Optional<Entity> getOccupant( Point pos)
   {
      if (isOccupied(pos))
      {
         return Optional.of(getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   private Entity getOccupancyCell(Point pos)
   {
      return occupancy[pos.y][pos.x];
   }

   public boolean isOccupied(Point pos)
   {
      return withinBounds(pos) &&
              getOccupancyCell(pos) != null;
   }

   public void setOccupancyCell(Point pos, Entity entity)
   {
      this.occupancy[pos.y][pos.x] = entity;
   }

   private void removeEntityAt(Point pos)
   {
      if (withinBounds(pos)
              && getOccupancyCell(pos) != null)
      {
         Entity entity = getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         this.entities.remove(entity);
         setOccupancyCell(pos, null);
      }
   }

   public void addEntity( Entity entity)
   {
      //private
      if (withinBounds(entity.getPosition()))
      {
         setOccupancyCell(entity.getPosition(), entity);
         this.entities.add(entity);
      }
   }

   /*public void removeEntity(Entity entity)
   {
      if (entity instanceof Octo_not_full)
         removeEntityAt(((Octo_not_full)entity).getPosition());
      else if(entity instanceof Octo_full)
         removeEntityAt(((Octo_full)entity).getPosition());
      else if(entity instanceof Crab)
         removeEntityAt(((Crab)entity).getPosition());
      else if(entity instanceof Fish)
         removeEntityAt(((Fish)entity).getPosition());
      else if (entity instanceof Quake)
         removeEntityAt(((Quake)entity).getPosition());
      else
         removeEntityAt(((Atlantis)entity).getPosition());
   }*/
   public void removeEntity(Entity entity)
   {
      removeEntityAt(entity.getPosition());
   }

   //Need modification
   public Optional<Entity> findNearest( Point pos,
                                              Entity kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : this.entities)
      {
         if (entity.getClass().equals(kind.getClass()))
         //if (entity.getKind() == kind)
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   private Optional<Entity> nearestEntity(List<Entity> entities,
                                                Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);//md
         int nearestDistance = nearest.getPosition().distanceSquared(pos);

         for (Entity other : entities)
         {
            int otherDistance = other.getPosition().distanceSquared(pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }
   private void setBackgroundCell(Point pos, Background background)
   {
      this.background[pos.y][pos.x] = background;
   }

   public void tryAddEntity(Entity entity)
   {
      if (this.isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      this.addEntity(entity);
   }

   public void setBackground(Point pos,
                                    Background background)
   {
      if (this.withinBounds(pos))
      {
         this.setBackgroundCell(pos, background);
      }
   }
}
