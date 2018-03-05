import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.SET;

public class KdTree {
  private static class Node {
    private Point2D p = null;      // the point
    private RectHV rect = null;    // the axis-aligned rectangle corresponding to this node
    private Node lb = null;        // the left/bottom subtree
    private Node rt = null;        // the right/top subtree
    private boolean isVertical = true;
    private int size = 0;
    private Node(Point2D p, RectHV rect, boolean isVertical, int size) {
      this.p = p;
      this.rect = rect;
      this.isVertical = isVertical;
      this.size = size;
    }
  }

  private Node root = null;
  private int size = 0;

  public KdTree() {
    // construct an empty set of points
  }

  public boolean isEmpty() {
    // is the set empty?
    return this.root == null;
  }

  public int size() {
    // number of points in the set
    return size(root);
  }
  private int size(Node x) {
    if (x == null) return 0;
    else return x.size;
  }

  public void insert(Point2D p) {
    // add the point to the set (if it is not already in the set)
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    root = insert(root, p, new RectHV(0, 0, 1, 1), true);
  }

  private Node insert(Node x, Point2D p, RectHV rect, boolean isVertical) {
    if (x == null) {
      return new Node(p, rect, isVertical, 1);
    }
    if (x.isVertical) {
      if (p.x() < x.p.x()) {
        rect = new RectHV(rect.xmin(), rect.ymin(), x.p.x(), rect.ymax());
        x.lb = insert(x.lb, p, rect, !x.isVertical);
      } else if (!x.p.equals(p)) {
        rect = new RectHV(x.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
        x.rt = insert(x.rt, p, rect, !x.isVertical);
      } else {
        x.p = p;
      }
    } else {
      if (p.y() < x.p.y()) {
        rect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.p.y());
        x.lb = insert(x.lb, p, rect, !x.isVertical);
      } else if (!x.p.equals(p)) {
        rect = new RectHV(rect.xmin(), x.p.y(), rect.xmax(), rect.ymax());
        x.rt = insert(x.rt, p, rect, !x.isVertical);
      } else {
        x.p = p;
      }
    }
    x.size = 1 + size(x.lb) + size(x.rt);
    return x;
  }

  public boolean contains(Point2D p) {
    // does the set contain point p?
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    return contains(root, p);
  }

  private boolean contains(Node x, Point2D p) {
    if (x == null) return false;
    int cmp = 0;
    if (x.isVertical) {
      if (p.x() < x.p.x()) {
        cmp = -1;
      } else if (!x.p.equals(p)) {
        cmp = 1;
      } else {
        cmp = 0;
      }
    } else {
      if (p.y() < x.p.y()) {
        cmp = -1;
      } else if (!x.p.equals(p)) {
        cmp = 1;
      } else {
        cmp = 0;
      }
    }
    if (cmp < 0) {
      return contains(x.lb, p);
    } else if (cmp > 0) {
      return contains(x.rt, p);
    } else {
      return true;
    }
  }

  public void draw() {
    // draw all points to standard draw
    draw(root);
  }
  private void draw(Node x) {
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
    x.p.draw();
    if (x.isVertical) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.setPenRadius();
      StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
    } else {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.setPenRadius();
      StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
    }
    draw(x.lb);
    draw(x.rt);
  }

  public Iterable<Point2D> range(RectHV rect) {
    // all points that are inside the rectangle (or on the boundary)
    if (rect == null) {
      throw new java.lang.IllegalArgumentException();
    }
    SET<Point2D> result = new SET<Point2D>();
    Node x = root;
    while (x != null) {

    }
  }

  public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to point p; null if the set is empty

  }

  public static void main(String[] args) {
    // unit testing of the methods (optional)
  }
}
