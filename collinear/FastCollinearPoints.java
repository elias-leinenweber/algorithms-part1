/* *****************************************************************************
 *  Name:           FastCollinearPoints
 *  Date:           2020-07-16
 *  Description:    A faster, sorting-based solution.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private static final int N = 4;
    private final ArrayList<LineSegment> ls;
    private Point[] sortedPoints;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)
    {
        Point[] sortedBySlopePoints;
        double slopeA, slopeB;
        int begin, end, i, k;

        if (!checkInputAndSortPoints(points))
            throw new IllegalArgumentException();
        ls = new ArrayList<>();
        if (points.length >= N) {
            for (Point p : sortedPoints) {
                sortedBySlopePoints = sortedPoints.clone();
                Arrays.sort(sortedBySlopePoints, p.slopeOrder());
                k = 2;
                begin = 1;
                end = 1;
                slopeB = p.slopeTo(sortedBySlopePoints[1]);
                for (i = 1; i < sortedBySlopePoints.length - 1; ++i) {
                    slopeA = slopeB;
                    slopeB = p.slopeTo(sortedBySlopePoints[i + 1]);
                    if (Double.compare(slopeA, slopeB) == 0) {
                        ++k;
                        end = i + 1;
                        if (end < sortedBySlopePoints.length - 1)
                            continue;
                    }
                    if (k >= N && p.compareTo(sortedBySlopePoints[begin]) <= 0)
                        ls.add(new LineSegment(p, sortedBySlopePoints[end]));
                    k = 2;
                    begin = end;
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments()
    {
        return ls.size();
    }

    // the line segments
    public LineSegment[] segments()
    {
        return ls.toArray(new LineSegment[numberOfSegments()]);
    }

    private boolean checkInputAndSortPoints(Point[] points)
    {
        int i;

        if (points == null)
            return false;
        for (Point point : points)
            if (point == null)
                return false;
        sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        for (i = 0; i < sortedPoints.length - 1; ++i)
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0)
                return false;
        return true;
    }

    public static void main(String[] args)
    {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
