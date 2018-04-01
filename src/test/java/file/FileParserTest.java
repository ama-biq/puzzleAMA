package file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class FileParserTest {

    File lineReadyForParse = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
    File valid3 = new File("src\\test\\resources\\validPuzzle3Peaces.txt");

    @BeforeEach
    public void beforeEach() {

    }

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



}