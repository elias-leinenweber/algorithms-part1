import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private final Item item;
        private final Node previous;
        private final Node next;

        public Node(Item item, Node previous, Node next)
        {
            this.item = item;
            this.previous = previous;
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
            if (current == null)
                throw new NoSuchElementException();
            Item item = current.item;
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
        ++size;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        tail = new Node(item, tail, null);
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        Item first = head.item;
        head = head.next;
        --size;
        return first;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        Item last = tail.item;
        tail = tail.previous;
        --size;
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
        Deque<String> deque = new Deque<>();
        assert(deque.isEmpty());
    }

}