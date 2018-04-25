package impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Task implements Runnable{
    private List<PuzzleElementDefinition> elementsList = new ArrayList<>();
    private int row;
    AtomicBoolean flag;

    public Task(List<PuzzleElementDefinition> elementsList, int row) {

        this.elementsList = elementsList;
        this.row = row;
    }


    public Task(List<PuzzleElementDefinition> elementsList, int row, AtomicBoolean flag) {

        this.elementsList = elementsList;
        this.row = row;
        this.flag = flag;
    }
    @Override
    public void run() {
        System.out.println("elementList " + elementsList.size());
        System.out.println("flag " + flag.get());
        new Solver().solve(elementsList, row, flag);
    }
}
