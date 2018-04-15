package impl;

import file.FileUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.*;


public class SolverTest {

    @BeforeEach
    public void beforeEach() {
        EventHandler.emptyEventList();
    }

    Solver puzzleSolver = new Solver();
    PuzzleElementDefinition puzzleElementDefinition = new PuzzleElementDefinition();
    List<PuzzleElementDefinition> listOfPuzzleElementDefinitionsWithoutId = new ArrayList<>();


    @Test
    public void positiveOneElementIsSumOfAllEdgesIsZero() {

        setAllPuzzleElementDefinitionToZero(puzzleElementDefinition);
        assertTrue(puzzleSolver.isSumOfAllEdgesIsZero(puzzleElementDefinition));
    }


    @Test
    public void negativeOneElementIsSumOfAllEdgesIsZero() {

        puzzleElementDefinition.setLeft(0);
        puzzleElementDefinition.setUp(1);
        puzzleElementDefinition.setRight(0);
        puzzleElementDefinition.setBottom(0);
        assertFalse(puzzleSolver.isSumOfAllEdgesIsZero(puzzleElementDefinition));

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
    @MethodSource("testNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneColumn")

    public void testNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneColumn(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {

        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitionsWithoutId));
    }


    private static Stream<Arguments> testNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneColumn() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(1, 1, 1, 1), new PuzzleElementDefinition(-1, -1, -1, -1)),
                Arguments.of(new PuzzleElementDefinition(1, 0, 0, 0), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 1, 1), new PuzzleElementDefinition(0, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 1, 0, 0), new PuzzleElementDefinition(0, 1, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 1), new PuzzleElementDefinition(0, 0, 0, 1))
        );
    }

    @ParameterizedTest
    @MethodSource("testPositiveIsEnoughCornerElementsForPuzzleOfSeveralElementOneRows")

    public void testPositiveIsEnoughCornerElementsForPuzzleOfSeveralElementOneRows(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {

        setEdgesForTwoElements(p1, p2);
        assertTrue(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitionsWithoutId));
    }

    private static Stream<Arguments> testPositiveIsEnoughCornerElementsForPuzzleOfSeveralElementOneRows() {
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
    @MethodSource("testNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneRow")

    public void testNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneRow(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {

        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isEnoughCornerElementsForOneRow(listOfPuzzleElementDefinitionsWithoutId));

    }

    private static Stream<Arguments> testNegativeIsEnoughCornerElementsForPuzzleOfSeveralElementOneRow() {
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
        assertTrue(puzzleSolver.isSumOfAllEdgesEqual(listOfPuzzleElementDefinitionsWithoutId));
    }


    @ParameterizedTest
    @MethodSource("negativeTestisSumOfAllEdgesEqualForTwoElements")
    public void negativeTestisSumOfAllEdgesEqualForTwoElements(PuzzleElementDefinition p1, PuzzleElementDefinition p2) {
        setEdgesForTwoElements(p1, p2);
        assertFalse(puzzleSolver.isSumOfAllEdgesEqual(listOfPuzzleElementDefinitionsWithoutId));

    }

    @Test
    public void testPositiveAllErrorMessagesForOneColumnPuzzleWrittenToList() {
        PuzzleElementDefinition p1 = new PuzzleElementDefinition(1, 1, 1, 1);
        PuzzleElementDefinition p2 = new PuzzleElementDefinition(-1, -1, -1, -1);
        setEdgesForTwoElements(p1, p2);
        puzzleSolver.isEnoughCornerElementsForOneColumn(listOfPuzzleElementDefinitionsWithoutId);
        assertTrue(EventHandler.getEventList().containsAll(expectedAllErrorWrittenToListOneColumnPuzzle()));
    }

   /* @Test
    public void positiveTestSolveThePuzzle() throws Exception {
        File inputFile = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
        File outputFile = new File("src\\test\\resources\\OutPutFile.txt");
        puzzleSolver.solveThePuzzle(inputFile);
    }*/


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
    public void positive2ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(2, 0, 0, 0, 0));
        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(2);
        puzzleSolver.solve(idsList);
        assertEquals(puzzleSolver.getSolutionList(), expectedList);

    }

    @Test
    public void positive3ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(2, -1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(3, 0, 0, 1, 0));

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(3);
        expectedList.add(2);
        puzzleSolver.solve(idsList);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
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
        puzzleSolver.solve(idsList);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
    }

