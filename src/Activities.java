import processing.core.PImage;

import java.util.List;

abstract class Activities extends WorldEntity{

    public int actionPeriod;

    public Activities(String id, Point position,
                List<PImage> images,
                int actionPeriod)
    {
        super(id,position, images);
        this.actionPeriod = actionPeriod;
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore), actionPeriod);
    }
}