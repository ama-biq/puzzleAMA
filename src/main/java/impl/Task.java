package impl;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class run the threads that solve the puzzle.
 */

public class Task implements Runnable {

    private List<PuzzleElementDefinition> puzzleElements;
    private int row;
    private boolean rotate;
    private File file;
    private AtomicBoolean solved;

    Task(List<PuzzleElementDefinition> puzzleElements, int row, boolean rotate, File file, AtomicBoolean solved) {
        this.puzzleElements = puzzleElements;
        this.row = row;
        this.rotate = rotate;
        this.file = file;
        this.solved = solved;
    }

    @Override
    public void run() {
        System.out.println("going to solve puzzle: " + row + "X" + puzzleElements.size() / row);
        new Solver(solved).solve(puzzleElements, row, rotate, file);
    }
}
