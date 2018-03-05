import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  private class SearchNode implements Comparable<SearchNode>{
    public Board bd = null;
    public int moves = 0;
    public SearchNode prev = null;
    public int priority = 0;
    public SearchNode(Board bd, int moves, SearchNode prev) {
      this.bd = bd;
      this.moves = moves;
      this.prev = prev;
      this.priority = bd.manhattan() + moves;
    }
    @Override
    public int compareTo(SearchNode that) {
      // if (this.priority == that.priority) {
      //   return new Integer(this.bd.manhattan()).compareTo(new Integer(that.bd.manhattan()));
      // }
      return new Integer(this.priority).compareTo(new Integer(that.priority));
    }
  }

  private boolean solvable = false;
  private Stack<Board> solution = null;
  private int moves = -1;

  public Solver(Board initial) {
    // find a solution to the initial board (using the A* algorithm)
    if (initial == null) {
      throw new java.lang.IllegalArgumentException();
    }
    // Parallel A* algorithm
    MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
    MinPQ<SearchNode> pq_pair = new MinPQ<SearchNode>();
    Board twin = initial.twin();
    pq.insert(new SearchNode(initial, 0, null)); // insert initial
    pq_pair.insert(new SearchNode(twin, 0, null));
    while (!pq.min().bd.isGoal() && !pq_pair.min().bd.isGoal()) {
      SearchNode temp = pq.delMin();
      for (Board b : temp.bd.neighbors()) {
        if (temp.prev != null && b.equals(temp.prev.bd)) {
          continue;
        }
        pq.insert(new SearchNode(b, temp.moves + 1, temp));
      }
      temp = pq_pair.delMin();
      for (Board b : temp.bd.neighbors()) {
        if (temp.prev != null && b.equals(temp.prev.bd)) {
          continue;
        }
        pq_pair.insert(new SearchNode(b, temp.moves + 1, temp));
      }
      // StdOut.println("size: " + pq.size() + " " + pq_pair.size() + " moves: " + mvs);
    }
    if (pq.min().bd.isGoal()) {
      SearchNode last = pq.delMin();
      this.solvable = true;
      this.moves = last.moves;
      Stack<Board> sol = new Stack<Board>();
      while (last != null) {
        sol.push(last.bd);
        last = last.prev;
      }
      this.solution = sol;
    }
  }

  public boolean isSolvable() {           // is the initial board solvable?
    return solvable;
  }

  public int moves() {        // min number of moves to solve initial board; -1 if unsolvable
    return moves;
  }

  public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
    return solution;
  }

  public static void main(String[] args) {  // solve a slider puzzle (given below)
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
  }
}
