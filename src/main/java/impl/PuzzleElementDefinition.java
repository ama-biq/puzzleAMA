package impl;

public class PuzzleElementDefinition {
    private int id;

    private int left;
    private int up;
    private int right;
    private int bottom;

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

    public boolean isLeftCorner() {
        if(getBottom()==0 &&
                getLeft()==0 &&
                getUp()==0){
            return true;
        }
        else
        {return false;}
    }

    public boolean isRightCorner() {
        if(getBottom()==0 &&
                getRight()==0 &&
                getUp()==0){
            return true;
        }
        else {
            return false;
        }
    }
}
