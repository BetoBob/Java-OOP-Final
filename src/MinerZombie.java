import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerZombie extends Miner {

    private String ZOMBIE_ID = "zombie";

    public MinerZombie(String id, Point position,
                     List<PImage> images, int resourceLimit,
                     int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, actionPeriod*3, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> zombieTarget = position.findNearest(world, new MinerVisitor());
        long nextPeriod = actionPeriod;

        if (zombieTarget.isPresent())
        {
            Point tgtPos = zombieTarget.get().getPosition();

            if (moveTo(world, zombieTarget.get(), scheduler))
            {

                MinerZombie minerZombie = new MinerZombie(ZOMBIE_ID, tgtPos,
                        imageStore.getImageList(ZOMBIE_ID), 0,
                        ((Miner)zombieTarget.get()).getActionPeriod(),
                        ((Miner)zombieTarget.get()).getAnimationPeriod());

                world.addEntity(minerZombie);
                nextPeriod += actionPeriod;
                minerZombie.scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                nextPeriod);
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {

        if (position.adjacent(target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {

            SingleStepPathingStrategy SSPS = new SingleStepPathingStrategy();

            List<Point> pointList = SSPS.computePath(position, target.getPosition(),
                    p -> world.withinBounds(p) && !(world.isOccupied(p)),
                    (p1, p2) -> neighbors(p1, p2), PathingStrategy.CARDINAL_NEIGHBORS);

            if (pointList.size() > 0) {

                Point nextPos = pointList.get(0);

                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }
}
