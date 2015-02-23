import java.util.*;

public class Fast {

    private static void findCollinearPoints(Point[] points) {

        Set<ArrayList<Point>> results = new HashSet<ArrayList<Point>>();

        Point[] otherPoints = points.clone();

        for (Point p : points) {

            // sort other points by slope wrt to p
            Arrays.sort(otherPoints, p.SLOPE_ORDER);

            // look for 3+ adjacent points with same slope
            // print each line segment as ordered sequence
            // and draw each segment
            for (int i = 0; i < otherPoints.length; i++) {

                Point q = otherPoints[i];

                // short circuit if invoking point
                if (p == q) {
                    continue;
                }

                double pqSlope = p.slopeTo(q);
                ArrayList<Point> collinearPoints = new ArrayList<Point>();
                collinearPoints.add(p); // add invoking point
                while (p.slopeTo(otherPoints[i]) == pqSlope && (i < otherPoints.length - 1)) {
                    collinearPoints.add(otherPoints[i]);
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
