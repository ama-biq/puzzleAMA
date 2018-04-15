package impl;

import java.io.File;
import java.util.List;

public class Orchestrator {
    public static void main(String[] args) throws Exception {
        File inputFile = new File("src\\test\\resources\\18AmirFile.txt");
        Solver solver = new Solver();
        List<PuzzleElementDefinition> list = solver.checkTheInputFile(inputFile);
        if(list.isEmpty()){
            solver.writeErrorsToTheOutPutFile();

        }else if(solver.isSumOfEdgesZero(list)) {
            //todo  check in loop corners, sum of all edges and straight edges
                solver.solve(list);
        }
        if(solver.getSolutionList().isEmpty()){
            solver.writeErrorsToTheOutPutFile();
        }else{
            solver.writeSolutionToTheOutPutFile();
        }

    }
}
