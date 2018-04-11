package impl;

import java.io.File;
import java.util.List;

public class Orchestrator {
    public static void main(String[] args) throws Exception {
        File inputFile = new File("src\\test\\resources\\validPuzzle2PeacesOneRow.txt");
        Solver solver = new Solver();
        PuzzleElementDefinition templateElement = new PuzzleElementDefinition(0,0,Integer.MAX_VALUE,0) ;
        List<PuzzleElementDefinition> list = solver.checkTheInputFile(inputFile);
        if(list.isEmpty()){
            solver.writeErrorsToTheOutPutFile();
        }else {
            solver.resolveTheOneRowPuzzle(list,templateElement);
        }
        if(solver.getSolutionList().isEmpty()){
            solver.writeErrorsToTheOutPutFile();
        }else{
            solver.addSolutionToFile();
            solver.writeErrorsToTheOutPutFile();
        }

    }
}
