package impl;

import file.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class SolverTest {

    @BeforeEach
    public void beforeEach() {
        EventHandler.emptyEventList();
        FileUtils.deleteFile(new File("src\\test\\resources\\OutPutFile.txt"));
        Orchestrator.isSolved.set(false);
    }

    Solver puzzleSolver = new Solver();
    Orchestrator orchestrator = new Orchestrator();
    PuzzleElementDefinition puzzleElementDefinition = new PuzzleElementDefinition();
    List<PuzzleElementDefinition> listOfPuzzleElementDefinitionsWithoutId = new ArrayList<>();


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
        puzzleSolver.solve(idsList, 3);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
    }


    @Test
    public void positive4ElementsTestResolveThePuzzle() throws IOException {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(3, 1, 0, 0, -1));
        idsList.add(new PuzzleElementDefinition(2, 0, -1, 0, 0));
        idsList.add(new PuzzleElementDefinition(4, 0, 1, 0, 0));

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(3);
        expectedList.add(2);
        expectedList.add(4);
        puzzleSolver.solve(idsList, 2);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
    }

    @Test
    public void negative3ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(2, -1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(3, 0, 0, 1, 0));

        List<String> expectedEvents = new ArrayList<>();
        expectedEvents.add(EventHandler.NO_SOLUTION);
        puzzleSolver.solve(idsList, 1);
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

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(3);
        expectedList.add(4);
        expectedList.add(2);
        expectedList.add(5);
        expectedList.add(6);
        puzzleSolver.solve(idsList, 3);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
    }

    //-------------------------------------------------------

    @Test
    public void TwoRowPuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(2, 0, 0, -1, 0));
        idsList.add(new PuzzleElementDefinition(3, 1, 0, -1, 0));
        idsList.add(new PuzzleElementDefinition(5, 1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(6, 0, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(4, 0, 0, 0, 0));

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(6);
        expectedList.add(4);
        expectedList.add(2);
        expectedList.add(3);
        expectedList.add(5);
        puzzleSolver.solve(idsList, 2);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
    }

    @Test
    public void OneRowPuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 1, 0));
        idsList.add(new PuzzleElementDefinition(2, -1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(3, -1, 0, 1, 0));

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(3);
        expectedList.add(2);
        puzzleSolver.solve(idsList, 1);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
    }

    //---------------------------------------------------------

    @Test
    public void positive9ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(8, 0, 0, -1, -1));
        idsList.add(new PuzzleElementDefinition(6, 1, 0, 1, -1));
        idsList.add(new PuzzleElementDefinition(3, -1, 0, 0, -1));
        idsList.add(new PuzzleElementDefinition(9, 0, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(5, 1, 1, 1, 1));
        idsList.add(new PuzzleElementDefinition(2, -1, 1, 0, 1));
        idsList.add(new PuzzleElementDefinition(7, 0, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(1, -1, -1, -1, 0));
        idsList.add(new PuzzleElementDefinition(4, 1, -1, 0, 0));

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(8);
        expectedList.add(6);
        expectedList.add(3);

        expectedList.add(9);
        expectedList.add(5);
        expectedList.add(2);

        expectedList.add(7);
        expectedList.add(1);
        expectedList.add(4);
        puzzleSolver.solve(idsList, 3);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
    }

    @Test
    public void positive12ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 1, -1));
        idsList.add(new PuzzleElementDefinition(2, -1, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(3, 1, 0, -1, -1));
        idsList.add(new PuzzleElementDefinition(4, 1, 0, 0, 1));
        idsList.add(new PuzzleElementDefinition(5, 0, 1, -1, -1));
        idsList.add(new PuzzleElementDefinition(6, 1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(7, -1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(8, 1, -1, 0, -1));
        idsList.add(new PuzzleElementDefinition(9, 0, 1, -1, 0));
        idsList.add(new PuzzleElementDefinition(10, 1, 1, -1, 0));
        idsList.add(new PuzzleElementDefinition(11, 1, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(12, -1, 1, 0, 0));

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(2);
        expectedList.add(3);
        expectedList.add(4);

        expectedList.add(5);
        expectedList.add(6);
        expectedList.add(7);
        expectedList.add(8);

        expectedList.add(9);
        expectedList.add(10);
        expectedList.add(11);
        expectedList.add(12);
        puzzleSolver.solve(idsList, 3);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
    }

    //not relevant for multi threading purpose
    /*@Test
    public void E2EnoCorners() throws Exception {
        String inputFilePath = "src\\test\\resources\\NoCorners.txt";
        orchestrator.orchestrateThePuzzle(inputFilePath);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\ExpectedNoTRcorner.txt");
        assertEquals(expected, out);
    }*/

    @Test
    public void firstE2EoneElement() throws Exception {
        String inputFilePath = "src\\test\\resources\\1AmirFileIn.txt";
        orchestrator.orchestrateThePuzzle(inputFilePath);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\1AmirFileExpected.txt");
        assertEquals(expected, out);
    }

    @Test
    public void fourElementsPuzzleElementE2Etest() throws Exception {
        String inputFilePath = "src\\test\\resources\\2AmirFile.txt";
        orchestrator.orchestrateThePuzzle(inputFilePath);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\2AmirFileExpected.txt");
        assertEquals(expected, out);
    }

    @Test
    public void missingPuzzleElementE2Etest() throws Exception {
        String inputFilePath = "src\\test\\resources\\3AmirFile.txt";
        orchestrator.orchestrateThePuzzle(inputFilePath);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\3AmirFileExpected.txt");
        assertEquals(expected, out);
    }

    @Test
    public void multipleErrorsE2Etest() throws Exception {
        String inputFilePath = "src\\test\\resources\\4AmirFile.txt";
        orchestrator.orchestrateThePuzzle(inputFilePath);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\4AmirFileExpected.txt");
        assertEquals(expected, out);
    }

    @Test
    public void severalIdMissingE2Etest() throws Exception {
        String inputFilePath = "src\\test\\resources\\7AmirFile.txt";
        orchestrator.orchestrateThePuzzle(inputFilePath);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\7AmirFileExpected.txt");
        assertEquals(expected, out);
    }

    @Test
    public void oneColumnSolutionE2Etest() throws Exception {
        String inputFilePath = "src\\test\\resources\\10AmirFile.txt";
        orchestrator.orchestrateThePuzzle(inputFilePath);
        String out = usingBufferedReader("src\\test\\resources\\OutPutFile.txt");
        String expected = usingBufferedReader("src\\test\\resources\\10AmirFileExpected.txt");
        assertEquals(expected, out);
    }

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
        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(5);
        expectedList.add(9);
        expectedList.add(13);

        expectedList.add(2);
        expectedList.add(6);
        expectedList.add(10);
        expectedList.add(14);

        expectedList.add(3);
        expectedList.add(7);
        expectedList.add(11);
        expectedList.add(15);

        expectedList.add(4);
        expectedList.add(8);
        expectedList.add(12);
        expectedList.add(16);
        puzzleSolver.solve(idsList, 4);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
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
        Collections.shuffle(idsList);

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(16);
        expectedList.add(21);
        expectedList.add(2);
        expectedList.add(17);
        expectedList.add(13);
        expectedList.add(1);
        expectedList.add(18);
        expectedList.add(9);
        expectedList.add(19);
        expectedList.add(7);
        expectedList.add(6);
        expectedList.add(10);
        expectedList.add(15);
        expectedList.add(3);
        expectedList.add(5);
        expectedList.add(24);
        expectedList.add(8);
        expectedList.add(23);
        expectedList.add(4);
        expectedList.add(11);
        expectedList.add(20);
        expectedList.add(14);
        expectedList.add(22);
        expectedList.add(12);
        puzzleSolver.solve(idsList, 6);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
    }


//test should be uncommented after indexing solution
    @Test
    public void positive48ElementsTestResolveThePuzzle() {

        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(17, 1, 0, 0, 1));
        idsList.add(new PuzzleElementDefinition(13, 0, 1, 1, -1));
        idsList.add(new PuzzleElementDefinition(18, -1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(21, -1, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(16, 0, 0, 1, -1));
        idsList.add(new PuzzleElementDefinition(9, 1, -1, 0, -1));
        idsList.add(new PuzzleElementDefinition(19, 0, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(6, 1, -1, 1, 1));
        idsList.add(new PuzzleElementDefinition(1, -1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(10, -1, 1, 0, -1));
        idsList.add(new PuzzleElementDefinition(35, -1, -1, 1, 1));
        idsList.add(new PuzzleElementDefinition(15, 0, -1, -1, 1));
        idsList.add(new PuzzleElementDefinition(3, 1, 1, 1, -1));
        idsList.add(new PuzzleElementDefinition(14, -1, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(5, -1, -1, 1, 1));
        idsList.add(new PuzzleElementDefinition(24, -1, 1, 0, 1));
        idsList.add(new PuzzleElementDefinition(2, 1, 0, -1, -1));
        idsList.add(new PuzzleElementDefinition(8, 0, -1, -1, -1));
        idsList.add(new PuzzleElementDefinition(310, -1, 1, 0, -1));
        idsList.add(new PuzzleElementDefinition(23, 1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(4, 1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(20, 0, 1, 1, 0));
        idsList.add(new PuzzleElementDefinition(22, -1, 1, -1, 0));
        idsList.add(new PuzzleElementDefinition(12, 1, -1, 0, 0));

        idsList.add(new PuzzleElementDefinition(317, 1, 0, 0, 1));
        idsList.add(new PuzzleElementDefinition(31, -1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(7, 1, 1, -1, -1));
        idsList.add(new PuzzleElementDefinition(11, -1, -1, 0, 1));
        idsList.add(new PuzzleElementDefinition(36, 1, -1, 1, 1));
        idsList.add(new PuzzleElementDefinition(39, 1, -1, 0, -1));
        idsList.add(new PuzzleElementDefinition(315, 0, -1, -1, 1));
        idsList.add(new PuzzleElementDefinition(318, -1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(323, 1, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(319, 0, 1, -1, 1));
        idsList.add(new PuzzleElementDefinition(37, 1, 1, -1, -1));
        idsList.add(new PuzzleElementDefinition(312, 1, -1, 0, 0));
        idsList.add(new PuzzleElementDefinition(38, 0, -1, -1, -1));
        idsList.add(new PuzzleElementDefinition(34, 1, -1, 1, -1));
        idsList.add(new PuzzleElementDefinition(313, 0, 1, 1, -1));
        idsList.add(new PuzzleElementDefinition(33, 1, 1, 1, -1));
        idsList.add(new PuzzleElementDefinition(311, -1, -1, 0, 1));
        idsList.add(new PuzzleElementDefinition(324, -1, 1, 0, 1));
        idsList.add(new PuzzleElementDefinition(320, 0, 1, 1, 0));
        idsList.add(new PuzzleElementDefinition(314, -1, -1, 1, 0));
        idsList.add(new PuzzleElementDefinition(322, -1, 1, -1, 0));
        idsList.add(new PuzzleElementDefinition(321, -1, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(32, 1, 0, -1, -1));
        idsList.add(new PuzzleElementDefinition(316, 0, 0, 1, -1));
        Collections.shuffle(idsList);

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(16);
        expectedList.add(21);
        expectedList.add(2);
        expectedList.add(17);
        expectedList.add(13);
        expectedList.add(1);
        expectedList.add(18);
        expectedList.add(9);
        expectedList.add(19);
        expectedList.add(7);
        expectedList.add(6);
        expectedList.add(10);
        expectedList.add(15);
        expectedList.add(3);
        expectedList.add(5);
        expectedList.add(24);
        expectedList.add(8);
        expectedList.add(23);
        expectedList.add(4);
        expectedList.add(11);
        expectedList.add(20);
        expectedList.add(14);
        expectedList.add(22);
        expectedList.add(12);

        expectedList.add(316);
        expectedList.add(321);
        expectedList.add(32);
        expectedList.add(317);
        expectedList.add(313);
        expectedList.add(31);
        expectedList.add(318);
        expectedList.add(39);
        expectedList.add(319);
        expectedList.add(37);
        expectedList.add(36);
        expectedList.add(310);
        expectedList.add(315);
        expectedList.add(33);
        expectedList.add(35);
        expectedList.add(324);
        expectedList.add(38);
        expectedList.add(323);
        expectedList.add(34);
        expectedList.add(311);
        expectedList.add(320);
        expectedList.add(314);
        expectedList.add(322);
        expectedList.add(312);
        Long start = System.currentTimeMillis();
        puzzleSolver.solve(idsList, 12);
        Long end = System.currentTimeMillis();
        System.out.println("time: " + (end - start));
        assertEquals(expectedList, puzzleSolver.getSolutionList());
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

    @Test
    public void isElementsEqual() {
        PuzzleElementDefinition p1 = (new PuzzleElementDefinition(1, 0, 1, -1, 0));
        PuzzleElementDefinition p2 = (new PuzzleElementDefinition(2, 1, -1, 0, 0));
        assertTrue(puzzleSolver.isElementsEquals(p1, p2), p2.getRotationAngle() +" " + p1.getRotationAngle());
    }
}