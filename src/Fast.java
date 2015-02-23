import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Fast {

    private static void findCollinearPoints(Point[] points) {

        Set<ArrayList<Point>> results = new HashSet<ArrayList<Point>>();

        Point[] aux = points.clone();

        for (Point p : points) {

            // sort other points by slope wrt to p
            Arrays.sort(aux, p.SLOPE_ORDER);

            // look for 3+ adjacent points with same slope
            // print each line segment as ordered sequence
            // and draw each segment
            for (int i = 0; i < aux.length; i++) {

                Point q = aux[i];

                // short circuit if invoking point
                if (p == q) {
                    continue;
                }

                double pqSlope = p.slopeTo(q);
                ArrayList<Point> collinearPoints = new ArrayList<Point>();
                collinearPoints.add(p); // add invoking point
                while (p.slopeTo(aux[i]) == pqSlope && (i < aux.length - 1)) {
                    collinearPoints.add(aux[i]);
                    i++;
                }

                if (collinearPoints.size() >= 4) {
                    Collections.sort(collinearPoints);
                    results.add(collinearPoints);
                }

            }

        }

        reportLineSegments(results);
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
        for (int i = 0; i < lineSegment.size() - 1; i++) {
            Point p1 = lineSegment.get(i);
            Point p2 = lineSegment.get(i + 1);
            p1.drawTo(p2);
        }
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
