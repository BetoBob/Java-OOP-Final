import processing.core.PImage;

import java.util.List;

abstract class Move extends Animated {

    public Move(String id, Point position,
                 List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

    public static boolean neighbors(Point p1, Point p2)
    {
        return p1.x+1 == p2.x && p1.y == p2.y ||
                p1.x-1 == p2.x && p1.y == p2.y ||
                p1.x == p2.x && p1.y+1 == p2.y ||
                p1.x == p2.x && p1.y-1 == p2.y;
    }

}