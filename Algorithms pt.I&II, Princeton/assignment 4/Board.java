import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
  private int hammingDistance = 0;
  private int manhattanDistance = 0;
  private int dimension = 0;
  private int[][] blocks = null;

  public Board(int[][] blocks) {    // construct a board from an n-by-n array of blocks
                                    // (where blocks[i][j] = block in row i, column j)
    if (blocks == null) {
      throw new java.lang.IllegalArgumentException();
    }
    dimension = blocks.length;
    this.blocks = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        this.blocks[i][j] = blocks[i][j];
        if ((blocks[i][j] != (i*dimension + j + 1)) && (blocks[i][j] != 0)) {
          hammingDistance++;
          int temp = blocks[i][j] % dimension;
          if (temp == 0) {
            temp = dimension;
          }
          int targetCol = temp - 1;
          int targetRow = (blocks[i][j] - temp) / dimension;
          if (targetRow > i) {
            manhattanDistance = manhattanDistance + targetRow - i;
          } else {
            manhattanDistance = manhattanDistance + i - targetRow;
          }
          if (targetCol > j) {
            manhattanDistance = manhattanDistance + targetCol - j;
          } else {
            manhattanDistance = manhattanDistance + j - targetCol;
          }
        }
      }
    }
  }

  public int dimension() {          // board dimension n
    return dimension;
  }

  public int hamming() {            // number of blocks out of place
    return hammingDistance;
  }

  public int manhattan() {          // sum of Manhattan distances between blocks and goal
    return manhattanDistance;
  }

  public boolean isGoal() {         // is this board the goal board?
    int[][] newBlocks = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        newBlocks[i][j] = (i*dimension + j + 1);
      }
    }
    newBlocks[dimension - 1][dimension - 1] = 0;
    Board goal = new Board(newBlocks);
    return this.equals(goal);
  }

  public Board twin() {          // a board that is obtained by exchanging any pair of blocks
    int num1 = 0;
    int num2 = 0;
    while (num1 == num2) {
      num1 = StdRandom.uniform(1, dimension*dimension - 1);
      num2 = StdRandom.uniform(1, dimension*dimension - 1);
    }
    int[][] newBlocks = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        newBlocks[i][j] = blocks[i][j];
        if (blocks[i][j] == num1) {
          newBlocks[i][j] = num2;
        } else if (blocks[i][j] == num2) {
          newBlocks[i][j] = num1;
        }
      }
    }
    return new Board(newBlocks);
  }

  public boolean equals(Object y) {       // does this board equal y?
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;
    Board that = (Board) y;
    if (this.dimension != that.dimension) {
      return false;
    }
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if (blocks[i][j] != that.blocks[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  public Iterable<Board> neighbors() {    // all neighboring boards
    int row = 0;
    int col = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if (blocks[i][j] == 0) {
          row = i;
          col = j;
        }
      }
    }
    Queue<Board> q = new Queue<Board>();
    if (row != 0) {
      int[][] newBlocks = new int[dimension][dimension];
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          newBlocks[i][j] = blocks[i][j];
          if ((i == (row - 1)) && (j == col)) {
            newBlocks[i][j] = blocks[i + 1][j];
          } else if ((i == row) && (j == col)) {
            newBlocks[i][j] = blocks[i - 1][j];
          }
        }
      }
      q.enqueue(new Board(newBlocks));
    }
    if (row != (dimension - 1)) {
      int[][] newBlocks = new int[dimension][dimension];
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          newBlocks[i][j] = blocks[i][j];
          if ((i == (row + 1)) && (j == col)) {
            newBlocks[i][j] = blocks[i - 1][j];
          } else if ((i == row) && (j == col)) {
            newBlocks[i][j] = blocks[i + 1][j];
          }
        }
      }
      q.enqueue(new Board(newBlocks));
    }
    if (col != 0) {
      int[][] newBlocks = new int[dimension][dimension];
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          newBlocks[i][j] = blocks[i][j];
          if ((i == row) && (j == (col - 1))) {
            newBlocks[i][j] = blocks[i][j + 1];
          } else if ((i == row) && (j == col)) {
            newBlocks[i][j] = blocks[i][j - 1];
          }
        }
      }
      q.enqueue(new Board(newBlocks));
    }
    if (col != (dimension - 1)) {
      int[][] newBlocks = new int[dimension][dimension];
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          newBlocks[i][j] = blocks[i][j];
          if ((i == row) && (j == (col + 1))) {
            newBlocks[i][j] = blocks[i][j - 1];
          } else if ((i == row) && (j == col)) {
            newBlocks[i][j] = blocks[i][j + 1];
          }
        }
      }
      q.enqueue(new Board(newBlocks));
    }
    // Generate Iterable
    return q;
  }

  public String toString() {
    // string representation of this board (in the output format specified below)
    StringBuilder s = new StringBuilder();
    s.append(dimension + "\n");
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        s.append(String.format("%2d ", blocks[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  public static void main(String[] args) { // unit tests (not graded)
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    Board bd = new Board(blocks);
    StdOut.println("manhattan() = " + bd.manhattan());
  }
}
