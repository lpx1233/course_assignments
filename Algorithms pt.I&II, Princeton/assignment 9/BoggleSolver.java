import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
  private TST<Boolean> tset = new TST<Boolean>();
  // Initializes the data structure using the given array of strings as the dictionary.
  // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
  public BoggleSolver(String[] dictionary) {
    for (String str : dictionary) {
      tset.put(str, true);
    }
  }
  // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public Iterable<String> getAllValidWords(BoggleBoard board) {
    TrieSET validWords = new TrieSET();
    for (int i=0; i<board.rows(); i++) {
      for (int j=0; j<board.cols(); j++) {
        validWords = dfs(new Pos(i, j), "", new boolean[board.rows()][board.cols()], board, validWords);
      }
    }
    return validWords;
  }

  private class Pos {
    private int row = 0;
    private int col = 0;
    public Pos(int row, int col) {
      this.row = row;
      this.col = col;
    }
    public int row() {
      return this.row;
    }
    public int col() {
      return this.col;
    }
  }

  private TrieSET dfs(Pos pos, String prefix, boolean[][] visited, BoggleBoard board, TrieSET validWords) {
    // visit this position
    visited[pos.row()][pos.col()] = true;
    // examine the string
    String newPrefix = prefix + board.getLetter(pos.row(), pos.col());
    if (board.getLetter(pos.row(), pos.col()) == 'Q') newPrefix = newPrefix + 'U';
    if (tset.contains(newPrefix) && scoreOf(newPrefix) > 0) validWords.add(newPrefix);
    if (!tset.keysWithPrefix(newPrefix).iterator().hasNext()) return validWords;
    // visit adj positions
    for (int i=pos.row()-1; i<=pos.row()+1; i++) {
      for (int j=pos.col()-1; j<=pos.col()+1; j++) {
        if (i >= 0 && i < board.rows() && j >= 0 && j < board.cols() && !visited[i][j]) {
          boolean[][] visitedCopied = new boolean[board.rows()][];
          for(int row=0; row<board.rows(); row++) visitedCopied[row] = visited[row].clone();
          validWords = dfs(new Pos(i, j), newPrefix, visitedCopied, board, validWords);
        }
      }
    }
    // visited[pos.row()][pos.col()] = false;
    return validWords;
  }

  // Returns the score of the given word if it is in the dictionary, zero otherwise.
  // (You can assume the word contains only the uppercase letters A through Z.)
  public int scoreOf(String word) {
    int length = word.length();
    int score = 0;
    if(length <= 2) score = 0;
    if(length == 3 || length == 4) score = 1;
    if(length == 5) score = 2;
    if(length == 6) score = 3;
    if(length == 7) score = 5;
    if(length >= 8) score = 11;
    if (!tset.contains(word)) score = 0;
    return score;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    for (String word : solver.getAllValidWords(board)) {
      StdOut.println(word);
      score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
  }
}
