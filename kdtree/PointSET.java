/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * The {@code PointSET} class is a mutable data type...
 *
 * @author Elias Leinenweber
 */
public class PointSET {

    private final TreeSet<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<>();
    }

    /**
     * Returns whether the set is empty or not.
     *
     * @return whether the set is empty or not
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set)
            p.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        List<Point2D> res = new LinkedList<>();
        for (Point2D p : set)
            if (rect.contains(p))
                res.add(p);
        return res;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        Point2D res = null;
        double distance, shortestDistance = Double.POSITIVE_INFINITY;
        for (Point2D q : set) {
            distance = p.distanceSquaredTo(q);
            if (distance < shortestDistance) {
                res = q;
                shortestDistance = distance;
            }
        }
        return res;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
