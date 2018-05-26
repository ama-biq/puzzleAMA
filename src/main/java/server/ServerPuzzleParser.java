package server;

import file.CmdPuzzleParser;
import impl.PuzzleElementDefinition;
import impl.Solver;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerPuzzleParser {
//    void orchestrateThePuzzle(CmdPuzzleParser cmdPuzzleParser) throws Exception {
//        // atomic boolean solved using to check if one of threads solve the puzzle.
//        AtomicBoolean solved = new AtomicBoolean(false);
//        Solver solver = new Solver(solved);
//        File inputFile = new File(cmdPuzzleParser.getFileInputPath());
//        int maxPoolSize = cmdPuzzleParser.getThreadAmount();
//        boolean rotate = cmdPuzzleParser.isRotate();
//
//        List<PuzzleElementDefinition> puzzlePiecesList = solver.checkTheInputFile(inputFile);
//        outputFile = new File(cmdPuzzleParser.getFileOutputPath());
//        if (puzzlePiecesList.isEmpty()) {
//            solver.writeErrorsToTheOutPutFile(outputFile);
//        } else {
//            List<Integer> boardsList = getSolutionBoardsList(solver, puzzlePiecesList, rotate);
//            if (!boardsList.isEmpty()) {
//                solvePuzzleInMultiThread(solved, puzzlePiecesList, maxPoolSize, rotate, outputFile, boardsList, boardsList.size());
//            }
//        }
//    }

}
