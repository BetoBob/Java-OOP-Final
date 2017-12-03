public class Node {
    private final Point point;
    private int fScore; // total cost to get to a node                                                      (start to goal)
    private int gScore; // cost it took to get to the node; the number of squares passed by from the start  (start to node)
    private int hScore; // guess as to how much it'll cost to reach the goal from the node point            (node to goal)
    private Node parent;

    public Node(Point point) {
        this.point = point;
        this.gScore = 0;
        this.hScore = 0;
        this.fScore = 0;
        this.parent = null;
    }

    // getter methods
    public Point getPoint() { return point; }
    public int getfScore() { return fScore; }
    public int getgScore() { return gScore; }
    public int gethScore() { return hScore; }

    public void setfScore(int f) { fScore = f; }
    public void setgScore(int g) { gScore = g; }
    public void sethScore(int h) { hScore = h; }
    public void setParent(Node parent) { this.parent = parent; }

    public void computefScore() { fScore = gScore + hScore; }

    public Point toPoint() {
        return point;
    }
}
