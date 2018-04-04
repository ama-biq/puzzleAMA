package file;

import impl.EventHandler;
import impl.PuzzleElementDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileParserTest{

    File lineReadyForParse = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
    File valid3 = new File("src\\test\\resources\\validPuzzle3Peaces.txt");
    List<PuzzleElementDefinition> listOfPuzzleElementDefinitionsContainsId = new ArrayList<>();

    @BeforeEach
    public void beforeEach(){
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
    public void failGetNumOfElementsMoreThanOneSplit(String firstLine) {
        assertThrows(Exception.class,
                () -> {
                    FileParserUtils.getNumOfElements(firstLine);
                });


    }



    @ParameterizedTest
    @CsvSource({
            "numofelements=2",
            "NUMOFELEMENTS=2",
            "NumOfElements77=2",
            "Num Of Elements=3",
            "NumOfElementss=3"

    })
    public void failGetNumOfElementsExactPhrase(String firstLine) {
        assertThrows(Exception.class,
                () -> {
                    FileParserUtils.getNumOfElements(firstLine);
                });


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
    public void failGetNumOfElementsParseInt(String firstLine) {
        assertThrows(Exception.class,
                () -> {
                    FileParserUtils.getNumOfElements(firstLine);
                });


    }
//    @Test
//    public void failGetNumOfElementsParseInt() {
//        String firstLine = "NumOfElements=A";
//        assertThrows(Exception.class,
//                () -> {
//                    FileParserUtils.getNumOfElements(firstLine);
//                });
//
//
//    }
    @Test
    public void passCreatePEDFrom5Sides() throws Exception {
        PuzzleElementDefinition referencePed = new PuzzleElementDefinition(1, 0, 1, 0);
        String line = "0 1 0 1 0";
        PuzzleElementDefinition testPed = FileParserUtils.createPuzzleElementDefinition(line);
        assertEquals(testPed,referencePed);
    }

//    @ParameterizedTest
//    @CsvSource({
//            "0 0 0 0",
//            "1 1 1 1 1 1"
//    })
//    public void failCreatePEDWrongAmountOfSides(String line) throws Exception {
//        PuzzleElementDefinition testPed = FileParserUtils.createPuzzleElementDefinition(line);
//        assertFalse(getEventList().isEmpty());//TODO: Find a better validation
//    }

    @ParameterizedTest
    @MethodSource("positiveTestCheckIdValidity")
    public void positiveTestCheckIdValidity(PuzzleElementDefinition p1, PuzzleElementDefinition p2,
                                            PuzzleElementDefinition p3, PuzzleElementDefinition p4) throws Exception {
        setEdgesForFourElements(p1, p2, p3, p4);
        assertTrue(FileParserUtils.verifyPuzzleIDs(listOfPuzzleElementDefinitionsContainsId, 4));
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
        assertFalse(FileParserUtils.verifyPuzzleIDs(listOfPuzzleElementDefinitionsContainsId, 4));
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

}