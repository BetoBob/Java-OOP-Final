import processing.core.PImage;

import java.util.List;

abstract class Animated extends Activities {
    public int animationPeriod;

    public Animated(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore), actionPeriod);
        scheduler.scheduleEvent(this,
                new Animation(this, 0), getAnimationPeriod());
    }

}