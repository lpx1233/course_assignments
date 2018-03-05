import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import java.util.Arrays;
import java.util.ArrayList;

public class BaseballElimination {
  private int numTeams = 0;
  private String[] teamName = null;
  private int[] w = null;
  private int[] l = null;
  private int[] r = null;
  private int[][] g = null;

  public BaseballElimination(String filename) {
    // create a baseball division from given filename in format specified below
    In inputStream = new In(filename);
    this.numTeams = inputStream.readInt();
    teamName = new String[this.numTeams];
    w = new int[this.numTeams];
    l = new int[this.numTeams];
    r = new int[this.numTeams];
    g = new int[this.numTeams][this.numTeams];

    for (int i = 0; i < this.numTeams; i++) {
      teamName[i] = inputStream.readString();
      w[i] = inputStream.readInt();
      l[i] = inputStream.readInt();
      r[i] = inputStream.readInt();
      for (int j = 0; j < this.numTeams; j++) {
        g[i][j] = inputStream.readInt();
      }
    }

  }

  public int numberOfTeams() { // number of teams
    return this.numTeams;
  }

  public Iterable<String> teams() { // all teams
    return Arrays.asList(teamName);
  }

  private int findIdByName(String team) {
    int idx = -1;
    for (int i = 0; i < this.numTeams; i++) {
      if (this.teamName[i].equals(team)) {
        idx = i;
        break;
      }
    }
    if (idx == -1) throw new java.lang.IllegalArgumentException();
    return idx;
  }

  public int wins(String team) { // number of wins for given team
    return w[findIdByName(team)];
  }

  public int losses(String team) { // number of losses for given team
    return l[findIdByName(team)];
  }

  public int remaining(String team) { // number of remaining games for given team
    return r[findIdByName(team)];
  }

  public int against(String team1, String team2) {
    // number of remaining games between team1 and team2
    return g[findIdByName(team1)][findIdByName(team2)];
  }

  private int transformIndex(int i, int toBeDeterminted) {
    if (i < toBeDeterminted) return i;
    else return i + 1;
  }

  private ArrayList<String> constructAndTrivialElimination(FlowNetwork fn, int toBeDeterminted) {
    // Construct the flow network and perform trivial elimination
    // return the certificate of trivial elimination
    ArrayList<String> certOfElim = new ArrayList<String>();
    int maxWinOfToBeDet = w[toBeDeterminted] + r[toBeDeterminted];
    int gameIndex = this.numTeams - 1;
    int s = fn.V() - 2;
    int t = fn.V() - 1;
    for (int i = 0; i < this.numTeams - 1; i++) {
      int cap = maxWinOfToBeDet - w[transformIndex(i, toBeDeterminted)];
      if (cap < 0) {
        certOfElim.add(this.teamName[transformIndex(i, toBeDeterminted)]);
        continue;
      }
      else fn.addEdge(new FlowEdge(i, t, cap));
      for (int j = i + 1; j < this.numTeams - 1 ; j++) {
        int games = g[transformIndex(i, toBeDeterminted)][transformIndex(j, toBeDeterminted)];
        fn.addEdge(new FlowEdge(s, gameIndex, games));
        fn.addEdge(new FlowEdge(gameIndex, i, Double.POSITIVE_INFINITY));
        fn.addEdge(new FlowEdge(gameIndex, j, Double.POSITIVE_INFINITY));
        gameIndex++;
      }
    }
    return certOfElim;
  }

  public boolean isEliminated(String team) {
    // is given team eliminated?
    int toBeDeterminted = findIdByName(team);
    // Create flow network & add flow edges
    int V = 2 + this.numTeams - 1 + (this.numTeams - 1) * (this.numTeams - 2) / 2;
    int s = V - 2;
    int t = V - 1;
    FlowNetwork fn = new FlowNetwork(V);
    ArrayList<String> ret = constructAndTrivialElimination(fn, toBeDeterminted);
    if (!ret.isEmpty()) return true;
    // Use FordFulkerson algorithm
    FordFulkerson ff = new FordFulkerson(fn, s, t);
    for (FlowEdge e : fn.adj(s)) {
      if (e.capacity() != e.flow()) return true;
    }
    return false;
  }

  public Iterable<String> certificateOfElimination(String team) {
    // subset R of teams that eliminates given team; null if not eliminated
    int toBeDeterminted = findIdByName(team);
    // Create flow network & add flow edges
    int V = 2 + this.numTeams - 1 + (this.numTeams - 1) * (this.numTeams - 2) / 2;
    int s = V - 2;
    int t = V - 1;
    FlowNetwork fn = new FlowNetwork(V);
    ArrayList<String> certOfElim = constructAndTrivialElimination(fn, toBeDeterminted);
    if (!certOfElim.isEmpty()) return certOfElim;
    // Use FordFulkerson algorithm
    FordFulkerson ff = new FordFulkerson(fn, s, t);
    for (int i = 0; i < this.numTeams - 1; i++) {
      if (ff.inCut(i)) certOfElim.add(this.teamName[transformIndex(i, toBeDeterminted)]);
    }
    if (certOfElim.isEmpty()) return null;
    else return certOfElim;
  }

  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
      StdOut.println(team);

      // if (division.isEliminated(team)) {
      //   StdOut.print(team + " is eliminated by the subset R = { ");
      //   for (String t : division.certificateOfElimination(team)) {
      //     StdOut.print(t + " ");
      //   }
      //   StdOut.println("}");
      // }
      // else {
      //   StdOut.println(team + " is not eliminated");
      // }
    }
    StdOut.println(division.wins("Atlanta"));
  }
}
