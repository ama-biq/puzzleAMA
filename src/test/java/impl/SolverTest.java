package impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SolverTest{

    @BeforeEach
    public void beforeEach(){
        EventHandler.emptyEventList();
    }

    Solver puzzleSolver = new Solver();
    PuzzleElementDefinition puzzleElementDefinition = new PuzzleElementDefinition();
    PuzzleElementDefinition puzzleElementDefinition1 = new PuzzleElementDefinition();
    List<PuzzleElementDefinition> listOfPuzzleElementDefinitionsWithoutId = new ArrayList<>();
    List<PuzzleElementDefinition> listOfPuzzleElementDefinitionsContainsId = new ArrayList<>();

    static List<String>actualErrorList = new ArrayList<>();

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
        listOfPuzzleElementDefinitionsWithoutId.add(puzzleElementDefinition);
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitionsWithoutId));
    }

    @Test
    public void testPositiveIsEnoughCornerElementsForSeveralRows(){

        PuzzleElementDefinition elementDefinitionTL = new PuzzleElementDefinition(0,0,1,-1);
        PuzzleElementDefinition elementDefinitionTR = new PuzzleElementDefinition(1,0,0,1);
        PuzzleElementDefinition elementDefinitionBL = new PuzzleElementDefinition(0,-1,-1,0);
        PuzzleElementDefinition elementDefinitionBR = new PuzzleElementDefinition(1,1,0,0);
        listOfPuzzleElementDefinitionsWithoutId.add(elementDefinitionTL);
        listOfPuzzleElementDefinitionsWithoutId.add(elementDefinitionTR);
        listOfPuzzleElementDefinitionsWithoutId.add(elementDefinitionBL);
        listOfPuzzleElementDefinitionsWithoutId.add(elementDefinitionBR);
        assertTrue(puzzleSolver.isEnoughCornerElementsForSeveralRows(listOfPuzzleElementDefinitionsWithoutId));
    }



    @ParameterizedTest
    @MethodSource("testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn")

    public void testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn(PuzzleElementDefinition p1, PuzzleElementDefinition p2){

        setEdgesForTwoElements(p1, p2);
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitionsWithoutId));
    }

    private static Stream<Arguments> testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 0,0,0,0), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,0,0,0), new PuzzleElementDefinition( 0,0,0,1)),
                Arguments.of(new PuzzleElementDefinition( 0,0,0,1), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,1,0,0), new PuzzleElementDefinition( 0,0,0,1)),
                Arguments.of(new PuzzleElementDefinition( 0,0,0,1), new PuzzleElementDefinition( 0,1,0,0))
        );
    }

    @ParameterizedTest
    @MethodSource("testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn")

    public void testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn(PuzzleElementDefinition p1, PuzzleElementDefinition p2){

        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitionsWithoutId));
    }



    private static Stream<Arguments> testNegativeIsEnoughCornerElementsForPazzelOfSeveralElementOneColumn() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 1,1,1,1), new PuzzleElementDefinition( -1,-1,-1,-1)),
                Arguments.of(new PuzzleElementDefinition( 1,0,0,0), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,0,1,1), new PuzzleElementDefinition( 0,0,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,1,0,0), new PuzzleElementDefinition( 0,1,0,0)),
                Arguments.of(new PuzzleElementDefinition( 0,0,0,1), new PuzzleElementDefinition( 0,0,0,1))
        );
    }

    @ParameterizedTest
    @MethodSource("testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneRows")

    public void testPositiveIsEnoughCornerElementsForPazzelOfSeveralElementOneRows(PuzzleElementDefinition p1, PuzzleElementDefinition p2){

        setEdgesForTwoElements(p1, p2);
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitionsWithoutId));
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
        assertFalse(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitionsWithoutId));

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
        assertTrue(puzzleSolver.isSumOfAllEdgesEqual(listOfPuzzleElementDefinitionsWithoutId));
    }


    @ParameterizedTest
    @MethodSource("negativeTestisSumOfAllEdgesEqualForTwoElements")
    public void negativeTestisSumOfAllEdgesEqualForTwoElements(PuzzleElementDefinition p1, PuzzleElementDefinition p2){
        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isSumOfAllEdgesEqual(listOfPuzzleElementDefinitionsWithoutId));

    }

    @Test
    public void testPositiveAllErrorMessagesForOneColumnPuzzleWrittenToList(){
        PuzzleElementDefinition p1 = new PuzzleElementDefinition(1,1,1,1);
        PuzzleElementDefinition p2 = new PuzzleElementDefinition(-1,-1,-1,-1);
        setEdgesForTwoElements(p1, p2);
        puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitionsWithoutId);
        assertTrue(EventHandler.getEventList().containsAll(expectedAllErrorWrittenToListOneColumnPuzzle()));
    }

    @Test
    public void positiveTestSolveThePuzzle() throws Exception {
        File inputFile = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
        File outputFile = new File("src\\test\\resources\\OutPutFile.txt");
        puzzleSolver.solveThePuzzle(inputFile);
    }



    public static ArrayList<String> expectedAllErrorWrittenToListOneColumnPuzzle(){
        return setErrorMessagesToExpectedErrorList(EventHandler.MISSING_CORNER + "BL", EventHandler.MISSING_CORNER + "BR",
                EventHandler.MISSING_CORNER + "TL", EventHandler.MISSING_CORNER + "TR"
        );
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
        listOfPuzzleElementDefinitionsWithoutId.add(ped1);
        listOfPuzzleElementDefinitionsWithoutId.add(ped2);
    }



    private static ArrayList<String> setErrorMessagesToExpectedErrorList(String... errors){
        ArrayList<String>errorList = new ArrayList<>();
        for(String error : errors){
            errorList.add(error);
        }
        return errorList;
    }
}