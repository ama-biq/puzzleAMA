package impl;

public class PuzzleElementDefinition {
    private int id;
    private int left;
    private int top;
    private int bottom;
    private int right;

    public PuzzleElementDefinition(int id, int left, int top, int bottom, int right) {
        this.id = id;
        this.left = left;
        this.top = top;
        this.bottom = bottom;
        this.right = right;
    }

    public int getId() {
        return id;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getRight() {
        return right;
    }
}
