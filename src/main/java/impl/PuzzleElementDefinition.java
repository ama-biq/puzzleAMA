package impl;

public class PuzzleElementDefinition {
    private int id;

    private int left;
    private int up;
    private int right;
    private int bottom;

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

        if (getId() != that.getId()) return false;
        if (getLeft() != that.getLeft()) return false;
        if (getUp() != that.getUp()) return false;
        if (getRight() != that.getRight()) return false;
        return getBottom() == that.getBottom();
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getLeft();
        result = 31 * result + getUp();
        result = 31 * result + getRight();
        result = 31 * result + getBottom();
        return result;
    }
}
