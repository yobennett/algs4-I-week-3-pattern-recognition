import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlopeOrder();

    private final int x;
    private final int y;

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {

        // vertical line
        if (this.x - that.x == 0) {
            // degenerate
            if (this.y - that.y == 0) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }

        // horizontal line
        if (this.y - that.y == 0) {
            return +0.0;
        }

        return ((double)that.y - (double)this.y) / ((double)that.x - (double)this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
            return 0;
        } else if (this.y < that.y || this.y == that.y && this.x < that.x) {
            return -1;
        } else {
            return 1;
        }
    }

    private class BySlopeOrder implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            double slope1 = Point.this.slopeTo(o1);
            double slope2 = Point.this.slopeTo(o2);

            if (slope1 < slope2) {
                return -1;
            } else if (slope2 < slope1) {
                return 1;
            } else {
                return o1.compareTo(o2);
            }
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        Point p1 = new Point(4096, 20992);
        Point p2 = new Point(5120, 20992);

        System.out.println(p1.compareTo(p2));
        System.out.println(p2.compareTo(p1));

        p1.draw();
        p2.draw();
        p1.drawTo(p2);

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}