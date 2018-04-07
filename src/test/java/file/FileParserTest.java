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
import java.util.stream.Stream;

import static impl.EventHandler.getEventList;
import static org.junit.jupiter.api.Assertions.*;

public class FileParserTest {

    File lineReadyForParse = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
    File valid3 = new File("src\\test\\resources\\validPuzzle3Peaces.txt");
    File valid2 = new File("src\\test\\resources\\validPuzzle2Peaces.txt");

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
        assertTrue(FileParserUtils.isLineReadyForParse(line));
    }


    public void failIsLineReadyForParse() {
        String line = "#                    ";
        assertFalse(FileParserUtils.isLineReadyForParse(line));
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
        assertFalse(FileParserUtils.isLineReadyForParse(line));
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
        assertEquals(FileParserUtils.getNumOfElements(firstLine), 3);
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
        assertNotEquals(FileParserUtils.getNumOfElements(firstLine), 2);
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
        FileParserUtils.getNumOfElements(firstLine);


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

        FileParserUtils.getNumOfElements(firstLine);
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
        FileParserUtils.getNumOfElements(firstLine);
        assertTrue(getEventList().contains("Bad format for NumOfElements declaration line: " + firstLine));


    }


    //////////////////////////////////////// createPuzzleElementDefinition() Tests ////////////////////////////////////////


    @ParameterizedTest
    @CsvSource({
            "0 0 0 0 0",
            "1 1 1 1 1",
            "-1 -1 -1 -1 1",
            "2 -1 -1 -1 -1",
            "5 0 1 0 1",
            "20 1 1 0 0",
            "7 1 1 1 0",
            "9 1 0 0 0",
    })
    public void passCreatePED(String line) throws Exception {
        String[] testLine = line.split("\\s+");
        System.out.println(Arrays.toString(testLine));
        int id = Integer.parseInt(testLine[0]);
        int left = Integer.parseInt(testLine[1]);
        int up = Integer.parseInt(testLine[2]);
        int right = Integer.parseInt(testLine[3]);
        int bottom = Integer.parseInt(testLine[4]);

        PuzzleElementDefinition referencePed = new PuzzleElementDefinition(id, left, up, right, bottom);
        PuzzleElementDefinition testPed = FileParserUtils.createPuzzleElementDefinition(line);
        assertEquals(testPed, referencePed);
    }

    @ParameterizedTest
    @CsvSource({
            "0 4 0 0 0",
            "1 7 1 1 1",
            "-2 4 0 0 0",
            "0 4 -8 0 0",
            "1 2 3 4 5",
            "-9 -9 -9 -9 -9",
            "5 0 0 5 5",
            "7 -3 0 0 1",
    })
    public void passCreatePEDFrom5SidesPEDNotValid(String line) throws Exception {
        String[] testLine = line.split("\\s+");
        PuzzleElementDefinition testPed = FileParserUtils.createPuzzleElementDefinition(line);
        assertTrue(getEventList().contains("Puzzle ID " + testLine[0] + " has wrong data: " + line));
        assertEquals(testPed, null);
    }

    @ParameterizedTest
    @CsvSource({
            "0 A 0 0 0",
            "B 1 1 1 1 "
    })
    public void failCreatePEDRightAmountOfSidesCannotParseID(String line) throws Exception {
        PuzzleElementDefinition testPed = FileParserUtils.createPuzzleElementDefinition(line);
        assertTrue(getEventList().contains("Bad format for puzzle piece line: " + line));
        assertEquals(testPed, null);
    }


    @ParameterizedTest
    @CsvSource({
            "0 0 0 0 ",
            "1 1 1 1 1 1"
    })
    public void failCreatePEDWrongAmountOfSidesCanParseID(String line) throws Exception {
        String[] testLine = line.split("\\s+");
        PuzzleElementDefinition testPed = FileParserUtils.createPuzzleElementDefinition(line);
        assertTrue(getEventList().contains("Puzzle ID " + testLine[0] + " has wrong data: " + line));
        assertEquals(testPed, null);
    }

    @ParameterizedTest
    @CsvSource({
            "A 0 0 0 ",
            "B 1 1 1 1 1"
    })
    public void failCreatePEDWrongAmountOfSidesCannotParseID(String line) throws Exception {
        PuzzleElementDefinition testPed = FileParserUtils.createPuzzleElementDefinition(line);
        assertTrue(getEventList().contains("Bad format for puzzle piece line: " + line));
        assertEquals(testPed, null);
    }


    //////////////////////////////////////// verifyPuzzleIDs() Tests ////////////////////////////////////////


    @ParameterizedTest
    @MethodSource("positiveTestCheckIdValidity")
    public void positiveTestCheckIdValidity(PuzzleElementDefinition p1, PuzzleElementDefinition p2,
                                            PuzzleElementDefinition p3, PuzzleElementDefinition p4) throws Exception {
        setEdgesForFourElements(p1, p2, p3, p4);
        assertTrue(FileParserUtils.verifyPuzzleIDs(listOfPuzzleElementDefinitionsContainsId, 4));
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
                                            PuzzleElementDefinition p3, PuzzleElementDefinition p4) throws Exception {
        setEdgesForFourElements(p1, p2, p3, p4);
        assertFalse(FileParserUtils.verifyPuzzleIDs(listOfPuzzleElementDefinitionsContainsId, 4));
    }

    private static Stream<Arguments> negativeTestCheckIdValidity() {
        return Stream.of(
                Arguments.of(new PuzzleElementDefinition(1, 1, 0, 0, 0),
                        new PuzzleElementDefinition(1, 0, 0, 0, 0),
                        new PuzzleElementDefinition(4, 1, 0, 0, 0),
                        new PuzzleElementDefinition(2, 1, 0, 0, 0)),
                Arguments.of(new PuzzleElementDefinition(1, 1, 0, 0, 0),
                        new PuzzleElementDefinition(2, 0, 0, 0, 0),
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


    //////////////////////////////////////// fileToPEDArray() Tests ////////////////////////////////////////


//    @ParameterizedTest
//    @CsvSource({
//            "src\\test\\resources\\validPuzzle2Peaces.txt",
//            "src\\test\\resources\\validPuzzle3Peaces.txt",
//            "src\\test\\resources\\validPuzzle4Peaces.txt",
//            "src\\test\\resources\\validPuzzle5Peaces.txt",
//            "src\\test\\resources\\validPuzzle6Peaces.txt",
//            1,
//            2,
//            3,
//            4,
//
//    })
//    public void passCreateListOfPEDsValidFile(String path, int id, int left, int top, int right, int bottom) throws Exception {
//        File testFile = new File(path);
////        File file3Peaces = new File("src\\test\\resources\\validPuzzle3Peaces.txt");
////        File file4Peaces = new File("src\\test\\resources\\validPuzzle4Peaces.txt");
////        File file5Peaces = new File("src\\test\\resources\\validPuzzle5Peaces.txt");
////        File file6Peaces = new File("src\\test\\resources\\validPuzzle6Peaces.txt");
//
//        List<PuzzleElementDefinition> testList = FileParserUtils.fileToPEDArray(testFile);
//        PuzzleElementDefinition referencePED1 = new PuzzleElementDefinition(1, 0, 0, 0, 0);
//        PuzzleElementDefinition referencePED2 = new PuzzleElementDefinition(2, 0, 0, 0, 0);
//
//
//
//
//        listOfPuzzleElementDefinitionsContainsId.add(referencePED1);
//        listOfPuzzleElementDefinitionsContainsId.add(referencePED2);
//
//        assertTrue(listOfPuzzleElementDefinitionsContainsId.containsAll(testList)
//                && testList.containsAll(listOfPuzzleElementDefinitionsContainsId));
//
//    }



    @Test
    public void passCreateListOfPEDsValidFile() throws Exception {
        List<PuzzleElementDefinition> testList = FileParserUtils.fileToPEDArray(valid2);
        PuzzleElementDefinition referencePED1 = new PuzzleElementDefinition(1, 0, 0, 0, 0);
        PuzzleElementDefinition referencePED2 = new PuzzleElementDefinition(2, 0, 0, 0, 0);
        listOfPuzzleElementDefinitionsContainsId.add(referencePED1);
        listOfPuzzleElementDefinitionsContainsId.add(referencePED2);

        assertTrue(listOfPuzzleElementDefinitionsContainsId.containsAll(testList)
                && testList.containsAll(listOfPuzzleElementDefinitionsContainsId));

    }







}






