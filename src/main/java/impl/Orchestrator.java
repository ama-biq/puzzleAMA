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

    public void orchestrateThePuzzle(String receivedFilePath) throws Exception {
        File inputFile = new File(receivedFilePath);
         Solver solver = new Solver();
         List<PuzzleElementDefinition> list = solver.checkTheInputFile(inputFile);
        if (list.isEmpty()){
            solver.writeErrorsToTheOutPutFile();
        } else if (solver.isSumOfParallelEdgesZero(list)) {
            List<Integer> rowList = solver.getSolverRows(list);
            ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(0, 1, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
            AtomicBoolean atomicFlag = new AtomicBoolean(false);
                for (Integer row : rowList) {
                    while (atomicFlag.equals(false)) {
                        poolExecutor.execute(new Task(list, row, atomicFlag));
                    }
                    System.out.println("atomicFlag " + atomicFlag.get());
            }
        }
        if (solver.getSolutionList().isEmpty())
        {
            solver.writeErrorsToTheOutPutFile();
        }else{
            solver.writeSolutionToTheOutPutFile();
        }

    }


    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String inPath = sc.next();
        Orchestrator orchestrator = new Orchestrator();
        orchestrator.orchestrateThePuzzle(inPath);

    }
}
