package impl;

import java.io.File;
import java.util.List;

public class Task implements Runnable{

    private List<PuzzleElementDefinition> puzzleElements;
    private int row;
    private boolean rotate;
    private File file;

    public Task(List<PuzzleElementDefinition> puzzleElements, int row, boolean rotate, File file) {
        this.puzzleElements = puzzleElements;
        this.row = row;
        this.rotate = rotate;
        this.file = file;
    }

    @Override
    public void run() {
        new Solver().solve(puzzleElements, row, rotate, file);
    }
}