//    @Test
//    public void positive4ElementsTestResolveThePuzzle() {
//        List<PuzzleElementDefinition> idsList = new ArrayList<>();
//        idsList.add(new PuzzleElementDefinition(1, 0, 0, -1, 1));
//        idsList.add(new PuzzleElementDefinition(3, 1, 0, 0, -1));
//        idsList.add(new PuzzleElementDefinition(2, 0, -1, 0, 0));
//
//        List<Integer> expectedList = new ArrayList<>();
//        expectedList.add(1);
//        expectedList.add(3);
//        expectedList.add(2);
//        expectedList.add(4);
//        puzzleSolver.solve(idsList);
//        assertEquals(expectedList, puzzleSolver.getSolutionList());
//    }
    @Test
    public void negative3ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(2, -1, 0, 0, 0));
        idsList.add(new PuzzleElementDefinition(3, 0, 0, 1, 0));

        List<String > expectedEvents = new ArrayList<>();
        expectedEvents.add(EventHandler.NO_SOLUTION);
        puzzleSolver.solve(idsList);
        assertTrue(EventHandler.getEventList().containsAll(expectedEvents));
    }

    @Test
    public void positive6ElementsTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, -1, 1));
        idsList.add(new PuzzleElementDefinition(3, 1, 0, 0, -1));
        idsList.add(new PuzzleElementDefinition(4, 0, 1, 0, 0));
        idsList.add(new PuzzleElementDefinition(2, 0, -1, 0, 0));
        idsList.add(new PuzzleElementDefinition(5, 0, 0, -1, 0));
        idsList.add(new PuzzleElementDefinition(6, 1, 0, 0, 0));

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(3);
        expectedList.add(2);
        expectedList.add(4);
        expectedList.add(5);
        expectedList.add(6);
        puzzleSolver.solve(idsList);
        assertEquals(expectedList, puzzleSolver.getSolutionList());
    }

    @Test
    public void positive1ElementTestResolveThePuzzle() {
        List<PuzzleElementDefinition> idsList = new ArrayList<>();
        idsList.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        puzzleSolver.solve(idsList);
        assertEquals(puzzleSolver.getSolutionList(), expectedList);
    }


    //---------------------------------------------------------------------------------------

    @Test
    public void positiveSumOfAllElementEdgesIsZero() {

        List<PuzzleElementDefinition> listOfPuzzleElements = new ArrayList<>();
        listOfPuzzleElements.add(new PuzzleElementDefinition(1, 0, 1, 0, -1));
        listOfPuzzleElements.add(new PuzzleElementDefinition(1, -1, 1, 1, -1));
        assertTrue(Solver.isSumOfEdgesZero(listOfPuzzleElements), "sum of edges is not zero");
    }

    @Test
    public void negativeSumOfAllElementEdgesIsNotZero() {

        List<PuzzleElementDefinition> listOfPuzzleElements = new ArrayList<>();
        listOfPuzzleElements.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        listOfPuzzleElements.add(new PuzzleElementDefinition(1, -1, 1, -1, -1));
        assertFalse(Solver.isSumOfEdgesZero(listOfPuzzleElements), "sum of edges is zero");
        assertTrue(EventHandler.getEventList().contains(EventHandler.SUM_ZERO), "expected error message [" + EventHandler.SUM_ZERO + "] not found");
    }

    @Test
    public void positiveOneElementCornerTest() {
        List<PuzzleElementDefinition> listOfPuzzleElements = new ArrayList<>();
        listOfPuzzleElements.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        assertFalse(Solver.isMissingCornerElements(1, listOfPuzzleElements), "not all corners are present");
    }


    @ParameterizedTest
    @MethodSource("negativeOneElementPuzzle_MissingCornerElements")
    public void negativeOneElementPuzzle_MissingCornerElements(PuzzleElementDefinition puzzleElement, String corner1, String corner2) {
        List<PuzzleElementDefinition> listOfPuzzleElements = new ArrayList<>();
        listOfPuzzleElements.add(puzzleElement);
        assertTrue(Solver.isMissingCornerElements(1, listOfPuzzleElements), "all corners are present");
        assertTrue(EventHandler.getEventList().contains(EventHandler.MISSING_CORNER + corner1), "expected error message [" + EventHandler.MISSING_CORNER + corner1 + " ] not found");
        assertTrue(EventHandler.getEventList().contains(EventHandler.MISSING_CORNER + corner2), "expected error message [" + EventHandler.MISSING_CORNER + corner2 + " ] not found");
    }

    private static Stream<Arguments> negativeOneElementPuzzle_MissingCornerElements() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(0, 1, 0, 0, 0), "TL", "BL"),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 1, 0), "TR", "BR"),
                Arguments.of(new PuzzleElementDefinition(0, 0, 1, 0, 0), "TL", "TR"),
                Arguments.of(new PuzzleElementDefinition(0, 0, 0, 0, 1), "BL", "BR")
        );
    }

    @ParameterizedTest
    @MethodSource("negativeColumnElementPuzzle_MissingCornerElements")
    public void negativeColumnElementPuzzle_MissingCornerElements(int wide, List<String> corners, List<PuzzleElementDefinition> puzzleElements) {
        assertTrue(Solver.isMissingCornerElements(wide, puzzleElements), "all corners are present");
        assertTrue(corners.size() == EventHandler.getEventList().size(), "expected number of events is " + corners.size() + ", but was " + EventHandler.getEventList().size());
        for (String corner : corners) {
            assertTrue(EventHandler.getEventList().contains(EventHandler.MISSING_CORNER + corner), "expected error message [" + EventHandler.MISSING_CORNER + corner + " ] not found");
        }
    }

    private static Stream<Arguments> negativeColumnElementPuzzle_MissingCornerElements() {
        List<PuzzleElementDefinition> ped1_1x2 = new ArrayList<>();
        ped1_1x2.add(new PuzzleElementDefinition(1, 0, 0, 0, 0));
        ped1_1x2.add(new PuzzleElementDefinition(2, 0, -1, 0, -1));

        List<PuzzleElementDefinition> ped2_1x3 = new ArrayList<>();
        ped2_1x3.add(new PuzzleElementDefinition(1, 0, -1, 0, 0));
        ped2_1x3.add(new PuzzleElementDefinition(2, 0, 1, 0, 1));
        ped2_1x3.add(new PuzzleElementDefinition(3, 0, 0, 1, 1));

        List<PuzzleElementDefinition> ped3_1x4 = new ArrayList<>();
        ped3_1x4.add(new PuzzleElementDefinition(1, 0, 0, 0, -1));
        ped3_1x4.add(new PuzzleElementDefinition(2, 0, 0, 0, 1));
        ped3_1x4.add(new PuzzleElementDefinition(3, -1, 0, 0, -1));
        ped3_1x4.add(new PuzzleElementDefinition(4, 0, 0, -1, 0));

//        List<PuzzleElementDefinition> ped4_1x4 = new ArrayList<>();
//        ped4_1x4.add(new PuzzleElementDefinition(1, -1, -1, -1, -1));
//        ped4_1x4.add(new PuzzleElementDefinition(2, 0, -1, 1, 0));
//        ped4_1x4.add(new PuzzleElementDefinition(3, 0, 0, 0, 0));
//        ped4_1x4.add(new PuzzleElementDefinition(4, 0, 0, 0, 0));

        return Stream.of(
//                Arguments.of(1, Arrays.asList("TL", "TR"), ped1_1x2),
//                Arguments.of(1, Arrays.asList("TR"), ped2_1x3),
                Arguments.of(1, Arrays.asList("BR"), ped3_1x4)
//                Arguments.of(1, Arrays.asList("BL"), ped4_1x4)
        );
    }


    @Test
    public void negativeOneElement_AllCornersAreMissingTest() {
        List<PuzzleElementDefinition> listOfPuzzleElements = new ArrayList<>();
        listOfPuzzleElements.add(new PuzzleElementDefinition(1, 1, 1, -1, 1));
        assertTrue(Solver.isMissingCornerElements(1, listOfPuzzleElements), "not all corners are present");
        assertTrue(EventHandler.getEventList().contains(EventHandler.MISSING_CORNER + "BL"), "expected error message [" + EventHandler.MISSING_CORNER + " BL ] not found");
        assertTrue(EventHandler.getEventList().contains(EventHandler.MISSING_CORNER + "BR"), "expected error message [" + EventHandler.MISSING_CORNER + " BR ] not found");
        assertTrue(EventHandler.getEventList().contains(EventHandler.MISSING_CORNER + "TL"), "expected error message [" + EventHandler.MISSING_CORNER + " TL ] not found");
        assertTrue(EventHandler.getEventList().contains(EventHandler.MISSING_CORNER + "TR"), "expected error message [" + EventHandler.MISSING_CORNER + " TR ] not found");
    }

