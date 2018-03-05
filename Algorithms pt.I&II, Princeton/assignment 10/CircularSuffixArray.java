import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
  private int[] index = null;
  private String str = null;
  private static final int R             = 256;   // extended ASCII alphabet size
  private static final int CUTOFF        = 100;   // cutoff to insertion sort

  public CircularSuffixArray(String s) {  // circular suffix array of s
    if (s == null) throw new java.lang.IllegalArgumentException();
    this.str = s;
    this.index = new int[s.length()];
    for (int i=0; i<this.index.length; i++) {
      this.index[i] = i;
    }
    int[] aux = new int[this.index.length];
    sort(aux, 0, this.index.length-1, 0);
  }

  private char charAt(String s, int d) {
    return s.charAt(d % s.length());
  }

  private void sort(int[] aux, int lo, int hi, int d) {
    if (hi <= lo + CUTOFF || d >= 10000) {
      insertion(lo, hi, d);
      return;
    }
    int[] count = new int[R+2];
    for (int i = lo; i <= hi; i++)
      count[charAt(str, index[i] + d) + 2]++;
    for (int r = 0; r < R+1; r++)
      count[r+1] += count[r];
    for (int i = lo; i <= hi; i++)
      aux[count[charAt(str, index[i] + d) + 1]++] = this.index[i];
    for (int i = lo; i <= hi; i++)
      this.index[i] = aux[i - lo];
    for (int r = 0; r < R; r++)
      sort(aux, lo + count[r], lo + count[r+1] - 1, d+1);
  }

  // insertion sort a[lo..hi], starting at dth character
  private void insertion(int lo, int hi, int d) {
    for (int i = lo; i <= hi; i++)
      for (int j = i; j > lo && less(this.index[j], this.index[j-1], d); j--) {
        int temp = this.index[j];
        this.index[j] = this.index[j - 1];
        this.index[j - 1] = temp;
      }
  }

  // is v less than w, starting at character d
  private boolean less(int v, int w, int d) {
    for (int i = d; i < str.length(); i++) {
      if (charAt(this.str, i + v) < charAt(this.str, i + w)) return true;
      if (charAt(this.str, i + v) > charAt(this.str, i + w)) return false;
    }
    return false;
  }

  public int length() {                   // length of s
    return this.index.length;
  }

  public int index(int i) {               // returns index of ith sorted suffix
    if (i < 0 || i >= index.length) throw new java.lang.IllegalArgumentException();
    return index[i];
  }

  public static void main(String[] args) {// unit testing (required)
    String str = args[0];
    CircularSuffixArray csa = new CircularSuffixArray(str);
    for (int i=0; i<csa.length(); i++) {
      StdOut.println(str.substring(csa.index(i)) + str.substring(0, csa.index(i)));
    }
  }
}
