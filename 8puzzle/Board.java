/* *****************************************************************************
 *  Name:           Board
 *  Date:           2020-07-16
 *  Description:    An immutable data type that models an n-by-n board with
 *                  sliding tiles.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Board {

    private final int[][] grid;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        n = tiles.length;
        grid = deepCopy(tiles, n);
    }

    // string representation of this board
    public String toString()
    {
        StringBuilder sb;

        sb = new StringBuilder();
        sb.append(n).append('\n');
        for (int[] row : grid) {
            for (int tile : row)
                sb.append(String.format("%2d ", tile));
            sb.append('\n');
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension()
    {
        return n;
    }

    // number of tiles out of place
    public int hamming()
    {
        int d, i, j;

        d = 0;
        for (i = 0; i < n; ++i)
            for (j = 0; j < n; ++j)
                if (grid[i][j] != 0 && grid[i][j] != n * i + j + 1)
                    ++d;
        return d;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        int d, i, j;

        d = 0;
        for (i = 0; i < n; ++i)
            for (j = 0; j < n; ++j)
                if (grid[i][j] != 0)
                    d += Math.abs((grid[i][j] - 1) / n - i) +
                         Math.abs((grid[i][j] - 1) % n - j);
        return d;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        int i, j;

        if (y != null && getClass() == y.getClass() && n == ((Board) y).n) {
            for (i = 0; i < n; ++i)
                for (j = 0; j < n; ++j)
                    if (grid[i][j] != ((Board) y).grid[i][j])
                        return false;
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        LinkedList<Board> neighbors;

        neighbors = new LinkedList<>();
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                if (grid[i][j] == 0) {
                    if (i > 0)
                        neighbors.add(swap(i, j, i - 1, j));
                    if (i < n - 1)
                        neighbors.add(swap(i, j, i + 1, j));
                    if (j > 0)
                        neighbors.add(swap(i, j, i, j - 1));
                    if (j < n - 1)
                        neighbors.add(swap(i, j, i, j + 1));
                    break;
                }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        int i, j;

        for (i = 0; i < n; ++i)
            for (j = 0; j < n - 1; ++j)
                if (grid[i][j] != 0 && grid[i][j + 1] != 0)
                    return swap(i, j, i, j + 1);
        return null;
    }

    private Board swap(int i1, int j1, int i2, int j2)
    {
        int[][] swappedTiles;
        int tmp;

        swappedTiles = deepCopy(grid, n);
        tmp = swappedTiles[i1][j1];
        swappedTiles[i1][j1] = swappedTiles[i2][j2];
        swappedTiles[i2][j2] = tmp;
        return new Board(swappedTiles);
    }

    private int[][] deepCopy(int[][] array, int dim)
    {
        int[][] copy;
        int i, j;

        copy = new int[dim][dim];
        for (i = 0; i < dim; ++i)
            for (j = 0; j < dim; ++j)
                copy[i][j] = array[i][j];
        return copy;
    }

    // unit testing (not graded)
    public static void main(String[] args)
    {
        Board board;
        int[][] tiles;

        tiles = new int[][] {
            { 0, 1, 3 },
            { 4, 2, 5 },
            { 7, 8, 6 }
        };
        board = new Board(tiles);
        StdOut.println(board.toString());
        StdOut.println(board.dimension());
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
    }
}
