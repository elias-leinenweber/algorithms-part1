/* *****************************************************************************
 *  Name:           Solver
 *  Date:           2020-07-16
 *  Description:    An immutable data type that implements A* search to solve
 *                  n-by-n slider puzzles.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode solution;
    private int moves;
    private boolean solvable;

    private static class SearchNode implements Comparable<SearchNode> {

        private final Board board;
        private final int moves;
        private final SearchNode prev;
        private final int manhattan;

        public SearchNode(Board board, int moves, SearchNode prev)
        {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            manhattan = board.manhattan();
        }

        private int priority()
        {
            return manhattan + moves;
        }

        @Override
        public int compareTo(SearchNode that)
        {
            return this.priority() - that.priority();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        MinPQ<SearchNode> pq;
        SearchNode node;

        if (initial == null)
            throw new IllegalArgumentException();

        pq = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));

        while (!pq.isEmpty()) {
            node = pq.delMin();
            moves = node.moves;

            if (node.manhattan == 2 && node.board.twin().isGoal()) {
                solvable = false;
                break;
            }

            if (node.board.isGoal()) {
                solvable = true;
                solution = node;
                break;
            }

            for (Board neighbor : node.board.neighbors())
                if (node.prev == null || !neighbor.equals(node.prev.board))
                    pq.insert(new SearchNode(neighbor, node.moves + 1, node));
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()
    {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
        return isSolvable() ? moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {
        Stack<Board> path;
        SearchNode node;

        if (!isSolvable())
            return null;
        path = new Stack<>();
        node = solution;
        while (node != null) {
            path.push(node.board);
            node = node.prev;
        }
        return path;
    }

    // test client (see below)
    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
