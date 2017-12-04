import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class MinerFull extends Miner  {

    public MinerFull(String id, Point position,
                 List<PImage> images, int resourceLimit,
                 int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = position.findNearest(world, new BlackSmithVisitor());

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler))
        {
            transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), actionPeriod);
        }
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(target.getPosition()))
        {
            return true;
        }
        else {

            SingleStepPathingStrategy SSPS = new SingleStepPathingStrategy();

            List<Point> pointList = SSPS.computePath(position, target.getPosition(),
                    p -> world.withinBounds(p) && !(world.isOccupied(p)),
                    (p1, p2) -> neighbors(p1, p2), PathingStrategy.CARDINAL_NEIGHBORS);

            if (pointList.size() > 0) {

                Point nextPos = pointList.get(0);

                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
        }
        return false;
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        MinerNotFull miner = new MinerNotFull(id, position, images,
                resourceLimit, 0, actionPeriod, animationPeriod);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);

        return true;
    }

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }
}