package impl;

import java.util.List;

public class Task implements Runnable{

    private List<PuzzleElementDefinition> puzzleElements;
    private int row;

    public Task(List<PuzzleElementDefinition> puzzleElements, int row) {
        this.puzzleElements = puzzleElements;
        this.row = row;
    }

    @Override
    public void run() {
        new Solver().solve(puzzleElements, row);
    }
}
