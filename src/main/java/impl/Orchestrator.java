package impl;

import file.CmdPuzzleParser;

import java.io.File;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Orchestrator {

    static AtomicBoolean isSolved = new AtomicBoolean(false);

    void orchestrateThePuzzle(CmdPuzzleParser cmdPuzzleParser) throws Exception {

        File inputFile = new File(cmdPuzzleParser.getFileInputPath());
        Solver solver = new Solver();
        List<PuzzleElementDefinition> list = solver.checkTheInputFile(inputFile);
        ThreadPoolExecutor threadPool;
        int maxPoolSize = cmdPuzzleParser.getThreadAmount();
        boolean rotate = cmdPuzzleParser.isRotate();
        File outputFile = new File(cmdPuzzleParser.getFileOutputPath());
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
                    threadPool.execute(new Task(list, row, rotate, outputFile));
                    --rowCount;
                }
            }
            while (threadPool.getActiveCount() != 0) {
                Thread.sleep(100);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        CmdPuzzleParser cmdPuzzleParser = new CmdPuzzleParser();
        cmdPuzzleParser.parse(args);
        new Orchestrator().orchestrateThePuzzle(cmdPuzzleParser);
    }

}
