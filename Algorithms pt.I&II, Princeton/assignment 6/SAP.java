import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
  private Digraph G = null;

  // constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph G) {
    if (G == null) throw new java.lang.IllegalArgumentException();
    this.G = new Digraph(G);
  }

  // length of shortest ancestral path between v and w; -1 if no such path
  public int length(int v, int w) {
    BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.G, v);
    BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(this.G, w);
    int shortestLength = java.lang.Integer.MAX_VALUE;
    for (int x = 0; x < G.V(); x++) {
      if (bfdpV.hasPathTo(x) && bfdpW.hasPathTo(x)) {
        int length = bfdpV.distTo(x) + bfdpW.distTo(x);
        shortestLength = length < shortestLength ? length : shortestLength;
      }
    }
    if(shortestLength == java.lang.Integer.MAX_VALUE) shortestLength = -1;
    return shortestLength;
  }

  // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
  public int ancestor(int v, int w) {
    BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.G, v);
    BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(this.G, w);
    int shortestLength = java.lang.Integer.MAX_VALUE;
    int commonAncestor = -1;
    for (int x = 0; x < G.V(); x++) {
      if (bfdpV.hasPathTo(x) && bfdpW.hasPathTo(x)) {
        int length = bfdpV.distTo(x) + bfdpW.distTo(x);
        if (length < shortestLength) {
          shortestLength = length;
          commonAncestor = x;
        }
      }
    }
    return commonAncestor;
  }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.G, v);
    BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(this.G, w);
    int shortestLength = java.lang.Integer.MAX_VALUE;
    for (int x = 0; x < G.V(); x++) {
      if (bfdpV.hasPathTo(x) && bfdpW.hasPathTo(x)) {
        int length = bfdpV.distTo(x) + bfdpW.distTo(x);
        shortestLength = length < shortestLength ? length : shortestLength;
      }
    }
    if(shortestLength == java.lang.Integer.MAX_VALUE) shortestLength = -1;
    return shortestLength;
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.G, v);
    BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(this.G, w);
    int shortestLength = java.lang.Integer.MAX_VALUE;
    int commonAncestor = -1;
    for (int x = 0; x < G.V(); x++) {
      if (bfdpV.hasPathTo(x) && bfdpW.hasPathTo(x)) {
        int length = bfdpV.distTo(x) + bfdpW.distTo(x);
        if (length < shortestLength) {
          shortestLength = length;
          commonAncestor = x;
        }
      }
    }
    return commonAncestor;
  }

  // do unit testing of this class
  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length   = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}
