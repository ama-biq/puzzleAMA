package impl;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


public class SolverTest {

    Solver puzzleSolver = new Solver();
    PuzzleElementDefinition puzzleElementDefinition = new PuzzleElementDefinition();
    PuzzleElementDefinition puzzleElementDefinition1 = new PuzzleElementDefinition();
    List<PuzzleElementDefinition> listOfPuzzleElementDefinitions = new ArrayList<>();

    @Test
    public void positiveOneElementIsSumOfAllEdgesIsZero() {

        setAllPuzzleElementDefinitionToZero(puzzleElementDefinition);
        assertTrue(puzzleSolver.isSumOfAllEdgesIsZero(puzzleElementDefinition));
    }


    @Test
    public void neGativeOneElementIsSumOfAllEdgesIsZero() {

        puzzleElementDefinition.setLeft(0);
        puzzleElementDefinition.setUp(1);
        puzzleElementDefinition.setRight(0);
        puzzleElementDefinition.setBottom(0);
        assertFalse(puzzleSolver.isSumOfAllEdgesIsZero(puzzleElementDefinition));

    }
    @Test
    public void testPositiveIsEnoughStraitEdges(){

        setAllPuzzleElementDefinitionToZero(puzzleElementDefinition);
        assertTrue(puzzleSolver.isEnoughStraitEdges(puzzleElementDefinition));
    }
    @Test
    public void testNegativeIsEnoughStraitEdges(){

        puzzleElementDefinition.setLeft(0);
        puzzleElementDefinition.setUp(0);
        puzzleElementDefinition.setRight(1);
        puzzleElementDefinition.setBottom(0);
        assertFalse(puzzleSolver.isEnoughStraitEdges(puzzleElementDefinition));
    }

    @Test
    public void testIsEnoughCornerElementsForPazzelOfOneElement(){

        setAllPuzzleElementDefinitionToZero(puzzleElementDefinition );
        listOfPuzzleElementDefinitions.add(puzzleElementDefinition);
        assertTrue(puzzleSolver.isEnoughCornerElements(listOfPuzzleElementDefinitions));
    }

    //@Test
//    public void testIsEnoughCornerElementsForPazzelOfSeveralElementOneRow(){
//
//        setAllPuzzleElementDefinitionToZero(puzzleElementDefinition);
//        puzzleElementDefinition1.setLeft(0);
//        puzzleElementDefinition1.setUp(0);
//        puzzleElementDefinition1.setRight(0);
//        puzzleElementDefinition1.setBottom(0);
//        listOfPuzzleElementDefinitions.add(puzzleElementDefinition);
//        listOfPuzzleElementDefinitions.add(puzzleElementDefinition1);
//        assertTrue(puzzleSolver.isEnoughCornerElements(listOfPuzzleElementDefinitions));
//    }

    private void setAllPuzzleElementDefinitionToZero(PuzzleElementDefinition puzzleElementDefinition) {
        puzzleElementDefinition.setLeft(0);
        puzzleElementDefinition.setUp(0);
        puzzleElementDefinition.setRight(0);
        puzzleElementDefinition.setBottom(0);
    }

}