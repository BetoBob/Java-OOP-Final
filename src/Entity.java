import processing.core.PImage;

public interface Entity {
   Point getPosition();
   PImage getCurrentImage();
   void setPosition(Point P);

   <R> R accept(EntityVisitor<R> visitor);
   
}