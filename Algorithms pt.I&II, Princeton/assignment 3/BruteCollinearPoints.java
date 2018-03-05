import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private int numSeg = 0;
  private class Node {
    public LineSegment ls = null;
    public Node next = null;
    public Node(LineSegment ls, Node next) {
      this.ls = ls;
      this.next = next;
    }
  }
  private Node first = null;

  public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
    if (points == null) {
     throw new IllegalArgumentException();
    }
    for (int i=0; i<points.length; i++) {
      if (points[i] == null) {
        throw new IllegalArgumentException();
      }
      for (int j=i+1; j<points.length; j++) {
        if (points[j] == null) {
          throw new IllegalArgumentException();
        } else if (points[i].compareTo(points[j]) == 0) {
          throw new IllegalArgumentException();
        }
        for (int k=j+1; k<points.length; k++) {
          if (points[k] == null) {
            throw new IllegalArgumentException();
          } else if ( (points[i].compareTo(points[k]) == 0) ||
                      (points[j].compareTo(points[k]) == 0) ) {
            throw new IllegalArgumentException();
          }
          for (int l=k+1; l<points.length; l++) {
            if (points[l] == null) {
              throw new IllegalArgumentException();
            } else if ( (points[i].compareTo(points[l]) == 0) ||
                        (points[j].compareTo(points[l]) == 0) ||
                        (points[k].compareTo(points[l]) == 0) ) {
              throw new IllegalArgumentException();
            }
            if (points[i].slopeOrder().compare(points[j], points[k]) == 0) {
              if (points[i].slopeOrder().compare(points[k], points[l]) == 0) {
                Point[] temPs = new Point[] {points[i], points[j], points[k], points[l]};
                Arrays.sort(temPs);
                LineSegment ls = new LineSegment(temPs[0], temPs[3]);
                if (first == null) {
                  first = new Node(ls, null);
                } else {
                  Node oldFirst = first;
                  first = new Node(ls, oldFirst);
                }
                numSeg = numSeg + 1;
              }
            }
          }
        }
      }
    }
  }

  public int numberOfSegments() {                // the number of line segments
    return numSeg;
  }

  public LineSegment[] segments() {              // the line segments
    LineSegment[] ls = new LineSegment[numSeg];
    Node nd = first;
    for (int i = 0; i < numSeg; i++) {
      ls[i] = nd.ls;
      nd = nd.next;
    }
    return ls;
  }

  public static void main(String[] args) {
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
