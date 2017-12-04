import processing.core.PImage;

import java.util.List;

public class Quake extends Animated  {

    public String id;
    public Point position;
    public List<PImage> images;
    public int actionPeriod;
    public int animationPeriod;

    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
        {
            super(id, position, images, actionPeriod, animationPeriod);
        }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                actionPeriod);
        scheduler.scheduleEvent(this,
                new Animation(this, QUAKE_ANIMATION_REPEAT_COUNT),
                animationPeriod);
    }

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }

}