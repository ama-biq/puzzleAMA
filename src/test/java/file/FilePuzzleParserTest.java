package file;

import impl.EventHandler;
import impl.PuzzleElementDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FilePuzzleParserTest {

    File lineReadyForParse = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
    File valid2 = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
    File valid3 = new File("src\\test\\resources\\validPuzzle3Peaces.txt");
    File valid4 = new File("src\\test\\resources\\validPuzzle4Peaces.txt");
    File valid5 = new File("src\\test\\resources\\validPuzzle5Peaces.txt");
    File valid6 = new File("src\\test\\resources\\validPuzzle6Peaces.txt");
    File novalid6 = new File("src\\test\\resources\\novalidPuzzle6Peaces.txt");
    File novalid5 = new File("src\\test\\resources\\novalidPuzzle5Peaces.txt");

    List<PuzzleElementDefinition> listOfPuzzleElementDefinitionsContainsId = new ArrayList<>();
    EventHandler eventHandler = new EventHandler();
    FileUtils fileUtils = new FileUtils();

    @BeforeEach
    public void beforeEach() {
        eventHandler.emptyEventList();
    }

//////////////////////////////////////// FileUtils Tests ////////////////////////////////////////


    @Test
    public void readFile() throws Exception {
        StringBuilder expectedSb = new StringBuilder("NumElements=2\n" +
                "1 0 0 0 0\n" +
                "2 0 0 0 0");
        File file = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
        StringBuilder sb = fileUtils.readFile(file);
        Assertions.assertTrue(expectedSb.toString().equals(sb.toString()), "expected input file payload is - " + "{" + expectedSb + "}" + " actual is " + "{" + sb + "}");
    }

    @Test
    public void fileNotFound() throws Exception {
        File file = new File("src\\test\\resources\\fileNotExist.txt");
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            fileUtils.readFile(file);
        }, "expected exception is FileNotFoundException");

    }

    @Test
    public void badFolder() throws Exception {
        File file = new File("src\\test\\resources2\\validPuzzle2Peaces.txt");
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            fileUtils.readFile(file);
        }, "expected exception is FileNotFoundException");
    }

    //////////////////////////////////////// isLineReadyForParse() Tests ////////////////////////////////////////


    @ParameterizedTest
    @ValueSource(strings = {"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            "              AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            ",",
            "''",
            "AAAAAAAAAAA#                                      ",
            "AAAAAAAAAA#AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            "!@#$%^&*()__+QWERTYUIONJSNKJNKJkjsdnckasdnndajkdjn",
    })
    public void passIsLineReadyForParse(String line) {
        FilePuzzleParser testParser = new FilePuzzleParser();
        assertTrue(testParser.isLineReadyForParse(line));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "                   ",
            "#                  ",
            "          #        ",
            "#      AAAAAAA     ",
            "#      AAAAAAA#####",
            "           #AAAAAAA"})
    public void failIsLineReadyForParse(String line) {
        FilePuzzleParser testParser = new FilePuzzleParser();
        assertFalse(testParser.isLineReadyForParse(line));
    }

    @ParameterizedTest
    @CsvSource({
            "NumElements=3",
            "NumElements=3            ",
            "            NumElements=3",
            "   NumElements=3         ",
            "  NumElements   =   3    "
    })

    //////////////////////////////////////// getNumOfElements() Tests ////////////////////////////////////////


    public void passGetNumElementsNumValue(String firstLine) {
        FilePuzzleParser testParser = new FilePuzzleParser();
        assertEquals(3, testParser.getNumOfElements(firstLine));
    }

    @ParameterizedTest
    @CsvSource({
            "NumElements=3",
            "NumElements=3            ",
            "            NumElements=3",
            "   NumElements=3         ",
            "  NumElements   =   3    "
    })
    public void failGetNumElementsNumValue(String firstLine) {
        FilePuzzleParser testParser = new FilePuzzleParser();
        assertNotEquals(2, testParser.getNumOfElements(firstLine));
    }

    @ParameterizedTest
    @CsvSource({
            "NumElements",
            "NumElements=3=",
            "NumElements        =          3          =         ",
            "NumElements=3 NumElements=3",
            "            NumElements==3",
            "   =NumElements=3         ",
            "  ===NumElements   =   3    "
    })
    public void failGetNumElementsMoreThanOneSplit(String firstLine) {
        FilePuzzleParser testParser = new FilePuzzleParser();
        testParser.getNumOfElements(firstLine);


        assertTrue(eventHandler.getEventList().contains("Bad format for NumElements declaration line: " + firstLine));

    }


    @ParameterizedTest
    @CsvSource({
            "numofelements=2",
            "NUMOFELEMENTS=2",
            "NumElements77=2",
            "Num Of Elements=3",
            "NumElementss=3"

    })
    public void failGetNumElementsNotEqualNumElementsWord(String firstLine) {
        FilePuzzleParser testParser = new FilePuzzleParser();
        testParser.getNumOfElements(firstLine);
        assertTrue(eventHandler.getEventList().contains("Bad format for NumElements declaration line: " + firstLine));

    }


    @ParameterizedTest
    @CsvSource({
            "NumElements=A",
            "NumElements=a",
            "NumElements=23A",
            "NumElements=a a a",
            "NumElements=#",
            "NumElements=        A",
            "NumElements=        $",
    })
    public void failGetNumElementsParseInt(String firstLine) {
        FilePuzzleParser testParser = new FilePuzzleParser();
        testParser.getNumOfElements(firstLine);
        assertTrue(eventHandler.getEventList().contains("Bad format for NumElements declaration line: " + firstLine));


    }


    //////////////////////////////////////// createPuzzleElementDefinition() Tests ////////////////////////////////////////


    @ParameterizedTest
    @CsvSource({
            "0 0 0 0 0",
            "1 1 1 1 1",
            "2 -1 -1 -1 -1",
            "5 0 1 0 1",
            "20 1 1 0 0",
            "7 1 1 1 0",
            "9 1 0 0 0"
    })
    public void passCreatePED(String line) {
        String[] testLine = line.split("\\s+");
        System.out.println(Arrays.toString(testLine));
        int id = Integer.parseInt(testLine[0]);
        int left = Integer.parseInt(testLine[1]);
        int up = Integer.parseInt(testLine[2]);
        int right = Integer.parseInt(testLine[3]);
        int bottom = Integer.parseInt(testLine[4]);
        FilePuzzleParser testParser = new FilePuzzleParser();


        PuzzleElementDefinition referencePed = new PuzzleElementDefinition(id, left, up, right, bottom);
        PuzzleElementDefinition testPed = testParser.createPuzzleElementDefinition(line);
        assertEquals(referencePed, testPed);
    }

    @ParameterizedTest
    @CsvSource({
            "0 4 0 0 0",
            "1 7 1 1 1",
            "0 4 -8 0 0",
            "1 2 3 4 5",
            "5 0 0 5 5",
            "7 -3 0 0 1"
    })
    public void failEdgesOfPEDnotValid(String line) {
        FilePuzzleParser testParser = new FilePuzzleParser(7);
        String[] testLine = line.split("\\s+");
        PuzzleElementDefinition testPed = testParser.createPuzzleElementDefinition(line);
        int testedID = Integer.parseInt(testLine[0]);
        assertTrue(eventHandler.getEventList().contains("Puzzle ID " + testedID + " has wrong data: " + line));
        assertEquals(null, testPed);
    }

    @ParameterizedTest
    @CsvSource({
            "0 A 0 0 0",
            "B 1 1 1 1 "
    })
    public void failCreatePEDCannotParseIDOrEdge(String line) {
        FilePuzzleParser testParser = new FilePuzzleParser();
        PuzzleElementDefinition testPed = testParser.createPuzzleElementDefinition(line);
        assertTrue(eventHandler.getEventList().contains("Bad format for puzzle piece line: " + line));
        assertEquals(null, testPed);
    }


    @ParameterizedTest
    @CsvSource({
            "0 0 0 0 ",
            "1 1 1 1 1 1"
    })
    public void failCreatePEDWrongAmountOfEdgesCanParseID(String line) {
        String[] testLine = line.split("\\s+");
        int testedID = Integer.parseInt(testLine[0]);
        FilePuzzleParser testParser = new FilePuzzleParser();
        PuzzleElementDefinition testPed = testParser.createPuzzleElementDefinition(line);
        assertTrue(eventHandler.getEventList().contains("Bad format for puzzle piece line: " + line));
        assertEquals(null, testPed);
    }

    @ParameterizedTest
    @CsvSource({
            "A 0 0 0 ",
            "B 1 1 1 1 1"
    })
    public void failCreatePEDWrongAmountOfEdgesCannotParseID(String line) {
        FilePuzzleParser testParser = new FilePuzzleParser();
        PuzzleElementDefinition testPed = testParser.createPuzzleElementDefinition(line);
        assertTrue(eventHandler.getEventList().contains("Bad format for puzzle piece line: " + line));
        assertEquals(null, testPed);
    }


    //////////////////////////////////////// verifyPuzzleIDs() Tests ////////////////////////////////////////


    @ParameterizedTest
    @MethodSource("positiveTestCheckIdValidity")
    public void positiveTestCheckIdValidity(PuzzleElementDefinition p1, PuzzleElementDefinition p2,
                                            PuzzleElementDefinition p3, PuzzleElementDefinition p4) {
        setEdgesForFourElements(p1, p2, p3, p4);
        FilePuzzleParser testParser = new FilePuzzleParser();
        assertTrue(testParser.verifyPuzzleIDs(listOfPuzzleElementDefinitionsContainsId, 4));
    }

    private static Stream<Arguments> positiveTestCheckIdValidity() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(1, 1, 0, 0, 0),
                        new PuzzleElementDefinition(3, 0, 0, 0, 0),
                        new PuzzleElementDefinition(4, 1, 0, 0, 0),
                        new PuzzleElementDefinition(2, 1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(1, 1, 0, 0, 0),
                        new PuzzleElementDefinition(2, 0, 0, 0, 0),
                        new PuzzleElementDefinition(3, 1, 0, 0, 0),
                        new PuzzleElementDefinition(4, 1, 0, 0, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("negativeTestCheckIdValidity")
    public void negativeTestCheckIdValidity(PuzzleElementDefinition p1, PuzzleElementDefinition p2,
                                            PuzzleElementDefinition p3, PuzzleElementDefinition p4) {
        setEdgesForFourElements(p1, p2, p3, p4);
        FilePuzzleParser testParser = new FilePuzzleParser();
        assertFalse(testParser.verifyPuzzleIDs(listOfPuzzleElementDefinitionsContainsId, 4));
    }

    private static Stream<Arguments> negativeTestCheckIdValidity() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(1, 1, 0, 0, 0),
                        new PuzzleElementDefinition(1, 0, 0, 0, 0),
                        new PuzzleElementDefinition(4, 1, 0, 0, 0),
                        new PuzzleElementDefinition(2, 1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(1, 1, 0, 0, 0),
                        new PuzzleElementDefinition(6, 0, 0, 0, 0),
                        new PuzzleElementDefinition(3, 1, 0, 0, 0),
                        new PuzzleElementDefinition(5, 1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(1, 1, 0, 0, 0),
                        new PuzzleElementDefinition(2, 0, 0, 0, 0),
                        new PuzzleElementDefinition(3, 1, 0, 0, 0),
                        new PuzzleElementDefinition(0, 1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(1, 1, 0, 0, 0),
                        new PuzzleElementDefinition(2, 0, 0, 0, 0),
                        new PuzzleElementDefinition(3, 1, 0, 0, 0),
                        new PuzzleElementDefinition(-6, 1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(1, 1, 0, 0, 0),
                        new PuzzleElementDefinition(2, 0, 0, 0, 0),
                        new PuzzleElementDefinition(4, 1, 0, 0, 0),
                        new PuzzleElementDefinition(-6, 1, 0, 0, 0))
        );
    }

    private void setEdgesForFourElements(PuzzleElementDefinition ped1, PuzzleElementDefinition ped2,
                                         PuzzleElementDefinition ped3, PuzzleElementDefinition ped4) {
        listOfPuzzleElementDefinitionsContainsId.add(ped1);
        listOfPuzzleElementDefinitionsContainsId.add(ped2);
        listOfPuzzleElementDefinitionsContainsId.add(ped3);
        listOfPuzzleElementDefinitionsContainsId.add(ped4);
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3,4,5",
            "2,3,1,5,4",
            "5,4,3,2,1"
    })
    public void positiveTestWichElementMissing(int a, int b, int c, int d, int e) {
        FilePuzzleParser testParser = new FilePuzzleParser();
        TreeSet<Integer> setToValid = new TreeSet<>();
        setToValid.add(a);
        setToValid.add(b);
        setToValid.add(c);
        setToValid.add(d);
        setToValid.add(e);
        ArrayList<String> actualList = testParser.whichElementIdMissing(setToValid, setToValid.size());
        ArrayList<Integer> expectedList = new ArrayList<>();
        assertEquals(expectedList, actualList);

    }


    //////////////////////////////////////// fileToPEDArray() Tests ////////////////////////////////////////


    @Test
    public void passCreateListOfPEDsValidFile2Peaces() throws Exception {
        FilePuzzleParser testParser = new FilePuzzleParser();
        List<PuzzleElementDefinition> testList = testParser.fileToPEDArray(valid2);
        List<PuzzleElementDefinition> referenceList = new ArrayList<>();
        PuzzleElementDefinition referencePED1 = new PuzzleElementDefinition(1, 0, 0, 0, 0);
        PuzzleElementDefinition referencePED2 = new PuzzleElementDefinition(2, 0, 0, 0, 0);
        referenceList.add(referencePED1);
        referenceList.add(referencePED2);

        assertTrue(referenceList.containsAll(testList)
                && testList.containsAll(referenceList));

    }


    @Test
    public void passCreateListOfPEDsValidFile3Peaces() throws Exception {
        FilePuzzleParser testParser = new FilePuzzleParser();
        List<PuzzleElementDefinition> testList = testParser.fileToPEDArray(valid3);
        List<PuzzleElementDefinition> referenceList = new ArrayList<>();
        PuzzleElementDefinition referencePED1 = new PuzzleElementDefinition(1, 0, 0, 0, 0);
        PuzzleElementDefinition referencePED2 = new PuzzleElementDefinition(2, 0, 0, 0, 0);
        PuzzleElementDefinition referencePED3 = new PuzzleElementDefinition(3, 0, 1, 0, -1);
        referenceList.add(referencePED1);
        referenceList.add(referencePED2);
        referenceList.add(referencePED3);

        assertTrue(referenceList.containsAll(testList)
                && testList.containsAll(referenceList));

    }


    @Test
    public void passCreateListOfPEDsValidFile4Peaces() throws Exception {
        FilePuzzleParser testParser = new FilePuzzleParser();
        List<PuzzleElementDefinition> testList = testParser.fileToPEDArray(valid4);
        List<PuzzleElementDefinition> referenceList = new ArrayList<>();
        PuzzleElementDefinition referencePED1 = new PuzzleElementDefinition(1, 0, 0, 0, 0);
        PuzzleElementDefinition referencePED2 = new PuzzleElementDefinition(2, 0, 0, 0, 0);
        PuzzleElementDefinition referencePED3 = new PuzzleElementDefinition(3, 1, 1, 1, 1);
        PuzzleElementDefinition referencePED4 = new PuzzleElementDefinition(4, -1, 1, -1, 0);
        referenceList.add(referencePED1);
        referenceList.add(referencePED2);
        referenceList.add(referencePED3);
        referenceList.add(referencePED4);

        assertTrue(referenceList.containsAll(testList)
                && testList.containsAll(referenceList));

    }


    @Test
    public void passCreateListOfPEDsValidFile5Peaces() throws Exception {
        FilePuzzleParser testParser = new FilePuzzleParser();
        List<PuzzleElementDefinition> testList = testParser.fileToPEDArray(valid5);
        List<PuzzleElementDefinition> referenceList = new ArrayList<>();
        PuzzleElementDefinition referencePED1 = new PuzzleElementDefinition(1, 0, 0, 0, 0);
        PuzzleElementDefinition referencePED2 = new PuzzleElementDefinition(2, 0, 0, 0, 0);
        PuzzleElementDefinition referencePED3 = new PuzzleElementDefinition(3, 1, 1, 1, 1);
        PuzzleElementDefinition referencePED4 = new PuzzleElementDefinition(4, 0, 1, 0, 1);
        PuzzleElementDefinition referencePED5 = new PuzzleElementDefinition(5, -1, 1, -1, 0);
        referenceList.add(referencePED1);
        referenceList.add(referencePED2);
        referenceList.add(referencePED3);
        referenceList.add(referencePED4);
        referenceList.add(referencePED5);

        assertTrue(referenceList.containsAll(testList)
                && testList.containsAll(referenceList));

    }

    @Test
    public void passCreateListOfPEDsValidFile6Peaces() throws Exception {
        FilePuzzleParser testParser = new FilePuzzleParser();
        List<PuzzleElementDefinition> testList = testParser.fileToPEDArray(valid6);
        List<PuzzleElementDefinition> referenceList = new ArrayList<>();
        PuzzleElementDefinition referencePED1 = new PuzzleElementDefinition(1, 0, 0, 0, 0);
        PuzzleElementDefinition referencePED2 = new PuzzleElementDefinition(2, 0, 0, 0, 0);
        PuzzleElementDefinition referencePED3 = new PuzzleElementDefinition(3, 1, 0, 1, 0);
        PuzzleElementDefinition referencePED4 = new PuzzleElementDefinition(4, 1, 1, 1, 1);
        PuzzleElementDefinition referencePED5 = new PuzzleElementDefinition(5, 0, 1, 0, 1);
        PuzzleElementDefinition referencePED6 = new PuzzleElementDefinition(6, -1, 1, -1, 0);
        referenceList.add(referencePED1);
        referenceList.add(referencePED2);
        referenceList.add(referencePED3);
        referenceList.add(referencePED4);
        referenceList.add(referencePED5);
        referenceList.add(referencePED6);

        assertTrue(referenceList.containsAll(testList)
                && testList.containsAll(referenceList));

    }


    @Test
    public void failCreateListOfPEDsMissingElement() throws Exception {
        FilePuzzleParser testParser = new FilePuzzleParser();
        List<PuzzleElementDefinition> testList = testParser.fileToPEDArray(novalid6);

        assertTrue(testList.isEmpty());
    }

    @Test
    public void failCreateListOfPEDsMissingEdge() throws Exception {
        FilePuzzleParser testParser = new FilePuzzleParser();
        List<PuzzleElementDefinition> testList = testParser.fileToPEDArray(novalid5);

        assertTrue(testList.isEmpty());
    }


}






