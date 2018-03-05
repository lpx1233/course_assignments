import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
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

  public FastCollinearPoints(Point[] points) {
    // finds all line segments containing 4 or more points
    if (points == null) {
      throw new IllegalArgumentException();
    }
    for (int k = 0; k < points.length; k++) {
      // copy array
      Point p = points[k];
      if (p == null) {
        throw new IllegalArgumentException();
      }
      Point[] temPs = new Point[points.length - 1];
      for (int i = 0; i < points.length; i++) {
        if (points[i] == null) {
          throw new IllegalArgumentException();
        }
        if (i < k) {
          temPs[i] = points[i];
        } else if (i > k) {
          temPs[i - 1] = points[i];
        }
      }
      // sort the array
      Arrays.sort(temPs, p.slopeOrder());
      // check if 3 have same slopes
      int i = 0;
      while (i<temPs.length) {
        if (p.compareTo(temPs[i]) == 0) {
          throw new IllegalArgumentException();
        }
        int start = i;
        double slope = p.slopeTo(temPs[i]);
        i++;
        if (i == temPs.length) {
          break;
        } else if (p.compareTo(temPs[i]) == 0) {
          throw new IllegalArgumentException();
        }
        while (p.slopeTo(temPs[i]) == slope) {
          i++;
          if (i == temPs.length) {
            break;
          }
          if (p.compareTo(temPs[i]) == 0) {
            throw new IllegalArgumentException();
          }
        }
        if ((i - start) < 3) {
          continue;
        }
        // Sort
        Point[] forSort = new Point[i - start + 1];
        for (int index = 0; index < (i - start); index++) {
          forSort[index] = temPs[index + start];
        }
        forSort[forSort.length - 1] = p;
        Arrays.sort(forSort);
        if (p.compareTo(forSort[0]) != 0) {
          continue;
        }
        // Add
        LineSegment ls = new LineSegment(forSort[0], forSort[forSort.length - 1]);
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
  public int numberOfSegments() {                  // the number of line segments
    return numSeg;
  }
  public LineSegment[] segments() {                // the line segments
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
