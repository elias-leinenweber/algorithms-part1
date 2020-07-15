import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;

        @Override
        public boolean hasNext()
        {
            return items[i] != null;
        }

        @Override
        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            return items[i++];
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        items = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return size;
    }

    // add the item
    public void enqueue(Item item)
    {
        Item tmp;
        int i;

        if (item == null)
            throw new IllegalArgumentException();
        items[size++] = item;
        if (size == items.length)
            resize(2 * items.length);
        i = StdRandom.uniform(size);
        tmp = items[i];
        items[i] = items[size - 1];
        items[size - 1] = tmp;
    }

    // remove and return a random item
    public Item dequeue()
    {
        Item item;

        if (isEmpty())
            throw new NoSuchElementException();
        item = items[--size];
        if (size > 0 && size == items.length / 4)
            resize(items.length / 2);
        items[size] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        return items[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

    private void resize(int capacity)
    {
        Item[] copy;

        copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; ++i)
            copy[i] = items[i];
        items = copy;
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<String> rq;
        String s;

        rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(rq.dequeue());
            else
                rq.enqueue(s);
        }
    }

}
