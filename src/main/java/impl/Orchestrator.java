package impl;

import file.CmdPuzzleParser;

import java.io.File;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Orchestrator {

    public static void main(String[] args) throws Exception {
        CmdPuzzleParser cmdPuzzleParser = new CmdPuzzleParser();
        cmdPuzzleParser.menu(args);
        new Orchestrator().orchestrateThePuzzle(cmdPuzzleParser);
    }

    /**
     * This function
     * @param cmdPuzzleParser
     * @throws Exception
     */
    void orchestrateThePuzzle(CmdPuzzleParser cmdPuzzleParser) throws Exception {
        // atomic boolean solved using to check if one of threads solve the puzzle.
        AtomicBoolean solved = new AtomicBoolean(false);
        Solver solver = new Solver(solved);

        File inputFile = new File(cmdPuzzleParser.getFileInputPath());
        List<PuzzleElementDefinition> list = solver.checkTheInputFile(inputFile);
        int maxPoolSize = cmdPuzzleParser.getThreadAmount();
        boolean rotate = cmdPuzzleParser.isRotate();

        File outputFile = new File(cmdPuzzleParser.getFileOutputPath());
        if (list.isEmpty()) {
            solver.writeErrorsToTheOutPutFile();
        } else if (solver.isSumOfParallelEdgesZero(list)) {
            List<Integer> rowList = solver.getSolverRows(list);
            // check and set the num of threads equals to amount of number of rows
            if (rowList.size() < maxPoolSize) {
                maxPoolSize = rowList.size();
            }
            int rowCount = rowList.size();
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(maxPoolSize, maxPoolSize, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(15));
            while (!solved.get() && rowCount > 0) {
                for (Integer row : rowList) {
                    //execute the pool with Task object, this object get list of elements
                    //number of row ??? is rotate availeble, thr path to outputfile and atomic boolean(solved)
                    threadPool.execute(new Task(list, row, rotate, outputFile, solved));
                    --rowCount;
                }
            }
            while (true) {
                if(waitTillAllThreadsShutdown(solved, threadPool)){
                    break;
                }
            }
        }
    }

    private boolean waitTillAllThreadsShutdown(AtomicBoolean solved, ThreadPoolExecutor threadPool) throws InterruptedException {
        while (solved.get()) {
            threadPool.shutdown();
            threadPool.awaitTermination(1000, TimeUnit.MILLISECONDS);
            return true;
        }
        return false;
    }

}
