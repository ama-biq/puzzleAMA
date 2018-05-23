package impl;

public class PuzzleElementDefinition {
    private int id;

    private int left;
    private int up;
    private int right;
    private int bottom;
    private int rotationAngle = 0;
    private PuzzleElementDefinition piece;

    PuzzleElementDefinition() {
    }

    PuzzleElementDefinition(int left, int up, int right, int bottom) {
        this.left = left;
        this.up = up;
        this.right = right;
        this.bottom = bottom;
    }

    public PuzzleElementDefinition(int id, int left, int up, int right, int bottom) {
        this.id = id;
        this.left = left;
        this.up = up;
        this.right = right;
        this.bottom = bottom;
    }

    PuzzleElementDefinition(int id, int left, int up, int right, int bottom, int rotationAngle) {
        this.id = id;
        this.left = left;
        this.up = up;
        this.right = right;
        this.bottom = bottom;
        this.rotationAngle = rotationAngle;
    }

    PuzzleElementDefinition(PuzzleElementDefinition piece) {
        this.id = piece.id;
        this.left = piece.left;
        this.up = piece.up;
        this.right = piece.right;
        this.bottom = piece.bottom;
        this.rotationAngle = piece.rotationAngle;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    void setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public Integer getId() {
        return id;
    }

    int getLeft() {
        return left;
    }

    void setLeft(int left) {
        this.left = left;
    }

    int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    int getRight() {
        return right;
    }

    void setRight(int right) {
        this.right = right;
    }

    int getBottom() {
        return bottom;
    }

    void setBottom(int bottom) {
        this.bottom = bottom;
    }

    boolean isLeftCornerExistsOnOneRowPuzzle() {
        return getBottom() == 0 &&
                getLeft() == 0 &&
                getUp() == 0 &&
                getRight() != 0;
    }

    boolean isRightCornerExistsOnOneRowPuzzle() {
        return getBottom() == 0 &&
                getRight() == 0 &&
                getUp() == 0 &&
                getLeft() != 0;
    }

    @Override
    public String toString() {
        return "PuzzleElementDefinition{" +
                "id=" + id +
                ", left=" + left +
                ", up=" + up +
                ", right=" + right +
                ", bottom=" + bottom +
                ", rotationAngle=" + rotationAngle +
                '}';
    }

    boolean isTLExistsOnSeveralRowsPuzzle() {
        return (getRight() == 0 && getUp() == 0);
    }

    boolean isTRExistsOnSeveralRowsPuzzle() {
        return (getLeft() == 0 && getUp() == 0);
    }

    boolean isBLExistsOnSeveralRowsPuzzle() {
        return (getLeft() == 0 && getBottom() == 0);
    }

    boolean isBRExistsOnSeveralRowsPazzle() {
        return (getRight() == 0 && getBottom() == 0);
    }

    boolean isBottomCornerExistsOnOneColumnPuzzle() {
        return (getBottom() == 0 &&
                getRight() == 0 &&
                getLeft() == 0 &&
                getUp() != 0);
    }

    boolean isTopCornerExistsOnOneColumnPuzzle() {
        return (getLeft() == 0 &&
                getRight() == 0 &&
                getUp() == 0 &&
                getBottom() != 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuzzleElementDefinition)) return false;

        PuzzleElementDefinition that = (PuzzleElementDefinition) o;

        if (id != that.id) return false;
        if (left != that.left) return false;
        if (up != that.up) return false;
        if (right != that.right) return false;
        if (bottom != that.bottom) return false;
        return rotationAngle == that.rotationAngle;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + left;
        result = 31 * result + up;
        result = 31 * result + right;
        result = 31 * result + bottom;
        result = 31 * result + rotationAngle;
        return result;
    }
}
