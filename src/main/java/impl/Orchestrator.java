package impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Orchestrator {
    static AtomicBoolean isSolved = new AtomicBoolean(false);

    public void orchestrateThePuzzle(String receivedFilePath) throws Exception {
        File inputFile = new File(receivedFilePath);
        Solver solver = new Solver();
        List<PuzzleElementDefinition> list = solver.checkTheInputFile(inputFile);
        ThreadPoolExecutor threadPool;
        int maxPoolSize = 5;

        if (list.isEmpty()) {
            solver.writeErrorsToTheOutPutFile();
        } else if (solver.isSumOfParallelEdgesZero(list)) {
            List<Integer> rowList = solver.getSolverRows(list);
            if (rowList.size() < maxPoolSize) {
                maxPoolSize = rowList.size();
            }
            int rowCount = rowList.size();

            threadPool = new ThreadPoolExecutor(0, maxPoolSize, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(15));

            while (!isSolved.get() && rowCount > 0) {
                for (Integer row : rowList) {
                    threadPool.execute(new Task(list, row));
                    --rowCount;
                }
            }
            while (threadPool.getActiveCount()!=0){
                Thread.sleep(1000);
            }
            System.out.println("isSolved: " + isSolved.get());
        }
    }


    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String inPath = sc.next();
        Orchestrator orchestrator = new Orchestrator();
        orchestrator.orchestrateThePuzzle(inPath);

    }
}
