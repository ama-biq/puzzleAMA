package impl;

import file.FileParserUtils;
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


public class SolverTest extends EventHandler {

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
        assertEquals(puzzleSolver.getErrorsList(), expectedAllErrorWrittenToListOneColumnPuzzle());
    }

    @ParameterizedTest
    @MethodSource("positiveTestCheckIdValidity")
    public void positiveTestCheckIdValidity(PuzzleElementDefinition p1, PuzzleElementDefinition p2,
                                            PuzzleElementDefinition p3, PuzzleElementDefinition p4){
        setEdgesForFourElements(p1, p2, p3, p4);
        assertTrue(puzzleSolver.checkIdValidity(listOfPuzzleElementDefinitionsContainsId));
    }

    private static Stream<Arguments> positiveTestCheckIdValidity() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 3,0, 0, 0, 0),
                        new PuzzleElementDefinition( 4,1, 0, 0, 0),
                        new PuzzleElementDefinition( 2,1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 2,0, 0, 0, 0),
                        new PuzzleElementDefinition( 3,1, 0, 0, 0),
                        new PuzzleElementDefinition( 4,1, 0, 0, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("negativeTestCheckIdValidity")
    public void negativeTestCheckIdValidity(PuzzleElementDefinition p1, PuzzleElementDefinition p2,
                                            PuzzleElementDefinition p3, PuzzleElementDefinition p4){
        setEdgesForFourElements(p1, p2, p3, p4);
        assertFalse(puzzleSolver.checkIdValidity(listOfPuzzleElementDefinitionsContainsId));
    }

    private static Stream<Arguments> negativeTestCheckIdValidity() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 1,0, 0, 0, 0),
                        new PuzzleElementDefinition( 4,1, 0, 0, 0),
                        new PuzzleElementDefinition( 2,1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 2,0, 0, 0, 0),
                        new PuzzleElementDefinition( 3,1, 0, 0, 0),
                        new PuzzleElementDefinition( 5,1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 2,0, 0, 0, 0),
                        new PuzzleElementDefinition( 3,1, 0, 0, 0),
                        new PuzzleElementDefinition( 0,1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 2,0, 0, 0, 0),
                        new PuzzleElementDefinition( 3,1, 0, 0, 0),
                        new PuzzleElementDefinition( -6,1, 0, 0, 0))
        );
    }

    public static ArrayList<String> expectedAllErrorWrittenToListOneColumnPuzzle(){
        return setErrorMessagesToExpextedErrorList("Cannot solve puzzle: missing corner element for one row solution: Top Corner",
                "Cannot solve puzzle: missing corner element for one row solution: Bottom Corner"
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

    private void setEdgesForFourElements(PuzzleElementDefinition ped1, PuzzleElementDefinition ped2,
                                         PuzzleElementDefinition ped3,PuzzleElementDefinition ped4) {
        listOfPuzzleElementDefinitionsContainsId.add(ped1);
        listOfPuzzleElementDefinitionsContainsId.add(ped2);
        listOfPuzzleElementDefinitionsContainsId.add(ped3);
        listOfPuzzleElementDefinitionsContainsId.add(ped4);
    }

    private static ArrayList<String> setErrorMessagesToExpextedErrorList(String ... erors){
        ArrayList<String>errorList = new ArrayList<>();
        for(int i = 0;i < erors.length ; i++){
            errorList.add(erors[i]);
        }
        return errorList;
    }
}