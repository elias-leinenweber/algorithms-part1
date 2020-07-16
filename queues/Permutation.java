import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args)
    {
        RandomizedQueue<String> rq;
        String s;
        int i, k, n;

        if (args.length == 1) {
            k = Integer.parseInt(args[0]);
            if (k > 0) {
                rq = new RandomizedQueue<>();
                n = 0;
                while (!StdIn.isEmpty()) {
                    ++n;
                    s = StdIn.readString();
                    if (n > k) {
                        if (StdRandom.uniform(n) < k)
                            rq.dequeue();
                        else
                            continue;
                    }
                    rq.enqueue(s);
                }
                for (i = 0; i < k; ++i)
                    StdOut.print(rq.dequeue() + "\n");
            }
        }
    }
}