//    @Test
//    public void firstAmirTest() throws Exception {
//
//        File inputFile = new File("src\\test\\resources\\FirstAmirFile.txt");
//        String expectedFileToString = readFile("src\\test\\resources\\FirstAmirFileExpected.txt");
//        String actualFileToString = readFile("src\\test\\resources\\OutPutFile.txt");
//        List<PuzzleElementDefinition> list;
//        list = puzzleSolver.checkTheInputFile(inputFile);
//
//            puzzleSolver.solve(list);
//
//            puzzleSolver.addSolutionToFile();
//            puzzleSolver.writeErrorsToTheOutPutFile();
//
//    }

//        @Test
//        public void e2eNoCorner() throws Exception {
//            File inputFile = new File("src\\test\\resources\\SumOfEdgesNotZero.txt");
//            String expectedFileToString = readFile("src\\test\\resources\\FirstAmirFileExpected.txt");
//            String actualFileToString = readFile("src\\test\\resources\\OutPutFile.txt");
//            List<PuzzleElementDefinition>list;
//            list = puzzleSolver.checkTheInputFile(inputFile);
//
//            puzzleSolver.isSumOfEdgesZero(list);
//            puzzleSolver.writeErrorsToTheOutPutFile();
//
//    }
    static String readFile(String path)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }

}