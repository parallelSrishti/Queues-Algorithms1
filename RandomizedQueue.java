/* *****************************************************************************
 *  Name: Srishti Singh
 *  Date: 01/01/21
 *  Description: A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random among items in the data structure. Create a generic data type RandomizedQueue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int len;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        len = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return len == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return len;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        items[len++] = item;
        if (len == items.length)
            resize(2 * items.length);
        swapItem();
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = this.items[--len];
        if (len > 0 && len == this.items.length / 4) {
            resize(this.items.length / 2);
        }
        this.items[len] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        return items[StdRandom.uniform(len)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int count;
        private Item[] copy;

        private RandomIterator() {
            count = 0;
            copy = (Item[]) new Object[len];
            for (int i = 0; i < len; i++) {
                copy[i] = items[i];
            }
            StdRandom.shuffle(copy);
        }

        public boolean hasNext() {
            return count != len;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return copy[count++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // private method to resize array
    private void resize(int cap) {
        Item[] temp = (Item[]) new Object[cap];
        for (int i = 0; i < len; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    // swaps items
    private void swapItem() {
        int i = StdRandom.uniform(len);
        Item temp = items[i];
        items[i] = items[len - 1];
        items[len - 1] = temp;
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(rq.dequeue());
            else
                rq.enqueue(s);
        }

    }
}
