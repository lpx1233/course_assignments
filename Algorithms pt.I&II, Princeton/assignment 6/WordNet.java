import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.DirectedCycle;
import java.util.ArrayList;

public class WordNet {
  private ST<String, ArrayList<Integer>> nounST = new ST<String, ArrayList<Integer>>();
  private ArrayList<String> synsets = new ArrayList<String>();
  private int numOfSynsets = 0;
  private Digraph G = null;
  private SAP sap = null;

  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms) {
    In synsetsIn = new In(synsets);
    while (synsetsIn.hasNextLine()) {
      this.numOfSynsets++;
      String[] raw = synsetsIn.readLine().split(",");
      int id = Integer.parseInt(raw[0]);
      this.synsets.add(raw[1]);
      String[] nouns = raw[1].split(" ");
      for (String str : nouns) {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        if (this.nounST.contains(str)) {
          idList = this.nounST.get(str);
        }
        idList.add(id);
        this.nounST.put(str, idList);
      }
    }
    this.G = new Digraph(this.numOfSynsets);
    boolean[] isNotRoot = new boolean[this.numOfSynsets];
    In hypernymsIn = new In(hypernyms);
    while (hypernymsIn.hasNextLine()) {
      String line = hypernymsIn.readLine();
      String[] raw = line.split(",", 2);
      int source = Integer.parseInt(raw[0]);
      isNotRoot[source] = true;
      if (raw.length == 1) continue;
      String[] destination = raw[1].split(",");
      for (String des : destination) {
        this.G.addEdge(source, Integer.parseInt(des));
      }
    }
    DirectedCycle dc = new DirectedCycle(this.G);
    if (dc.hasCycle()) throw new java.lang.IllegalArgumentException();
    int rootCnt = 0;
    for (boolean bl : isNotRoot) {
      if (!bl) rootCnt++;
    }
    if (rootCnt > 1) throw new java.lang.IllegalArgumentException();
    this.sap = new SAP(G);
  }

  // returns all WordNet nouns
  public Iterable<String> nouns() {
    return nounST.keys();
  }

  // is the word a WordNet noun?
  public boolean isNoun(String word) {
    return nounST.contains(word);
  }

  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB) {
    return this.sap.length(this.nounST.get(nounA), this.nounST.get(nounB));
  }

  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB) {
    return this.synsets.get(this.sap.ancestor(this.nounST.get(nounA), this.nounST.get(nounB)));
  }

  // do unit testing of this class
  public static void main(String[] args) {

  }
}
