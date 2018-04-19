package impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static impl.Solver.getPossibleNumberOfRows;

public class Orchestrator {

    public void orchestrateThePuzzle(String receivedFilePath) throws Exception {
        File inputFile = new File(receivedFilePath);
         Solver solver = new Solver();
         List<PuzzleElementDefinition> list = solver.checkTheInputFile(inputFile);
        if (list.isEmpty())

        {
            solver.writeErrorsToTheOutPutFile();

        } else if (solver.isSumOfEdgesZero(list)) {
            List<Integer> possibleNumberOfRowsList = getPossibleNumberOfRows(list);
            for (Integer row : possibleNumberOfRowsList) {
                solver.isMissingCornerElements(row, list);
            }

            if(EventHandler.getEventList().isEmpty()) {

                //List <Integer> tempList = getPossibleNumberOfRows(list);
                //  call to getPossibleNumbon each loop

                //in loop ON tempList call to
                //isMissingCornerElements(int amountOfRows, List<PuzzleElementDefinition> puzzleElements)
                // in loop call to number of straight edges, this method  not implemented yet
                //to run in loop on solve, solve get list and Integer from actual list
                solver.solve(list);
            }
        }
        if (solver.getSolutionList().isEmpty())
        {
            solver.writeErrorsToTheOutPutFile();
        }else

        {
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
