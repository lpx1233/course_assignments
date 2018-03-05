import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
  private SET<Point2D> set = null;

  public PointSET() {
    // construct an empty set of points
    set = new SET<Point2D>();
  }

  public boolean isEmpty() {
    // is the set empty?
    return set.isEmpty();
  }

  public int size() {
    // number of points in the set
    return set.size();
  }

  public void insert(Point2D p) {
    // add the point to the set (if it is not already in the set)
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    set.add(p);
  }

  public boolean contains(Point2D p) {
    // does the set contain point p?
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    return set.contains(p);
  }

  public void draw() {
    // draw all points to standard draw
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
    for (Point2D p: this.set) {
      p.draw();
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    // all points that are inside the rectangle (or on the boundary)
    if (rect == null) {
      throw new java.lang.IllegalArgumentException();
    }
    SET<Point2D> result = new SET<Point2D>();
    for (Point2D p: this.set) {
      if (rect.contains(p)) {
        result.add(p);
      }
    }
    return result;
  }

  public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to point p; null if the set is empty
    if (rect == null) {
      throw new java.lang.IllegalArgumentException();
    }
    double minDistance = 2;
    Point2D minDistPoint = null;
    for (Point2D pset: this.set) {
      if (p.distanceTo(pset) < minDistance) {
        minDistPoint = pset;
        minDistance = p.distanceTo(pset);
      }
    }
    return minDistPoint;
  }

  public static void main(String[] args) {
    // unit testing of the methods (optional)
  }
}
