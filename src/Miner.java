import processing.core.PImage;

import java.util.List;

abstract class Miner extends Move {

    public int resourceLimit;

    public Miner(String id, Point position,
                 List<PImage> images, int resourceLimit,
                 int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

}