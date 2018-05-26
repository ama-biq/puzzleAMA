package impl;

import file.CmdPuzzleParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Orchestrating the whole procces.
 * Using ThreadPoolExecutor to sync the threads that try to solve the puzzle.
 */

public class Orchestrator {
    private File outputFile;

    public static void main(String[] args) throws Exception {
        CmdPuzzleParser cmdPuzzleParser = new CmdPuzzleParser();
        cmdPuzzleParser.menu(args);
        new Orchestrator().orchestrateThePuzzle(cmdPuzzleParser);
    }

    /**
     * This function
     *
     * @param cmdPuzzleParser
     * @throws Exception
     */
    void orchestrateThePuzzle(CmdPuzzleParser cmdPuzzleParser) throws Exception {
        // atomic boolean solved using to check if one of threads solve the puzzle.
        AtomicBoolean solved = new AtomicBoolean(false);
        Solver solver = new Solver(solved);
        File inputFile = new File(cmdPuzzleParser.getFileInputPath());
        int maxPoolSize = cmdPuzzleParser.getThreadAmount();
        boolean rotate = cmdPuzzleParser.isRotate();

        List<PuzzleElementDefinition> puzzlePiecesList = solver.checkTheInputFile(inputFile);
        outputFile = new File(cmdPuzzleParser.getFileOutputPath());
        if (puzzlePiecesList.isEmpty()) {
            solver.writeErrorsToTheOutPutFile(outputFile);
        } else {
            List<Integer> boardsList = getSolutionBoardsList(solver, puzzlePiecesList, rotate);
            if (!boardsList.isEmpty()) {
                solvePuzzleInMultiThread(solved, puzzlePiecesList, maxPoolSize, rotate, outputFile, boardsList, boardsList.size());
            }
        }
    }

    void orchestrateThePuzzleFromJson(CmdPuzzleParser cmdPuzzleParser) throws Exception {
        // atomic boolean solved using to check if one of threads solve the puzzle.
        AtomicBoolean solved = new AtomicBoolean(false);
        Solver solver = new Solver(solved);
        File inputFile = new File(cmdPuzzleParser.getFileInputPath());
        int maxPoolSize = cmdPuzzleParser.getThreadAmount();
        boolean rotate = cmdPuzzleParser.isRotate();

        List<PuzzleElementDefinition> puzzlePiecesList = solver.checkTheInputFile(inputFile);
        outputFile = new File(cmdPuzzleParser.getFileOutputPath());
        if (puzzlePiecesList.isEmpty()) {
            solver.writeErrorsToTheOutPutFile(outputFile);
        } else {
            List<Integer> boardsList = getSolutionBoardsList(solver, puzzlePiecesList, rotate);
            if (!boardsList.isEmpty()) {
                solvePuzzleInMultiThread(solved, puzzlePiecesList, maxPoolSize, rotate, outputFile, boardsList, boardsList.size());
            }
        }
    }

    private List<Integer> getSolutionBoardsList(Solver solver, List<PuzzleElementDefinition> puzzlePiecesList, boolean rotate) throws IOException {
        List<Integer> boardsList = new ArrayList<>();
        if (solver.isSumOfParallelEdgesZero(puzzlePiecesList)) {
            List<Integer> availableBoardsList = solver.getSolverRows(puzzlePiecesList);
            for (Integer row : availableBoardsList) {
                if (solver.validateStraightEdges(puzzlePiecesList, row, rotate)) {
                    boardsList.add(row);
                }
            }
            if (boardsList.isEmpty()) {
                EventHandler.addEventToList(EventHandler.WRONG_STRAIGHT_EDGES);
                solver.writeErrorsToTheOutPutFile(outputFile);
            }
        } else {
            solver.writeErrorsToTheOutPutFile(outputFile);
        }
        return boardsList;
    }

    private void solvePuzzleInMultiThread(AtomicBoolean solved, List<PuzzleElementDefinition> list, int maxPoolSize, boolean rotate, File outputFile, List<Integer> boardsList, int boardCount) throws InterruptedException {
        // check and set the num of threads equals to amount of number of rows
        if (boardsList.size() < maxPoolSize) {
            maxPoolSize = boardsList.size();
        }
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(maxPoolSize, maxPoolSize, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(15));
        while (!solved.get() && boardCount > 0) {
            for (Integer board : boardsList) {
                //execute the pool with Task object, this object get list of elements
                //number of row is rotate available, the path to output file and atomic boolean(solved)
                threadPool.execute(new Task(list, board, rotate, outputFile, solved));
                --boardCount;
            }
        }
        while (true) {
            if (waitTillAllThreadsShutdown(solved, threadPool)) {
                break;
            }
        }
    }

    private boolean waitTillAllThreadsShutdown(AtomicBoolean solved, ThreadPoolExecutor threadPool) throws InterruptedException {
        while (solved.get()) {
            new Solver().getSolution();
            threadPool.shutdown();
            threadPool.awaitTermination(1000, TimeUnit.MILLISECONDS);
            return true;
        }
        return false;
    }

}
