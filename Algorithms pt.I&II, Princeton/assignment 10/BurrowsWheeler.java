import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import java.util.Arrays;


public class BurrowsWheeler {
  private static char charAt(String s, int d) {
    return s.charAt(d % s.length());
  }

  // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
  public static void transform() {
    String str = BinaryStdIn.readString();
    CircularSuffixArray csa = new CircularSuffixArray(str);
    int first = 0;
    for (int i=0; i<csa.length(); i++)
      if (csa.index(i) == 0) first = i;

    BinaryStdOut.write(first);
    for (int i=0; i<csa.length(); i++)
      BinaryStdOut.write(charAt(str, csa.index(i) + str.length() - 1));
    BinaryStdOut.close();
  }

  // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
  public static void inverseTransform() {
    int first = BinaryStdIn.readInt();
    String t = BinaryStdIn.readString();
    char[] head = t.toCharArray();
    Arrays.sort(head);
    // Construct Next
    int[] next = new int[t.length()];
    int R = 256;
    int[] count = new int[R+1];
    for (int i = 0; i < t.length(); i++)
      count[t.charAt(i)+1]++;
    for (int r = 0; r < R; r++)
      count[r+1] += count[r];
    for (int i = 0; i < t.length(); i++) {
      int idx = count[t.charAt(i)]++;
      next[idx] = i;
    }
    // Inverting Message
    for (int i = first, j = 0; j < t.length(); j++) {
      BinaryStdOut.write(head[i]);
      i = next[i];
    }
    BinaryStdOut.close();
  }

  // if args[0] is '-', apply Burrows-Wheeler transform
  // if args[0] is '+', apply Burrows-Wheeler inverse transform
  public static void main(String[] args) {
    if (args[0].equals("-")) transform();
    else if (args[0].equals("+")) inverseTransform();
  }
}
