import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Fast {

    private static void findCollinearPoints(Point[] points) {

        Set<ArrayList<Point>> lineSegments = new HashSet<ArrayList<Point>>();

        Point[] aux = points.clone();

        for (Point p : points) {
            // sort other points by slope wrt to p
            Arrays.sort(aux, p.SLOPE_ORDER);

            // aggregate collinear line segments
            lineSegments.addAll(findCollinearPointsFor(p, aux));
        }

        reportLineSegments(lineSegments);
    }

    private static Set<ArrayList<Point>> findCollinearPointsFor(
            Point p, Point[] aux) {
        return findCollinearPointsFor(p, aux, 1, new HashSet<ArrayList<Point>>());
    }

    private static Set<ArrayList<Point>> findCollinearPointsFor(
            Point p, Point[] aux, int i, Set<ArrayList<Point>> results) {

        if (i > aux.length - 1) {
            return results;
        }

        Point q = aux[i];

        // short circuit if invoking point
        if (p == q) {
            return results;
        }

        double pqSlope = p.slopeTo(q);
        ArrayList<Point> collinearPoints = new ArrayList<Point>();
        collinearPoints.add(p); // add invoking point
        int j = i;
        while ((j < aux.length) && p.slopeTo(aux[j]) == pqSlope) {
            collinearPoints.add(aux[j]);
            j++;
        }

        if (collinearPoints.size() >= 4) {
            Collections.sort(collinearPoints);
            results.add(collinearPoints);
        }

        return findCollinearPointsFor(p, aux, j, results);
    }

    private static void reportLineSegments(Set<ArrayList<Point>> lineSegments) {
        for (ArrayList<Point> lineSegment : lineSegments) {
            printLineSegment(lineSegment);
            drawLineSegment(lineSegment);
        }
    }

    private static void printLineSegment(ArrayList<Point> lineSegment) {
        String delimiter = " -> ";
        StringBuilder sb = new StringBuilder();
        Iterator iterator = lineSegment.listIterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(delimiter);
            }
        }

        StdOut.println(sb.toString());
    }

    private static void drawLineSegment(ArrayList<Point> lineSegment) {
        Collections.min(lineSegment).drawTo(Collections.max(lineSegment));
    }

    private static void setupDraw() {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
    }

    private static Point[] readInput(String filename) {
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            points[i] = p;
        }
        return points;
    }

    public static void main(String[] args) {
        setupDraw();
        Point[] points = readInput(args[0]);
        findCollinearPoints(points);
    }

}