import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class PumpkinMan extends Move {

    public PumpkinMan(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> pumpkinTarget = position.findNearest(world, new VeinVisitor());
        long nextPeriod = actionPeriod;

        if (pumpkinTarget.isPresent())
        {

            if (moveTo(world, pumpkinTarget.get(), scheduler))
            {
                nextPeriod += actionPeriod;
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