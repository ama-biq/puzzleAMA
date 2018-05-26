package client;

import java.util.Arrays;

public class Piece {
    private int Id;
    private int [] Piece = new int[4];

    public Piece(int id, int left, int up, int right, int bottom) {
        Id = id;
        Piece[0] = left;
        Piece[1] = up;
        Piece[2] = right;
        Piece[3] = bottom;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "Id=" + Id +
                ", Piece=" + Arrays.toString(Piece) +
                '}';
    }
}
