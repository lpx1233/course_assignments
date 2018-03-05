import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  private class Node {
    private Item item = null;
    private Node next = null;
    private Node previous = null;
  }

  private Node first = null;
  private Node last = null;
  private int size = 0;

  public Deque() {                                 // construct an empty deque

  }

  public boolean isEmpty() {                       // is the deque empty?
    return first == null;
  }

  public int size() {                              // return the number of items on the deque
    return size;
  }

  public void addFirst(Item item) {                // add the item to the front
    if (item == null) {
      throw new java.lang.IllegalArgumentException();
    }
    size = size + 1;
    if (first == null) {
      first = new Node();
      first.item = item;
      last = first;
    } else {
      Node oldFirst = first;
      first = new Node();
      first.item = item;
      first.next = oldFirst;
      oldFirst.previous = first;
    }
  }

  public void addLast(Item item) {                // add the item to the end
    if (item == null) {
      throw new java.lang.IllegalArgumentException();
    }
    size = size + 1;
    if (last == null) {
      last = new Node();
      last.item = item;
      first = last;
    } else {
      Node oldLast = last;
      last = new Node();
      last.item = item;
      oldLast.next = last;
      last.previous = oldLast;
    }
  }

  public Item removeFirst() {                     // remove and return the item from the front
    if (first == null) {
      throw new java.util.NoSuchElementException();
    }
    size = size - 1;
    Item item = null;
    item = first.item;
    if (first == last) {
      first = null;
      last = null;
    } else {
      first = first.next;
      first.previous = null;
    }
    return item;
  }

  public Item removeLast() {                      // remove and return the item from the end
    if (last == null) {
      throw new java.util.NoSuchElementException();
    }
    size = size - 1;
    Item item = null;
    item = last.item;
    if (last == first) {
      last = null;
      first = null;
    } else {
      last = last.previous;
      last.next = null;
    }
    return item;
  }

  public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
    return new java.util.Iterator<Item>() {
      private Node current = first;

      public boolean hasNext() {
        return current != null;
      }

      public Item next() {
        if (!hasNext()) {
          throw new java.util.NoSuchElementException();
        }
        Item item = current.item;
        current = current.next;
        return item;
      }

      public void remove() {
        throw new java.lang.UnsupportedOperationException();
      }
    };
  }

  public static void main(String[] args) {  // unit testing (optional)

  }
}
