package impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


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



    @ParameterizedTest
    @MethodSource("testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn")

    public void testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn(PuzzleElementDefinition p1, PuzzleElementDefinition p2){

        setEdgesForTwoElements(p1, p2);
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitions));
    }

    private static Stream<Arguments> testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 1,0,0,0), new PuzzleElementDefinition( 0,0,0,0))
//                Arguments.of(new PuzzleElementDefinition( 0,0,0,0), new PuzzleElementDefinition( 0,0,0,1)),
//                Arguments.of(new PuzzleElementDefinition( 0,0,0,1), new PuzzleElementDefinition( 0,0,0,0)),
//                Arguments.of(new PuzzleElementDefinition( 0,1,0,0), new PuzzleElementDefinition( 0,0,0,1)),
//                Arguments.of(new PuzzleElementDefinition( 0,0,0,1), new PuzzleElementDefinition( 0,1,0,0))
        );
    }

    @ParameterizedTest
    @MethodSource("testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn")

    public void testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn(PuzzleElementDefinition p1, PuzzleElementDefinition p2){

        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitions));
    }

    private static Stream<Arguments> testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 1,1,1,1), new PuzzleElementDefinition( -1,-1,-1,-1)),
                Arguments.of(new PuzzleElementDefinition( 1,0,0,0), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,0,0,1), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,1,0,0), new PuzzleElementDefinition( 0,0,0,1)),
                Arguments.of(new PuzzleElementDefinition( 0,0,0,1), new PuzzleElementDefinition( 0,1,0,0))
        );
    }

    @ParameterizedTest
    @MethodSource("testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneRows")

    public void testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneRows(PuzzleElementDefinition p1, PuzzleElementDefinition p2){

        setEdgesForTwoElements(p1, p2);
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitions));
    }

    private static Stream<Arguments> testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneRows() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 0,0,0,0), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 1,0,0,0), new PuzzleElementDefinition( 0,0,1,0)),
                Arguments.of(new PuzzleElementDefinition( 0,0,1,0), new PuzzleElementDefinition( 1,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,0,0,0), new PuzzleElementDefinition( 1,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,0,0,0), new PuzzleElementDefinition( 0,0,1,0)),
                Arguments.of(new PuzzleElementDefinition( 1,0,0,0), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition(-1,0,0,0), new PuzzleElementDefinition( 0,0,-1,0)),
                Arguments.of(new PuzzleElementDefinition(0,0,-1,0), new PuzzleElementDefinition( -1,0,0,0)),
                Arguments.of(new PuzzleElementDefinition(-1,0,0,0), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition(-1,0,0,0), new PuzzleElementDefinition( 0,0,0,0))

        );
    }


    @ParameterizedTest
    @MethodSource("testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneRow")

    public void testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneRow(PuzzleElementDefinition p1, PuzzleElementDefinition p2){

        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitions));

    }

    private static Stream<Arguments> testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneRow() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 0,0,0,0), new PuzzleElementDefinition( 0,0,0,1)),
                Arguments.of(new PuzzleElementDefinition( 0,0,1,0), new PuzzleElementDefinition( 0,0,0,1)),
                Arguments.of(new PuzzleElementDefinition( 0,1,0,0), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,0,0,1), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,0,-1,0), new PuzzleElementDefinition( 0,0,0,-1)),
                Arguments.of(new PuzzleElementDefinition( 0, -1, -1, 1), new PuzzleElementDefinition( 0,0,0,-1)),
                Arguments.of(new PuzzleElementDefinition(0,-1,0,0), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition(0,0,0,-1), new PuzzleElementDefinition( 0,0,0,0))

        );
    }



    private void setAllPuzzleElementDefinitionToZero(PuzzleElementDefinition puzzleElementDefinition) {
        puzzleElementDefinition.setLeft(0);
        puzzleElementDefinition.setUp(0);
        puzzleElementDefinition.setRight(0);
        puzzleElementDefinition.setBottom(0);
    }



    @ParameterizedTest
    @MethodSource("positiveTestisSumOfAllEdgesEqualForTwoElements")
    public void positiveTestisSumOfAllEdgesEqualForTwoElements(PuzzleElementDefinition p1, PuzzleElementDefinition p2){
        setEdgesForTwoElements(p1,  p2);
        assertTrue(puzzleSolver.isSumOfUpAndDownEdgesEqual(listOfPuzzleElementDefinitions));
    }


    @ParameterizedTest
    @MethodSource("negativeTestisSumOfAllEdgesEqualForTwoElements")

    public void negativeTestisSumOfAllEdgesEqualForTwoElements(PuzzleElementDefinition p1, PuzzleElementDefinition p2){
        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isSumOfUpAndDownEdgesEqual(listOfPuzzleElementDefinitions));

    }


    private static Stream<Arguments> negativeTestisSumOfAllEdgesEqualForTwoElements() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 1, 0, 0, 0), new PuzzleElementDefinition( 0, 0, 0, 0))

        );
    }

    private static Stream<Arguments> positiveTestisSumOfAllEdgesEqualForTwoElements() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 0, 0, 0, 0), new PuzzleElementDefinition( 0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 0, 0, 0, -1), new PuzzleElementDefinition( 0, 0, 0, 1)),
                Arguments.of(new PuzzleElementDefinition( 1, 0, 0, 0), new PuzzleElementDefinition( -1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 0, -1, 0, 0), new PuzzleElementDefinition( 0, 1, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 0, 0, 1, 0), new PuzzleElementDefinition( 0, 0, -1, 0)),
                Arguments.of(new PuzzleElementDefinition( 0, -1, -1, 1), new PuzzleElementDefinition( 0, 1, 1, -1)),
                Arguments.of(new PuzzleElementDefinition(0, -1, 1, 0), new PuzzleElementDefinition( 0, 1, -1, 0))

        );
    }
    private void setEdgesForTwoElements(PuzzleElementDefinition ped1, PuzzleElementDefinition ped2) {
        listOfPuzzleElementDefinitions.add(ped1);
        listOfPuzzleElementDefinitions.add(ped2);
    }

}