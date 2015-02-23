import java.util.*;

public class Fast {

    public Fast(Point[] points) {

        Point[] otherPoints = points.clone();
        Set<LinkedList<Point>> results = new HashSet<LinkedList<Point>>();

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
                LinkedList<Point> collinearPoints = new LinkedList<Point>();
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

        String delimiter = " -> ";
        for (LinkedList<Point> lineSegment : results) {

            StringBuilder sb = new StringBuilder();
            Iterator iterator = lineSegment.listIterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next());
                if (iterator.hasNext()) {
                    sb.append(delimiter);
                }
            }

            StdOut.println(sb.toString());

            drawLineSegment(lineSegment);
        }

    }

    private void drawLineSegment(LinkedList<Point> lineSegment) {
        Iterator<Point> iterator = lineSegment.listIterator();
        List<Point> lineSegmentPoints = new ArrayList<Point>();
        while (iterator.hasNext()) {
            lineSegmentPoints.add(iterator.next());
        }
        for (int i = 0; i < lineSegmentPoints.size() - 1; i++) {
            Point p1 = lineSegmentPoints.get(i);
            Point p2 = lineSegmentPoints.get(i + 1);
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
        new Fast(points);
    }

}
