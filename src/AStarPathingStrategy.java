import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy
{
    public List<Point> computePath(Point source, Point goal,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {

        List<Node> path = new ArrayList<>();
        List<Point> closedSet = new ArrayList<>();
        List<Point> openSetPoints = new ArrayList<>();
        List<Node> openSet = new ArrayList<>();

        openSet.add(new Node(source));
        openSetPoints.add(source);

        while (openSet.size() != 0) {

            Node cur = openSet.get(0);
            if (cur.getPoint() == goal) {
                return path.stream()
                        .map(Node::toPoint)
                        .collect(Collectors.toList());
            }

            openSet.remove(0);
            openSetPoints.remove(cur.getPoint());

            path.add(cur);
            closedSet.add(cur.getPoint());
            path.sort(Comparator.comparing(Node::getfScore));

            List<Node> cur_neighbors =
                    potentialNeighbors.apply(cur.getPoint())
                    .filter(canPassThrough)
                    .filter(pt ->
                            !pt.equals(cur.getPoint())
                                    && !pt.equals(goal))
                            .map(Point::toNode)
                    .collect(Collectors.toList());

            for (Node neigh : cur_neighbors) {
                if (!closedSet.contains(neigh.getPoint())) {
                    if (!openSetPoints.contains(neigh.getPoint())) {
                        neigh.setgScore(cur.getgScore() + 1);
                        neigh.sethScore(hScore(neigh.getPoint(), goal));
                        neigh.computefScore();

                        openSet.add(neigh);
                        openSet.sort(Comparator.comparing(Node::getfScore));
                        openSetPoints.add(neigh.getPoint());
                    }
                    /*
                    System.out.print("Cur gScore (+1): ");
                    System.out.println(cur.getgScore() + 1);
                    System.out.print("Neigh gScore: ");
                    System.out.println(neigh.getgScore());
                    */
                    if (cur.getgScore() + 1 < neigh.getgScore()) {
                        System.out.println("made it here");
                        neigh.setgScore(cur.getgScore() + 1);
                        neigh.setfScore(neigh.getgScore() + hScore(neigh.getPoint(), goal));

                        //path.remove(0);
                        openSet.add(neigh);
                        openSet.sort(Comparator.comparing(Node::getfScore));
                        openSetPoints.add(neigh.getPoint());
                    }
                }
            }
        }

        return path.stream()
                .map(Node::toPoint)
                .collect(Collectors.toList());
    }

    //manhattan distance
    public int hScore(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}