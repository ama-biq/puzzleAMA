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

import static impl.EventHandler.getEventList;
import static org.junit.jupiter.api.Assertions.*;

public class FileParserTest {



    File lineReadyForParse = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
    File valid2 = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
    File valid3 = new File("src\\test\\resources\\validPuzzle3Peaces.txt");
    File valid4 = new File("src\\test\\resources\\validPuzzle4Peaces.txt");
    File valid5 = new File("src\\test\\resources\\validPuzzle5Peaces.txt");
    File valid6 = new File("src\\test\\resources\\validPuzzle6Peaces.txt");
    File novalid6 = new File("src\\test\\resources\\novalidPuzzle6Peaces.txt");
    File novalid5 = new File("src\\test\\resources\\novalidPuzzle5Peaces.txt");



    List<PuzzleElementDefinition> listOfPuzzleElementDefinitionsContainsId = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        EventHandler.emptyEventList();

    }

//////////////////////////////////////// FileUtils Tests ////////////////////////////////////////


    @Test
    public void readFile() throws Exception {
        StringBuilder expectedSb = new StringBuilder("NumOfElements=2\n" +
                "1 0 0 0 0\n" +
                "2 0 0 0 0");
        File file = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
        StringBuilder sb = FileUtils.readFile(file);
        Assertions.assertTrue(expectedSb.toString().equals(sb.toString()), "expected input file payload is - " + "{" + expectedSb + "}" + " actual is " + "{" + sb + "}");
    }

    @Test
    public void fileNotFound() throws Exception {
        File file = new File("src\\test\\resources\\fileNotExist.txt");
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            FileUtils.readFile(file);
        }, "expected exception is FileNotFoundException");

    }

    @Test
    public void badFolder() throws Exception {
        File file = new File("src\\test\\resources2\\validPuzzle2Peaces.txt");
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            FileUtils.readFile(file);
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
        FileParserUtils testParser = new FileParserUtils();
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
        FileParserUtils testParser = new FileParserUtils();
        assertFalse(testParser.isLineReadyForParse(line));
    }

    @ParameterizedTest
    @CsvSource({
            "NumOfElements=3",
            "NumOfElements=3            ",
            "            NumOfElements=3",
            "   NumOfElements=3         ",
            "  NumOfElements   =   3    "
    })

    //////////////////////////////////////// getNumOfElements() Tests ////////////////////////////////////////


    public void passGetNumOfElementsNumValue(String firstLine) throws Exception {
        FileParserUtils testParser = new FileParserUtils();
        assertEquals(testParser.getNumOfElements(firstLine), 3);
    }

    @ParameterizedTest
    @CsvSource({
            "NumOfElements=3",
            "NumOfElements=3            ",
            "            NumOfElements=3",
            "   NumOfElements=3         ",
            "  NumOfElements   =   3    "
    })
    public void failGetNumOfElementsNumValue(String firstLine) throws Exception {
        FileParserUtils testParser = new FileParserUtils();
        assertNotEquals(testParser.getNumOfElements(firstLine), 2);
    }

    @ParameterizedTest
    @CsvSource({
            "NumOfElements",
            "NumOfElements=3=",
            "NumOfElements        =          3          =         ",
            "NumOfElements=3 NumOfElements=3",
            "            NumOfElements==3",
            "   =NumOfElements=3         ",
            "  ===NumOfElements   =   3    "
    })
    public void failGetNumOfElementsMoreThanOneSplit(String firstLine) throws Exception {
        FileParserUtils testParser = new FileParserUtils();
        testParser.getNumOfElements(firstLine);


        assertTrue(getEventList().contains("Bad format for NumOfElements declaration line: " + firstLine));

    }


    @ParameterizedTest
    @CsvSource({
            "numofelements=2",
            "NUMOFELEMENTS=2",
            "NumOfElements77=2",
            "Num Of Elements=3",
            "NumOfElementss=3"

    })
    public void failGetNumOfElementsNotEqualNumOfElementsWord(String firstLine) throws Exception {
        FileParserUtils testParser = new FileParserUtils();
        testParser.getNumOfElements(firstLine);
        assertTrue(getEventList().contains("Bad format for NumOfElements declaration line: " + firstLine));

    }


    @ParameterizedTest
    @CsvSource({
            "NumOfElements=A",
            "NumOfElements=a",
            "NumOfElements=23A",
            "NumOfElements=a a a",
            "NumOfElements=#",
            "NumOfElements=        A",
            "NumOfElements=        $",
    })
    public void failGetNumOfElementsParseInt(String firstLine) throws Exception {
        FileParserUtils testParser = new FileParserUtils();
        testParser.getNumOfElements(firstLine);
        assertTrue(getEventList().contains("Bad format for NumOfElements declaration line: " + firstLine));


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
    public void passCreatePED(String line) throws Exception {
        String[] testLine = line.split("\\s+");
        System.out.println(Arrays.toString(testLine));
        int id = Integer.parseInt(testLine[0]);
        int left = Integer.parseInt(testLine[1]);
        int up = Integer.parseInt(testLine[2]);
        int right = Integer.parseInt(testLine[3]);
        int bottom = Integer.parseInt(testLine[4]);
        FileParserUtils testParser = new FileParserUtils();


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
    public void failEdgesOfPEDnotValid(String line) throws Exception {
        FileParserUtils testParser = new FileParserUtils(7);
        String[] testLine = line.split("\\s+");
        PuzzleElementDefinition testPed = testParser.createPuzzleElementDefinition(line);
        System.out.println(testParser.getWrongElementsFormat());
        int testedID = Integer.parseInt(testLine[0]);
        assertTrue(testParser.getWrongElementsFormat().contains(testedID));
        assertEquals(testPed, null);
    }

    @ParameterizedTest
    @CsvSource({
            "0 A 0 0 0",
            "B 1 1 1 1 "
    })
    public void failCreatePEDCannotParseIDOrEdge(String line) throws Exception {
        FileParserUtils testParser = new FileParserUtils();
        PuzzleElementDefinition testPed = testParser.createPuzzleElementDefinition(line);
        assertTrue(getEventList().contains("Bad format for puzzle piece line: " + line));
        assertEquals(testPed, null);
    }


    @ParameterizedTest
    @CsvSource({
            "0 0 0 0 ",
            "1 1 1 1 1 1"
    })
    public void failCreatePEDWrongAmountOfEdgesCanParseID(String line) throws Exception {
        String[] testLine = line.split("\\s+");
        int testedID = Integer.parseInt(testLine[0]);
        FileParserUtils testParser = new FileParserUtils();
        PuzzleElementDefinition testPed = testParser.createPuzzleElementDefinition(line);
        assertTrue(testParser.getWrongElementsFormat().contains(testedID));
        assertEquals(testPed, null);
    }

    @ParameterizedTest
    @CsvSource({
            "A 0 0 0 ",
            "B 1 1 1 1 1"
    })
    public void failCreatePEDWrongAmountOfEdgesCannotParseID(String line) throws Exception {
        FileParserUtils testParser = new FileParserUtils();
        PuzzleElementDefinition testPed = testParser.createPuzzleElementDefinition(line);
        assertTrue(getEventList().contains("Bad format for puzzle piece line: " + line));
        assertEquals(testPed, null);
    }


    //////////////////////////////////////// verifyPuzzleIDs() Tests ////////////////////////////////////////



    @ParameterizedTest
    @MethodSource("positiveTestCheckIdValidity")
    public void positiveTestCheckIdValidity(PuzzleElementDefinition p1, PuzzleElementDefinition p2,
                                            PuzzleElementDefinition p3, PuzzleElementDefinition p4) throws Exception {
        setEdgesForFourElements(p1, p2, p3, p4);
        FileParserUtils testParser = new FileParserUtils();
        assertTrue(testParser.verifyPuzzleIDs(listOfPuzzleElementDefinitionsContainsId, 4));
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
                                            PuzzleElementDefinition p3, PuzzleElementDefinition p4) throws Exception {
        setEdgesForFourElements(p1, p2, p3, p4);
        FileParserUtils testParser = new FileParserUtils();
        assertFalse(testParser.verifyPuzzleIDs(listOfPuzzleElementDefinitionsContainsId, 4));
    }

    private static Stream<Arguments> negativeTestCheckIdValidity() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 1,0, 0, 0, 0),
                        new PuzzleElementDefinition( 4,1, 0, 0, 0),
                        new PuzzleElementDefinition( 2,1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 6,0, 0, 0, 0),
                        new PuzzleElementDefinition( 3,1, 0, 0, 0),
                        new PuzzleElementDefinition( 5,1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 2,0, 0, 0, 0),
                        new PuzzleElementDefinition( 3,1, 0, 0, 0),
                        new PuzzleElementDefinition( 0,1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 2,0, 0, 0, 0),
                        new PuzzleElementDefinition( 3,1, 0, 0, 0),
                        new PuzzleElementDefinition( -6,1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition( 1,1, 0, 0, 0),
                        new PuzzleElementDefinition( 2,0, 0, 0, 0),
                        new PuzzleElementDefinition( 4,1, 0, 0, 0),
                        new PuzzleElementDefinition( -6,1, 0, 0, 0))
        );
    }

    private void setEdgesForFourElements(PuzzleElementDefinition ped1, PuzzleElementDefinition ped2,
                                         PuzzleElementDefinition ped3,PuzzleElementDefinition ped4) {
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
    public void positiveTestWichElementMissing(int a, int b, int c, int d, int e) throws Exception {
        FileParserUtils testParser = new FileParserUtils();
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
        FileParserUtils testParser = new FileParserUtils();
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
        FileParserUtils testParser = new FileParserUtils();
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
        FileParserUtils testParser = new FileParserUtils();
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
        FileParserUtils testParser = new FileParserUtils();
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
        FileParserUtils testParser = new FileParserUtils();
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
        FileParserUtils testParser = new FileParserUtils();
        List<PuzzleElementDefinition> testList = testParser.fileToPEDArray(novalid6);

        assertTrue(testList.isEmpty());
    }

    @Test
    public void failCreateListOfPEDsMissingEdge() throws Exception {
        FileParserUtils testParser = new FileParserUtils();
        List<PuzzleElementDefinition> testList = testParser.fileToPEDArray(novalid5);

        assertTrue(testList.isEmpty());
    }

}






