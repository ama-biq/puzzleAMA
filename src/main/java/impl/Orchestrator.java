package impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static impl.Solver.getPossibleNumberOfRows;

public class Orchestrator {

    private void orchestrateThePuzzle() throws Exception {
         File inputFile = new File("src\\test\\resources\\test17.txt");
         Solver solver = new Solver();
         List<PuzzleElementDefinition> list = solver.checkTheInputFile(inputFile);
        if (list.isEmpty())

        {
            solver.writeErrorsToTheOutPutFile();

        } else if (!solver.isSumOfEdgesZero(list))

        {
            List<Integer> possibleNumberOfRowsList = getPossibleNumberOfRows(list);
            for (Integer row : possibleNumberOfRowsList) {
                solver.isMissingCornerElements(row, list);
            }

            //List <Integer> tempList = getPossibleNumberOfRows(list);
            //  call to getPossibleNumbon each loop

            //in loop ON tempList call to
            //isMissingCornerElements(int amountOfRows, List<PuzzleElementDefinition> puzzleElements)
            // in loop call to number of straight edges, this method  not implemented yet
            //to run in loop on solve, solve get list and Integer from actual list
            solver.solve(list);
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
    Orchestrator orchestrator = new Orchestrator();
    orchestrator.orchestrateThePuzzle();

    }
}
