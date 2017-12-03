import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class MinerNotFull extends Miner  {

    public int resourceCount;

    public MinerNotFull(String id, Point position,
                     List<PImage> images, int resourceLimit, int resourceCount,
                     int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);
        this.resourceCount = resourceCount;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = position.findNearest(world, new OreVisitor());

        if (!notFullTarget.isPresent() ||
                !moveTo(world, notFullTarget.get(), scheduler) ||
                !transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), actionPeriod);
        }
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(target.getPosition()))
        {
            resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }

        else {
            AStarPathingStrategy ASPS = new AStarPathingStrategy();
            //SingleStepPathingStrategy ASPS = new SingleStepPathingStrategy();

            List<Point> pointList = ASPS.computePath(position, target.getPosition(),
                    p -> world.withinBounds(p) && !(world.isOccupied(p)),
                    (p1, p2) -> neighbors(p1, p2), PathingStrategy.CARDINAL_NEIGHBORS);

            //System.out.print("pointlist: ");
            //System.out.println(pointList);

            if (pointList.size() > 1) {

                Point nextPos = pointList.get(1);
                //System.out.println(nextPos);

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
        if (resourceCount >= resourceLimit)
        {
            MinerFull miner = new MinerFull(id, position, images,
                    resourceLimit, actionPeriod, animationPeriod);

            world.removeEntity(miner);
            scheduler.unscheduleAllEvents(miner);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }
}