/* *****************************************************************************
 *  Name:           BruteCollinearPoints
 *  Date:           2020-07-16
 *  Description:    A program that examines 4 points at a time and checks
 *                  whether they all lie on the same line segment, returning all
 *                  such line segments.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private static final int N = 4;
    private final ArrayList<LineSegment> ls;
    private Point[] buffer;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        double pq, pr, ps;
        int p, q, r, s;

        if (!checkInputAndInitBuffer(points))
            throw new IllegalArgumentException();
        ls = new ArrayList<>();
        for (p = 0; p <= buffer.length - N; ++p)
            for (q = buffer.length - N + 1; q > p; --q)
                for (r = buffer.length - 2; r > q; --r)
                    for (s = buffer.length - 1; s > r; --s) {
                        pq = buffer[p].slopeTo(buffer[q]);
                        pr = buffer[p].slopeTo(buffer[r]);
                        if (Double.compare(pq, pr) == 0) {
                            ps = buffer[p].slopeTo(buffer[s]);
                            if (Double.compare(pq, ps) == 0)
                                ls.add(new LineSegment(buffer[p], buffer[s]));
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

    private boolean checkInputAndInitBuffer(Point[] points)
    {
        int i;

        if (points == null)
            return false;
        for (Point point : points)
            if (point == null)
                return false;
        buffer = new Point[points.length];
        System.arraycopy(points, 0, buffer, 0, points.length);
        Arrays.sort(buffer);
        for (i = 0; i < points.length - 1; ++i)
            if (buffer[i].compareTo(buffer[i + 1]) == 0)
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
