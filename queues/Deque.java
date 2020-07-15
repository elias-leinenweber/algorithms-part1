import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private final Item item;
        private Node prev;
        private Node next;

        public Node(Item item, Node prev, Node next)
        {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;

        @Override
        public boolean hasNext()
        {
            return current != null;
        }

        @Override
        public Item next()
        {
            Item item;

            if (!hasNext())
                throw new NoSuchElementException();
            item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque()
    {
        head = null;
        tail = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size()
    {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        head = new Node(item, null, head);
        if (isEmpty())
            tail = head;
        else
            head.next.prev = head;
        ++size;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        tail = new Node(item, tail, null);
        if (isEmpty())
            head = tail;
        else
            tail.prev.next = tail;
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        Item first;

        if (isEmpty())
            throw new NoSuchElementException();
        first = head.item;
        --size;
        if (isEmpty()) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        return first;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        Item last;

        if (isEmpty())
            throw new NoSuchElementException();
        last = tail.item;
        --size;
        if (isEmpty()) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        return last;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<String> deque;
        String s;

        deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(deque.removeLast());
            else
                deque.addFirst(s);
        }
    }

}
