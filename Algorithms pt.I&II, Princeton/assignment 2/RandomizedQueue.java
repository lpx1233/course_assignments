import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private int last = 0;
  private Item[] queue = (Item[]) new Object[2];

  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < last; i++) {
      copy[i] = queue[i];
    }
    queue = copy;
  }

  public RandomizedQueue() {                // construct an empty randomized queue

  }

  public boolean isEmpty() {                // is the queue empty?
    return last == 0;
  }

  public int size() {                       // return the number of items on the queue
    return last;
  }

  public void enqueue(Item item){           // add the item
    if (item == null) {
      throw new java.lang.IllegalArgumentException();
    }
    if (last == queue.length) {
      resize(2 * queue.length);
    }
    queue[last] = item;
    last = last + 1;
  }

  public Item dequeue(){                    // remove and return a random item
    if (last == 0) {
      throw new java.util.NoSuchElementException();
    }
    int i = StdRandom.uniform(last);
    Item item = queue[i];
    queue[i] = queue[last - 1];
    queue[last - 1] = null;
    last = last - 1;
    if ((last > 0) && (last == (queue.length / 4))) {
      resize(queue.length / 2);
    }
    return item;
  }

  public Item sample(){                     // return (but do not remove) a random item
    if (last == 0) {
      throw new java.util.NoSuchElementException();
    }
    int i = StdRandom.uniform(last);
    return queue[i];
  }

  public Iterator<Item> iterator(){         // return an independent iterator over items in random order
      return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {
    private Item[] thisQueue = (Item[]) new Object[last];
    private int current = 0;

    public RandomizedQueueIterator(){
      for (int i = 0;i < last;i++) {
        thisQueue[i] = queue[i];
      }
      StdRandom.shuffle(thisQueue);
    }

    public boolean hasNext() {
      return current != last;
    }

    public Item next() {
      if (!hasNext()) {
        throw new java.util.NoSuchElementException();
      }
      Item item = thisQueue[current];
      current = current + 1;
      return item;
    }

    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
  }
  public static void main(String[] args){   // unit testing (optional)
    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
    for (int i = 0;i < 10;i++) {
      rq.enqueue(i);
    }
    StdOut.println("first iteration:");
    for (int i: rq) {
      StdOut.println(i);
    }
    StdOut.println("second iteration:");
    for (int i: rq) {
      StdOut.println(i);
    }
    StdOut.println("third iteration:");
    for (int i: rq) {
      StdOut.println(i);
    }
  }
}
