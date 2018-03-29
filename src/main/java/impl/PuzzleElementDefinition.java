package impl;

public class PuzzleElementDefinition {
    private int id;

    private int left;
    private int up;
    private int right;
    private int bottom;

    public PuzzleElementDefinition() {
    }

    public PuzzleElementDefinition(int id, int left, int up, int right, int bottom) {
        this.id = id;
        this.left = left;
        this.up = up;
        this.right = right;
        this.bottom = bottom;
    }

    public int getId() {
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

    public boolean isLeftCornerOneLinePazzle() {
        if(getBottom()==0 &&
                getLeft()==0 &&
                getUp()==0){
            return true;
        }
        else
        {return false;}
    }

    public boolean isRightCornerOneLinePazzle() {
        if(getBottom()==0 &&
                getRight()==0 &&
                getUp()==0){
            return true;
        }
        else {
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
}
