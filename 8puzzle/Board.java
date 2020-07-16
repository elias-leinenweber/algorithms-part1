/* *****************************************************************************
 *  Name:           Board
 *  Date:           2020-07-16
 *  Description:    An immutable data type that models an n-by-n board with
 *                  sliding tiles.
 **************************************************************************** */

import java.util.LinkedList;

public class Board {

    private final int[][] tiles;
    private final int n;
    private final int blankRow;
    private final int blankCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        int i, j;

        this.tiles = tiles;
        n = tiles.length;
        j = 0;
        for (i = 0; i < n; ++i)
            for (j = 0; j < n; ++j)
                if (tiles[i][j] == 0)
                    break;
        blankRow = i;
        blankCol = j;
    }

    // string representation of this board
    public String toString()
    {
        String s = n + "\n";

        for (int[] row : tiles) {
            for (int tile : row)
                s += " " + tile + " ";
            s += "\n";
        }
        return s;
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
                if (tiles[i][j] != 0 && tiles[i][j] != n * i + j + 1)
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
                if (tiles[i][j] != 0)
                    d += abs((tiles[i][j] - 1) / n - i) +
                         abs((tiles[i][j] - 1) % n - j);
        return d;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        int i, j;

        if (y != null && getClass() == y.getClass() && n == ((Board) y).n) {
            for (i = 0; i < n; ++i)
                for (j = 0; j < n; ++j)
                    if (tiles[i][j] != ((Board) y).tiles[i][j])
                        return false;
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        LinkedList<Board> neighbors;
        int i, j;

        neighbors = new LinkedList<>();
        for (i = blankRow - 1; i <= blankRow + 1; ++i)
            for (j = blankCol - 1; j <= blankCol + 1; ++j)
                if (i >= 0 && i < n && j >= 0 && j < n)
                    neighbors.add(swap(blankRow, blankCol, i, j));
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {

    }

    private Board swap(int i1, int j1, int i2, int j2)
    {
        int[][] swappedTiles;
        int tmp;

        swappedTiles = tiles.clone();
        tmp = swappedTiles[i1][j1];
        swappedTiles[i1][j1] = swappedTiles[i2][j2];
        swappedTiles[i2][j2] = tmp;
        return new Board(swappedTiles);
    }

    private static Board goal(int n)
    {
        int[][] tiles;
        int i, j, k = 1;

        tiles = new int[n][n];
        for (i = 0; i < n; ++i)
            for (j = 0; j < n; ++j)
                tiles[i][j] = k++;
        tiles[n - 1][n - 1] = 0;
        return new Board(tiles);
    }

    private static int abs(int n)
    {
        return n < 0 ? -n : n;
    }

    // unit testing (not graded)
    public static void main(String[] args)
    { }
}
