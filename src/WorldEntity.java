import processing.core.PImage;

import java.util.List;

public abstract class WorldEntity implements Entity
{
    public String id;
    public Point position;
    public List<PImage> images;
    protected int imageIndex = 0;

    public WorldEntity(String id, Point position, List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
    }


    //getter methods
    public Point getPosition() { return position; }
    public PImage getCurrentImage() { return images.get(imageIndex); }
    public void setPosition(Point p) { position = p; }

}

