import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{
    private class Node{
        private Node parent;
        private Point pos;
        private double gCost;
        private double hCost;
        private double fCost;
        public Node(Point pos, double g, double h, double f){
            this.pos = pos;
            gCost = g;
            hCost = h;
            fCost = f;
        }
        public Node (Point pos){
            this.pos = pos;
        }
    }
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        PriorityQueue<Node> openList = new PriorityQueue<>(((o1, o2) -> (int) (o1.fCost - o2.fCost)));
        HashSet<Node> closedList = new HashSet<>();
        LinkedList<Point> path = new LinkedList<>();
        Node endPoint = null;
        if(unreachable(potentialNeighbors, end, canPassThrough)) return path;
        openList.add(new Node(start,0,distanceEuclidean(start,end), 0));
        while(!openList.isEmpty() ){
            Node curr = openList.remove();
            closedList.add(curr);
            if(withinReach.test(curr.pos, end)){
                endPoint = curr;
                break;
            }
            List<Point> neighbors= potentialNeighbors.apply(curr.pos)
                    .filter(canPassThrough)
                    .filter(pt ->  !pt.equals(start) && !pt.equals(end))
                    .collect(Collectors.toList());
            for(Point child: neighbors){
                if(closedList.contains(child)) continue;
                Node childNode = new Node (child);
                childNode.gCost = curr.gCost + distanceEuclidean(curr.pos, child);
                childNode.hCost = distanceEuclidean(child, end);
                childNode.fCost = childNode.gCost + childNode.hCost;
                childNode.parent = curr;
                if(containsPoint(openList, child)){
                    Node old = getNodeByPoint(openList, child);
                    if(childNode.gCost < old.gCost){
                        openList.removeIf(node -> node.pos == child);
                        openList.add(childNode);
                    }
                }
                else{openList.add(childNode);}
            }
        }
        while(endPoint != null && endPoint.pos != start){
            path.addFirst(endPoint.pos);
            endPoint = endPoint.parent;
        }
        return path;
    }
    public boolean containsPoint(PriorityQueue <Node> list, Point pt){
        for (Node node: list){
            if(node.pos.getX() == pt.getX() && node.pos.getY() == pt.getY())return true;
        }
        return false;
    }
    public Node getNodeByPoint(PriorityQueue<Node> list, Point pt){
        Node nd = null;
        for( Node node: list){
            if(node.pos.getX() == pt.getX() && node.pos.getY() == pt.getY()) {
                nd = node;
                break;
            }
        }
        return nd;
    }
    public boolean unreachable(Function<Point, Stream<Point>> potentialNeighbors, Point end,Predicate<Point> canPassThrough ){
        List<Point> neighbors = potentialNeighbors.apply(end)
                .filter(canPassThrough)
                .collect(Collectors.toList());
        return neighbors.size() == 0;
    }
    public double distanceManhattan (Point p1, Point p2){
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }
    public double distanceEuclidean(Point p1, Point p2){
        return Math.sqrt(Math.pow(p1.getX()-p2.getX(),2)+Math.pow(p1.getY()-p2.getY(), 2));
    }
}