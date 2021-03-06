import java.util.Arrays;

public class Brute {

    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
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

        for (int i = 0; i < N; i++) {
            Point p = points[i];
            for (int j = i + 1; j < N; j++) {
                Point q = points[j];
                double pqSlope = p.slopeTo(q);
                for (int k = j + 1; k < N; k++) {
                    Point r = points[k];
                    double prSlope = p.slopeTo(r);
                    if (pqSlope == prSlope) {
                        for (int l = k + 1; l < N; l++) {
                            Point s = points[l];
                            double psSlope = p.slopeTo(s);
                            if (pqSlope == psSlope) {
                                Point[] segment = {p, q, r, s};
                                Arrays.sort(segment);
                                segment[0].drawTo(segment[segment.length - 1]);
                                StdOut.printf("%s -> %s -> %s -> %s\n",
                                        segment[0],
                                        segment[1],
                                        segment[2],
                                        segment[3]);
                            }
                        }
                    }
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }

}
