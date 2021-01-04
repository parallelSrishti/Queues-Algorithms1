/* *****************************************************************************
 *  Name: Srishti Singh
 *  Date: 01/01/21
 *  Description: A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure. Create a generic data type Deque
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int len;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        len = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return len == 0;
    }

    // return the number of items on the deque
    public int size() {
        return len;
    }

    // add the item to the first
    public void addFirst(Item item) {
        validateItem(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty())
            last = first;
        else
            oldFirst.prev = first;
        len++;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty())
            first = last;
        else
            oldLast.next = last;
        len++;
    }

    // validate item
    private void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    // check if item may be removed
    private void validateList() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // remove and return the item from the first
    public Item removeFirst() {
        validateList();
        Item item = first.item;
        len--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateList();
        Item item = last.item;
        len--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from first to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // private inner iterator class
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }

    // private inner Node class
    private class Node {
        Item item;
        Node next;
        Node prev;

        private Node() {
            item = null;
            next = null;
            prev = null;
        }

    }

    // unit testing
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(deque.removeLast());
            else
                deque.addFirst(s);
        }
    }
}
