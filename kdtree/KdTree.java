/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;
    private Point2D nearestPoint;
    private double shortestDistance;

    private enum Orientation {
        VERTICAL, HORIZONTAL;

        public Orientation flip() {
            return this == Orientation.VERTICAL ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        }
    }

    private static class Node implements Comparable<Point2D> {
        private final Point2D point;
        private final RectHV rect;
        private Node left, right;
        private final Orientation orientation;

        public Node(Point2D p, Node left, Node right, Orientation orientation, Node parent) {
            point = p;
            this.left = left;
            this.right = right;
            this.orientation = orientation;
            if (parent != null) {
                double xmin = parent.rect.xmin();
                double xmax = parent.rect.xmax();
                double ymin = parent.rect.ymin();
                double ymax = parent.rect.ymax();
                int cmp = parent.compareTo(point);
                if (orientation == Orientation.VERTICAL) {
                    if (cmp > 0)
                        ymax = parent.point.y();
                    else
                        ymin = parent.point.y();
                } else {
                    if (cmp > 0)
                        xmax = parent.point.x();
                    else
                        xmin = parent.point.x();
                }
                rect = new RectHV(xmin, ymin, xmax, ymax);
            } else
                rect = new RectHV(0, 0, 1, 1);
        }

        @Override
        public int compareTo(Point2D p) {
            return orientation == Orientation.VERTICAL ?
                   Double.compare(point.x(), p.x()) :
                   Double.compare(point.y(), p.y());
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    /**
     * Returns whether the set is empty or not.
     *
     * @return whether the set is empty or not
     */
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (!contains(p))
            root = insert(root, p, Orientation.VERTICAL, null);
    }

    private Node insert(Node node, Point2D p, Orientation orientation, Node parent) {
        if (node == null) {
            ++size;
            node = new Node(p, null, null, orientation, parent);
        } else {
            if (node.compareTo(p) > 0)
                node.left = insert(node.left, p, orientation.flip(), node);
            else
                node.right = insert(node.right, p, orientation.flip(), node);
        }
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return contains(root, p);
    }

    private static boolean contains(Node node, Point2D p) {
        boolean res;
        if (node == null)
            res = false;
        else if (p.equals(node.point))
            res = true;
        else
            res = contains(node.compareTo(p) > 0 ? node.left : node.right, p);
        return res;
    }

    // draw all points to standard draw
    public void draw() {
        // TODO
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        List<Point2D> points = new LinkedList<>();
        range(root, points, rect);
        return points;
    }

    private static void range(Node node, List<Point2D> points, RectHV rect) {
        if (node != null && rect.intersects(node.rect)) {
            if (rect.contains(node.point))
                points.add(node.point);
            range(node.left, points, rect);
            range(node.right, points, rect);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        Point2D res = null;
        if (!isEmpty()) {
            nearestPoint = root.point;
            shortestDistance = Double.POSITIVE_INFINITY;
            nearest(root, p);
            res = nearestPoint;
        }
        return res;
    }

    private void nearest(Node node, Point2D p) {
        if (node != null && node.rect.distanceSquaredTo(p) < shortestDistance) {
            double distance = p.distanceSquaredTo(node.point);
            if (distance < shortestDistance) {
                nearestPoint = node.point;
                shortestDistance = distance;
            }
            if (node.compareTo(p) > 0) {
                nearest(node.left, p);
                nearest(node.right, p);
            } else {
                nearest(node.right, p);
                nearest(node.left, p);
            }
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();
    }
}
