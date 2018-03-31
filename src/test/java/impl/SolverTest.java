package impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitions));
    }

    @Test
    public void testPositiveIsEnoughCornerElementsForSeveralRows(){

        PuzzleElementDefinition elementDefinitionTL = new PuzzleElementDefinition(0,0,1,-1);
        PuzzleElementDefinition elementDefinitionTR = new PuzzleElementDefinition(1,0,0,1);
        PuzzleElementDefinition elementDefinitionBL = new PuzzleElementDefinition(0,-1,-1,0);
        PuzzleElementDefinition elementDefinitionBR = new PuzzleElementDefinition(1,1,0,0);
        listOfPuzzleElementDefinitions.add(elementDefinitionTL);
        listOfPuzzleElementDefinitions.add(elementDefinitionTR);
        listOfPuzzleElementDefinitions.add(elementDefinitionBL);
        listOfPuzzleElementDefinitions.add(elementDefinitionBR);
        assertTrue(puzzleSolver.isEnoughCornerElementsForSeveralRows(listOfPuzzleElementDefinitions));
    }



//    @ParameterizedTest
//    @CsvSource({
////            "0,0,0,0,0,0,0,0",
//            "0,0,0,0,0,0,0,1",
//
//
//    })
//    public void testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn(int val1, int val2, int val3, int val4, int val5, int val6, int val7, int val8){
//
//        setEdgesForTwoElements(val1, val2, val3, val4, val5, val6, val7, val8);
//        assertTrue(puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitions));
//    }

    @ParameterizedTest
    @CsvSource({
            "0,0,0,0,0,0,0,0",
            "1,0,0,0,0,0,1,0",
            "0,0,1,0,1,0,0,0",
            "0,0,0,0,1,0,0,0",
            "0,0,0,0,0,0,1,0",
            "1,0,0,0,0,0,0,0",
            "-1,0,0,0,0,0,-1,0",
            "0,0,-1,0,-1,0,0,0",
            "0,0,0,0,-1,0,0,0",
            "-1,0,0,0,0,0,0,0"
    })
    public void testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneRows(int val1, int val2, int val3, int val4, int val5, int val6, int val7, int val8){

        setEdgesForTwoElements(val1, val2, val3, val4, val5, val6, val7, val8);
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitions));
    }
    @ParameterizedTest
    @CsvSource({"0,0,0,0,0,0,0,1",
            "0,0,1,0,0,0,0,1",
            "0,1,0,0,0,0,0,0",
            "0,0,0,1,0,0,0,0",
            "0,0,0,0,0,0,0,-1",
            "0,0,-1,0,0,0,0,-1",
            "0,-1,0,0,0,0,0,0",
            "0,0,0,-1,0,0,0,0"
    })
    public void testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneRow(int val1, int val2, int val3, int val4, int val5, int val6, int val7, int val8){

        setEdgesForTwoElements(val1, val2, val3, val4, val5, val6, val7, val8);
        assertFalse(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitions));

    }
    private void setAllPuzzleElementDefinitionToZero(PuzzleElementDefinition puzzleElementDefinition) {
        puzzleElementDefinition.setLeft(0);
        puzzleElementDefinition.setUp(0);
        puzzleElementDefinition.setRight(0);
        puzzleElementDefinition.setBottom(0);
    }



    @ParameterizedTest
    @CsvSource({"0,0,0,0,0,0,0,0",
            "0,0,0,-1,0,0,0,1",
            "1,0,0,0,-1,0,0,0",
            "0,-1,0,0,0,1,0,0",
            "0,0,1,0,0,0,-1,0",
            "0,-1,-1,1,0,1,1,-1",
            "0,-1,1,0,0,1,-1,0"})
    public void positiveTestisSumOfAllEdgesEqualForTwoElements(int val1, int val2, int val3, int val4, int val5, int val6, int val7, int val8){
        setEdgesForTwoElements(val1, val2, val3, val4, val5, val6, val7, val8);
        assertTrue(puzzleSolver.isSumOfUpAndDownEdgesEqual(listOfPuzzleElementDefinitions));
    }

    @ParameterizedTest
    @CsvSource({"1,0,0,0,0,0,0,0",
//            "0,0,0,1,0,0,0,1",
//            "1,0,0,0,1,0,0,0",
//            "0,-1,0,0,0,0,1,0",
//            "0,-1,1,0,0,-1,-1,0"
 })
    public void negativeTestisSumOfAllEdgesEqualForTwoElements(int val1, int val2, int val3, int val4, int val5, int val6, int val7, int val8){
        setEdgesForTwoElements(val1, val2, val3, val4, val5, val6, val7, val8);
        assertFalse(puzzleSolver.isSumOfUpAndDownEdgesEqual(listOfPuzzleElementDefinitions));

    }



    private void setEdgesForTwoElements(int val1, int val2, int val3, int val4, int val5, int val6, int val7, int val8) {
        puzzleElementDefinition.setLeft(val1);
        puzzleElementDefinition.setUp(val2);
        puzzleElementDefinition.setRight(val3);
        puzzleElementDefinition.setBottom(val4);
        puzzleElementDefinition1.setLeft(val5);
        puzzleElementDefinition1.setUp(val6);
        puzzleElementDefinition1.setRight(val7);
        puzzleElementDefinition1.setBottom(val8);
        listOfPuzzleElementDefinitions.add(puzzleElementDefinition);
        listOfPuzzleElementDefinitions.add(puzzleElementDefinition1);

    }

    private void setEdgesForSeveralElements(int val1, int val2, int val3, int val4, int val5, int val6, int val7, int val8) {
        puzzleElementDefinition.setLeft(val1);
        puzzleElementDefinition.setUp(val2);
        puzzleElementDefinition.setRight(val3);
        puzzleElementDefinition.setBottom(val4);
        puzzleElementDefinition1.setLeft(val5);
        puzzleElementDefinition1.setUp(val6);
        puzzleElementDefinition1.setRight(val7);
        puzzleElementDefinition1.setBottom(val8);
        listOfPuzzleElementDefinitions.add(puzzleElementDefinition);
        listOfPuzzleElementDefinitions.add(puzzleElementDefinition1);

    }
//Example
    /*@ParameterizedTest
    @MethodSource("getParamProbider")
    public void testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneRow(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {
        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isEnoughCornerElements(listOfPuzzleElementDefinitions));
    }

    private static Stream<Arguments> sumProvider() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 1, 0), new PuzzleElementDefinition(1, 0, 0, 0, 1)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 1, 0, 0), new PuzzleElementDefinition(1, 0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 1, 0), new PuzzleElementDefinition(1, 0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 0, 0), new PuzzleElementDefinition(1, 0, 0, 0, -1)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, -1, 0), new PuzzleElementDefinition(1, 0, 0, 0, -1)),
                Arguments.of(new PuzzleElementDefinition(0, 0, -1, 0, 0), new PuzzleElementDefinition(1, 0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 0, -1), new PuzzleElementDefinition(1, 0, 0, 0, 1))

        );
    }

    private void setEdgesForTwoElements(PuzzleElementDefinition ped1, PuzzleElementDefinition ped2) {
        listOfPuzzleElementDefinitions.add(ped1);
        listOfPuzzleElementDefinitions.add(ped2);
    }*/

}