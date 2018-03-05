import java.util.LinkedList;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;

public class MoveToFront {
  // apply move-to-front encoding, reading from standard input and writing to standard output
  public static void encode() {
    int R = 256;
    LinkedList<Character> charList = new LinkedList<Character>();
    for (char i = 0; i < R; i++) {
      charList.add(i);
    }
    while (!BinaryStdIn.isEmpty()) {
      char c = BinaryStdIn.readChar();
      int idx = charList.indexOf(c);
      BinaryStdOut.write((char)idx);
      charList.remove(idx);
      charList.addFirst(c);
    }
    BinaryStdOut.close();
  }

  // apply move-to-front decoding, reading from standard input and writing to standard output
  public static void decode() {
    int R = 256;
    LinkedList<Character> charList = new LinkedList<Character>();
    for (char i = 0; i < R; i++) {
      charList.add(i);
    }
    while (!BinaryStdIn.isEmpty()) {
      int idx = BinaryStdIn.readChar();
      char c = charList.get(idx);
      BinaryStdOut.write(c);
      charList.remove(idx);
      charList.addFirst(c);
    }
    BinaryStdOut.close();
  }

  // if args[0] is '-', apply move-to-front encoding
  // if args[0] is '+', apply move-to-front decoding
  public static void main(String[] args) {
    if (args[0].equals("-")) encode();
    else if (args[0].equals("+")) decode();
  }
}
