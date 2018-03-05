import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
  private WordNet wn = null;
  public Outcast(WordNet wordnet) {
    this.wn = wordnet;
  }

  public String outcast(String[] nouns) {
    int[] d = new int[nouns.length];
    for (int i = 0; i < nouns.length; i++) {
      for (int j = i; j < nouns.length; j++) {
        int dist = this.wn.distance(nouns[i], nouns[j]);
        d[i] += dist;
        if (i != j) d[j] += dist;
      }
    }
    int maxDistance = 0;
    int maxIndex = 0;
    for (int i = 0; i < d.length; i++) {
      if (d[i] > maxDistance) {
        maxDistance = d[i];
        maxIndex = i;
      }
    }
    return nouns[maxIndex];
  }

  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}
