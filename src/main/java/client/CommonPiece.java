package client;

public class CommonPiece {
    int[] arrPiece=new int[4];

    public CommonPiece(int left, int up, int right, int bottom) {
        arrPiece[0] = left;
        arrPiece[1] = up;
        arrPiece[2] = right;
        arrPiece[3] = bottom;

    }
}
