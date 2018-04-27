package impl;

public class PuzzleElementDefinition {
    private int id;

    private int left;
    private int up;
    private int right;
    private int bottom;
    private int rotationAngle = 0;

    public PuzzleElementDefinition() {
    }

    public PuzzleElementDefinition(int left, int up, int right, int bottom) {
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

    public PuzzleElementDefinition(int id, int left, int up, int right, int bottom, int rotationAngle) {
        this.id = id;
        this.left = left;
        this.up = up;
        this.right = right;
        this.bottom = bottom;
        this.rotationAngle = rotationAngle;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public boolean isLeftCornerExistsOnOneRowPuzzle() {
        if (getBottom() == 0 &&
                getLeft() == 0 &&
                getUp() == 0 &&
                getRight() != 0
                ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRightCornerExistsOnOneRowPuzzle() {
        if (getBottom() == 0 &&
                getRight() == 0 &&
                getUp() == 0 &&
                getLeft() != 0
                ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "PuzzleElementDefinition{" +
                "id=" + id +
                ", left=" + left +
                ", up=" + up +
                ", right=" + right +
                ", bottom=" + bottom +
                '}';
    }

    public boolean isTLExistsOnSeveralRowsPuzzle() {
        return (getRight() == 0 && getUp() == 0);
    }

    public boolean isTRExistsOnSeveralRowsPuzzle() {
        return (getLeft() == 0 && getUp() == 0);
    }

    public boolean isBLExistsOnSeveralRowsPuzzle() {
        return (getLeft() == 0 && getBottom() == 0);
    }

    public boolean isBRExistsOnSeveralRowsPazzle() {
        return (getRight() == 0 && getBottom() == 0);
    }

    public boolean isBottomCornerExistsOnOneColumnPuzzle() {
        return (getBottom() == 0 &&
                getRight() == 0 &&
                getLeft() == 0 &&
                getUp() != 0);
    }

    public boolean isTopCornerExistsOnOneColumnPuzzle() {
        return (getLeft() == 0 &&
                getRight() == 0 &&
                getUp() == 0 &&
                getBottom() !=0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuzzleElementDefinition)) return false;

        PuzzleElementDefinition that = (PuzzleElementDefinition) o;

        if (getLeft() != that.getLeft()) return false;
        if (getUp() != that.getUp()) return false;
        if (getRight() != that.getRight()) return false;
        return getBottom() == that.getBottom();
    }

    @Override
    public int hashCode() {
        int result = left;
        result = 31 * result + up;
        result = 31 * result + right;
        result = 31 * result + bottom;
        return result;
    }

}
