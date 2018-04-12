package impl;

import java.io.File;
import java.util.List;

public class Orchestrator {
    public static void main(String[] args) throws Exception {
        File inputFile = new File("src\\test\\resources\\11AmirFileMatrix3on3.txt");
        Solver solver = new Solver();
        List<PuzzleElementDefinition> list = solver.checkTheInputFile(inputFile);
        if(list.isEmpty()){
            solver.writeErrorsToTheOutPutFile();

        }else if(solver.isSumOfEdgesZero(list)) {
                solver.solve(list);
        }
        if(solver.getSolutionList().isEmpty()){
            solver.writeErrorsToTheOutPutFile();
        }else{
            solver.writeSolutionToTheOutPutFile();
           // solver.writeErrorsToTheOutPutFile();
        }

    }
}
