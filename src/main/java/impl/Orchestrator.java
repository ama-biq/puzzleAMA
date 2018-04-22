package impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Orchestrator {

    public void orchestrateThePuzzle(String receivedFilePath) throws Exception {
        File inputFile = new File(receivedFilePath);
         Solver solver = new Solver();
         List<PuzzleElementDefinition> list = solver.checkTheInputFile(inputFile);
        if (list.isEmpty()){
            solver.writeErrorsToTheOutPutFile();
        } else if (solver.isSumOfParallelEdgesZero(list)) {
            List<Integer> rowList = solver.getSolverRows(list);
            List<Integer> possibleNumberOfRowsList = new ArrayList<>();
            for (Integer row : rowList) {
                if(!solver.isMissingCornerElements(row, list)){
                    possibleNumberOfRowsList.add(row);
                }
            }
            if(EventHandler.getEventList().isEmpty()) {
                for (Integer row : possibleNumberOfRowsList) {
                    if(solver.solve(list, row)){
                        break;
                    }
                }
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
