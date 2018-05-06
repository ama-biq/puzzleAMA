package impl;

import file.CmdPuzzleParser;
import file.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class SolverTest {

    private Solver puzzleSolver = new Solver(new AtomicBoolean(false));
    private Orchestrator orchestrator = new Orchestrator();
    private PuzzleElementDefinition puzzleElementDefinition = new PuzzleElementDefinition();
    private List<PuzzleElementDefinition> listOfPuzzleElementDefinitionsWithoutId = new ArrayList<>();
    private boolean rotate = false;
    private File outputFile = new File("src\\test\\resources\\OutPutFile.txt");
    private CmdPuzzleParser cmdPuzzleParser = new CmdPuzzleParser();
    private PuzzleValidator puzzleValidator = new PuzzleValidator();


    @BeforeEach
    public void beforeEach() {
        EventHandler.emptyEventList();
        FileUtils.deleteFile(new File("src\\test\\resources\\OutPutFile.txt"));
        cmdPuzzleParser.setFileOutputPath("src\\test\\resources\\OutPutFile.txt");
        cmdPuzzleParser.setRotate(rotate);
    }

    @Test
    public void testPositiveIsEnoughStraitEdges() {

        setAllPuzzleElementDefinitionToZero(puzzleElementDefinition);
        assertTrue(puzzleSolver.isEnoughStraitEdges(puzzleElementDefinition));
    }

    @Test
    public void testNegativeIsEnoughStraitEdges() {

        puzzleElementDefinition.setLeft(0);
        puzzleElementDefinition.setUp(0);
        puzzleElementDefinition.setRight(1);
        puzzleElementDefinition.setBottom(0);
        assertFalse(puzzleSolver.isEnoughStraitEdges(puzzleElementDefinition));
    }

    @Test
    public void testIsEnoughCornerElementsForPuzzlOfOneElement() {

        setAllPuzzleElementDefinitionToZero(puzzleElementDefinition);
        listOfPuzzleElementDefinitionsWithoutId.add(puzzleElementDefinition);
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitionsWithoutId));
    }

    @Test
    public void testPositiveIsEnoughCornerElementsForSeveralRows() {

        PuzzleElementDefinition elementDefinitionTL = new PuzzleElementDefinition(0, 0, 1, -1);
        PuzzleElementDefinition elementDefinitionTR = new PuzzleElementDefinition(1, 0, 0, 1);
        PuzzleElementDefinition elementDefinitionBL = new PuzzleElementDefinition(0, -1, -1, 0);
        PuzzleElementDefinition elementDefinitionBR = new PuzzleElementDefinition(1, 1, 0, 0);
        listOfPuzzleElementDefinitionsWithoutId.add(elementDefinitionTL);
        listOfPuzzleElementDefinitionsWithoutId.add(elementDefinitionTR);
        listOfPuzzleElementDefinitionsWithoutId.add(elementDefinitionBL);
        listOfPuzzleElementDefinitionsWithoutId.add(elementDefinitionBR);
        assertTrue(puzzleSolver.isEnoughCornerElementsForSeveralRows(listOfPuzzleElementDefinitionsWithoutId));
    }


    @ParameterizedTest
    @MethodSource("testPositiveIsEnoughCornerElementsForPuzzleOfSeveralElementOneColumn")

    public void testPositiveIsEnoughCornerElementsForPuzzleOfSeveralElementOneColumn(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {

        setEdgesForTwoElements(p1, p2);
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitionsWithoutId));
    }

    private static Stream<Arguments> testPositiveIsEnoughCornerElementsForPuzzleOfSeveralElementOneColumn() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 1)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 1), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 1, 0, 0), new PuzzleElementDefinition(0, 0, 0, 1)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 1), new PuzzleElementDefinition(0, 1, 0, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("dataForTestNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneColumn")

    public void testNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneColumn(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {

        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitionsWithoutId));
    }


    private static Stream<Arguments> dataForTestNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneColumn() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(1, 1, 1, 1), new PuzzleElementDefinition(-1, -1, -1, -1)),
                Arguments.of(new PuzzleElementDefinition(1, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 1, 1), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 1, 0, 0), new PuzzleElementDefinition(0, 1, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 1), new PuzzleElementDefinition(0, 0, 0, 1))
        );
    }

    @ParameterizedTest
    @MethodSource("dataForTestPositiveIsEnoughCornerElementsForPuzzleOfSeveralElementOneRows")

    public void testPositiveIsEnoughCornerElementsForPuzzleOfSeveralElementOneRows(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {

        setEdgesForTwoElements(p1, p2);
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitionsWithoutId));
    }

    private static Stream<Arguments> dataForTestPositiveIsEnoughCornerElementsForPuzzleOfSeveralElementOneRows() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(1, 0, 0, 0), new PuzzleElementDefinition(0, 0, 1, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 1, 0), new PuzzleElementDefinition(1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 0), new PuzzleElementDefinition(1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 0), new PuzzleElementDefinition(0, 0, 1, 0)),
                Arguments.of(new PuzzleElementDefinition(1, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(-1, 0, 0, 0), new PuzzleElementDefinition(0, 0, -1, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, -1, 0), new PuzzleElementDefinition(-1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(-1, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(-1, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0))

        );
    }


    @ParameterizedTest
    @MethodSource("dataForTestNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneRow")

    public void testNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneRow(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {

        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitionsWithoutId));

    }

    private static Stream<Arguments> dataForTestNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneRow() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 1)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 1, 0), new PuzzleElementDefinition(0, 0, 0, 1)),
                Arguments.of(new PuzzleElementDefinition(0, 1, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 1), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, -1, 0), new PuzzleElementDefinition(0, 0, 0, -1)),
                Arguments.of(new PuzzleElementDefinition(0, -1, -1, 1), new PuzzleElementDefinition(0, 0, 0, -1)),
                Arguments.of(new PuzzleElementDefinition(0, -1, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, -1), new PuzzleElementDefinition(0, 0, 0, 0))

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
    public void positiveTestisSumOfAllEdgesEqualForTwoElements(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {
        setEdgesForTwoElements(p1, p2);
        assertTrue(puzzleSolver.isSumOfParallelEdgesZero(listOfPuzzleElementDefinitionsWithoutId));
    }


    @ParameterizedTest
    @MethodSource("negativeTestisSumOfAllEdgesEqualForTwoElements")
    public void negativeTestisSumOfAllEdgesEqualForTwoElements(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {
        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isSumOfParallelEdgesZero(listOfPuzzleElementDefinitionsWithoutId));

    }

    @Test
    public void testPositiveAllErrorMessagesForOneColumnPuzzleWrittenToList() {
        PuzzleElementDefinition p1 = new PuzzleElementDefinition(1, 1, 1, 1);
        PuzzleElementDefinition p2 = new PuzzleElementDefinition(-1, -1, -1, -1);
        setEdgesForTwoElements(p1, p2);
        puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitionsWithoutId);
        assertTrue(EventHandler.getEventList().containsAll(expectedAllErrorWrittenToListOneColumnPuzzle()));
    }

    public static ArrayList<String> expectedAllErrorWrittenToListOneColumnPuzzle() {
        return setErrorMessagesToExpectedErrorList(EventHandler.MISSING_CORNER + "BL", EventHandler.MISSING_CORNER + "BR",
                EventHandler.MISSING_CORNER + "TL", EventHandler.MISSING_CORNER + "TR"
        );
    }

    private static Stream<Arguments> negativeTestisSumOfAllEdgesEqualForTwoElements() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(1, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0))

        );
    }

    private static Stream<Arguments> positiveTestisSumOfAllEdgesEqualForTwoElements() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, -1), new PuzzleElementDefinition(0, 0, 0, 1)),
                Arguments.of(new PuzzleElementDefinition(1, 0, 0, 0), new PuzzleElementDefinition(-1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, -1, 0, 0), new PuzzleElementDefinition(0, 1, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 1, 0), new PuzzleElementDefinition(0, 0, -1, 0)),
                Arguments.of(new PuzzleElementDefinition(0, -1, -1, 1), new PuzzleElementDefinition(0, 1, 1, -1)),
                Arguments.of(new PuzzleElementDefinition(0, -1, 1, 0), new PuzzleElementDefinition(0, 1, -1, 0))

        );
    }

    private void setEdgesForTwoElements(PuzzleElementDefinition ped1, PuzzleElementDefinition ped2) {
        listOfPuzzleElementDefinitionsWithoutId.add(ped1);
        listOfPuzzleElementDefinitionsWithoutId.add(ped2);
    }


    private static ArrayList<String> setErrorMessagesToExpectedErrorList(String... errors) {
        ArrayList<String> errorList = new ArrayList<>();
        for (String error : errors) {
            errorList.add(error);
        }
        return errorList;
    }


    //-------------------------puzzle solution tests------------------------------
    @Test
    public void positive1ColumnElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(2, 0, -1, 0, 0));
        idsList.add(new PuzzleElementDefinition(3, 0, 0, 0, 1));

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(3);
        expectedList.add(2);
        puzzleSolver.solve(idsList, 3, rotate, outputFile);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void negative3ElementsTestResolveThePuzzle() {

        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(2, -1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(3, 0, 0, 1, 0));

        List<String> expectedEvents = new ArrayList<>();
        expectedEvents.add(EventHandler.NO_SOLUTION);
        puzzleSolver.solve(idsList, 1, rotate, outputFile);
        assertTrue(EventHandler.getEventList().containsAll(expectedEvents));
    }

    @Test
    public void positive6ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(3, 1, 0, 0, -1));
        idsList.add(new PuzzleElementDefinition(2, 0, 1, 0, 0));
        idsList.add(new PuzzleElementDefinition(4, 0, -1, 0, 0));
        idsList.add(new PuzzleElementDefinition(5, 0, 0, -1, 0));
        idsList.add(new PuzzleElementDefinition(6, 1, 0, 0, 0));

        puzzleSolver.solve(idsList, 3, rotate, outputFile);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    //-------------------------------------------------------
    //todo code review with Andrey for validator
    @Test
    public void TwoRowPuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(2, 0, 0, -1, 0));
        idsList.add(new PuzzleElementDefinition(3, 1, 0, -1, 0));
        idsList.add(new PuzzleElementDefinition(5, 1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(6, 0, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(4, 0, 0, 0, 0));

        puzzleSolver.solve(idsList, 2, rotate, outputFile);
        assertEquals( puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void TwoRowPuzzleOnlyRotatePossible() {
        rotate = true;
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(2, -1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(3, 0, -1, 0, 1));
        idsList.add(new PuzzleElementDefinition(5, 1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(6, 0, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(4, 0, 0, 0, 0));

        puzzleSolver.solve(idsList, 2, rotate, outputFile);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void OneRowPuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 1, 0));
        idsList.add(new PuzzleElementDefinition(2, -1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(3, -1, 0, 1, 0));

        puzzleSolver.solve(idsList, 1, rotate, outputFile);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void positive16ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(7, -1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(9, 1, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 1, 1));
        idsList.add(new PuzzleElementDefinition(4, 0, -1, -1, 0));
        idsList.add(new PuzzleElementDefinition(6, 1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(13, 1, 0, 0, 1));
        idsList.add(new PuzzleElementDefinition(2, 0, -1, -1, -1));
        idsList.add(new PuzzleElementDefinition(3, 0, 1, 1, 1));
        idsList.add(new PuzzleElementDefinition(8, 1, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(10, -1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(16, -1, -1, 0, 0));
        idsList.add(new PuzzleElementDefinition(15, 1, 1, 0, 1));
        idsList.add(new PuzzleElementDefinition(14, -1, -1, 0, -1));
        idsList.add(new PuzzleElementDefinition(5, -1, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(12, -1, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(11, 1, 1, -1, 1));

        puzzleSolver.solve(idsList, 4, rotate, outputFile);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void positive32ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 1, 1));
        idsList.add(new PuzzleElementDefinition(2, 0, -1, -1, -1));
        idsList.add(new PuzzleElementDefinition(3, 0, 1, 1, 1));
        idsList.add(new PuzzleElementDefinition(4, 0, -1, -1, 0));
        idsList.add(new PuzzleElementDefinition(5, -1, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(6, 1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(7, -1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(8, 1, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(9, 1, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(10, -1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(11, 1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(12, -1, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(13, 1, 0, 0, 1));
        idsList.add(new PuzzleElementDefinition(14, -1, -1, 0, -1));
        idsList.add(new PuzzleElementDefinition(15, 1, 1, 0, 1));
        idsList.add(new PuzzleElementDefinition(16, -1, -1, 0, 0));


        idsList.add(new PuzzleElementDefinition(21, 0, 0, 1, 1));
        idsList.add(new PuzzleElementDefinition(22, 0, -1, -1, -1));
        idsList.add(new PuzzleElementDefinition(23, 0, 1, 1, 1));
        idsList.add(new PuzzleElementDefinition(24, 0, -1, -1, 0));
        idsList.add(new PuzzleElementDefinition(25, -1, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(26, 1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(27, -1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(28, 1, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(29, 1, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(210, -1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(211, 1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(212, -1, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(213, 1, 0, 0, 1));
        idsList.add(new PuzzleElementDefinition(214, -1, -1, 0, -1));
        idsList.add(new PuzzleElementDefinition(215, 1, 1, 0, 1));
        idsList.add(new PuzzleElementDefinition(216, -1, -1, 0, 0));

        puzzleSolver.solve(idsList, 8, rotate, outputFile);
        assertEquals( puzzleValidator.validatePuzzleSolution(), true);
    }


    @Test
    public void positive24ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(16, 0, 0, 1, -1));
        idsList.add(new PuzzleElementDefinition(21, -1, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(2, 1, 0, -1, -1));
        idsList.add(new PuzzleElementDefinition(17, 1, 0, 0, 1));
        idsList.add(new PuzzleElementDefinition(13, 0, 1, 1, -1));
        idsList.add(new PuzzleElementDefinition(1, -1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(18, -1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(9, 1, -1, 0, -1));
        idsList.add(new PuzzleElementDefinition(19, 0, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(7, 1, 1, -1, -1));
        idsList.add(new PuzzleElementDefinition(6, 1, -1, 1, 1));
        idsList.add(new PuzzleElementDefinition(10, -1, 1, 0, -1));
        idsList.add(new PuzzleElementDefinition(15, 0, -1, -1, 1));
        idsList.add(new PuzzleElementDefinition(3, 1, 1, 1, -1));
        idsList.add(new PuzzleElementDefinition(5, -1, -1, 1, 1));
        idsList.add(new PuzzleElementDefinition(24, -1, 1, 0, 1));
        idsList.add(new PuzzleElementDefinition(8, 0, -1, -1, -1));
        idsList.add(new PuzzleElementDefinition(23, 1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(4, 1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(11, -1, -1, 0, 1));
        idsList.add(new PuzzleElementDefinition(20, 0, 1, 1, 0));
        idsList.add(new PuzzleElementDefinition(14, -1, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(22, -1, 1, -1, 0));
        idsList.add(new PuzzleElementDefinition(12, 1, -1, 0, 0));
        puzzleSolver.solve(idsList, 6, rotate, outputFile);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    //---------------------------------------------------------

    //not relevant for multi threading purpose
    /*@Test
    public void E2EnoCorners() throws Exception {
        String inputFilePath = "src\\test\\resources\\NoCorners.txt";
        orchestrator.orchestrateThePuzzle(inputFilePath);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\ExpectedNoTRcorner.txt");
        assertEquals(expected, out);
    }*/
//----------------------E2E + multi threading puzzle solution tests -------------------------------------

    @Test
    public void oneColumnSolutionE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\10AmirFile.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);

    }

    @Test
    public void firstE2EoneElement() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\1AmirFileIn.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void fourElementsPuzzleElementE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\2AmirFile.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void _24ElementsPuzzleElementE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\test15.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void _42ElementsPuzzleElementE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\gen42_1.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void _64ElementsPuzzleElementE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\gen64_1.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void _64_2ElementsPuzzleElementE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\gen64_2.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Disabled
    @Test
    public void _100_1ElementsPuzzleElementE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\gen100_1.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Disabled
    @Test
    public void _100_2ElementsPuzzleElementE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\gen100_2.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Disabled
    @Test
    public void _100_3ElementsPuzzleElementE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\gen100_3.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        assertEquals(puzzleValidator.validatePuzzleSolution(), true);
    }

    @Test
    public void missingPuzzleElementE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\3AmirFile.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\3AmirFileExpected.txt");
        assertEquals(expected, out);
    }

    @Test
    public void multipleErrorsE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\4AmirFile.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\4AmirFileExpected.txt");
        assertEquals(expected, out);
    }

    @Test
    public void severalIdMissingE2Etest() throws Exception {
        cmdPuzzleParser.setFileInputPath("src\\test\\resources\\7AmirFile.txt");
        orchestrator.orchestrateThePuzzle(cmdPuzzleParser);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\7AmirFileExpected.txt");
        assertEquals(expected, out);
    }

    //---------------------------------------------------------------------------------------
    @Test
    public void positiveSumOfAllElementEdgesIsZero() {

        List<PuzzleElementDefinition> listOfPuzzleElements = new ArrayList<>();
        listOfPuzzleElements.add(new PuzzleElementDefinition(1, 0, 1, 0, -1));
        listOfPuzzleElements.add(new PuzzleElementDefinition(1, -1, 1, 1, -1));
        assertTrue(puzzleSolver.isSumOfParallelEdgesZero(listOfPuzzleElements), "sum of edges is not zero");
    }

    @Test
    public void negativeSumOfAllElementEdgesIsNotZero() {

        List<PuzzleElementDefinition> listOfPuzzleElements = new ArrayList<>();
        listOfPuzzleElements.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        listOfPuzzleElements.add(new PuzzleElementDefinition(1, -1, 1, -1, -1));
        assertFalse(puzzleSolver.isSumOfParallelEdgesZero(listOfPuzzleElements), "sum of edges is zero");
        assertTrue(EventHandler.getEventList().contains(EventHandler.SUM_ZERO), "expected error message [" + EventHandler.SUM_ZERO + "] not found");
    }

//-----------------------------methods---------------------------------------
    private static String usingBufferedReader(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }


}