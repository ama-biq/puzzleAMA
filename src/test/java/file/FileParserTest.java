package file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

public class FileParserTest {

    File lineReadyForParse = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
    File valid3 = new File("src\\test\\resources\\validPuzzle3Peaces.txt");

    @BeforeEach
    public void beforeEach(){

    }

    @ParameterizedTest
    @CsvSource({"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                "              AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                ",",
                "''",
                "AAAAAAAAAAA#                                      ",
                "AAAAAAAAAA#AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                "!@#$%^&*()__+QWERTYUIONJSNKJNKJkjsdnckasdnndajkdjn",
    })
    public void passIsLineReadyForParse(String line)  {
            assertTrue(FileParserUtils.isLineReadyForParse(line));
    }

    @Test
    public void readFile() throws Exception {
        StringBuilder expectedSb = new StringBuilder("NumOfElements=2\r\n" +
                "1 0 0 0 0\r\n" +
                "2 0 0 0 0");
        File file = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
        StringBuilder sb = FileUtils.readFile(file);
        Assertions.assertTrue(expectedSb.toString().equals(sb.toString()), "expected input file payload is - " + "{" + expectedSb + "}" + " actual is " + "{"+sb+"}");
    }

    @Test
    public void fileNotFound() throws Exception {
        File file = new File("src\\test\\resources\\fileNotExist.txt");
        Assertions.assertThrows(FileNotFoundException.class, ()-> {FileUtils.readFile(file);}, "expected exception is FileNotFoundException");

    }

    @Test
    public void badFolder() throws Exception {
        File file = new File("src\\test\\resources2\\validPuzzle2Peaces.txt");
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            FileUtils.readFile(file);
        }, "expected exception is FileNotFoundException");
    }

    public void failIsLineReadyForParse()  {
        String line = "#                    ";
        assertFalse(FileParserUtils.isLineReadyForParse(line));
    }


//    @ParameterizedTest
//    @CsvSource({
//            "                   ",
//            "#                  ",
//            "          #        ",
//            "#      AAAAAAA     ",
//            "#      AAAAAAA#####",
//            "           #AAAAAAA"})
//    public void failIsLineReadyForParse(String line)  {
//        assertFalse(FileParserUtils.isLineReadyForParse(line));
//    }

//    @Test
//    public void passGetNumOfElements() throws Exception {
//        String firstLine = "NumOfElements=3";
//        assertEquals(FileParserUtils.getNumOfElements(firstLine), 3);
//    }

    @ParameterizedTest
    @CsvSource({
            "NumOfElements=3",
            "NumOfElements=3            ",
            "            NumOfElements=3",
            "   NumOfElements=3         ",
            "  NumOfElements   =   3    "
    })
    public void passGetNumOfElements(String firstLine) throws Exception {
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
    public void failGetNumOfElements(String firstLine) throws Exception {
        assertEquals(FileParserUtils.getNumOfElements(firstLine), 3);
    }







    @Test
    public void failGetNumOfElements() throws Exception {
        String firstLine = "NumOfElements=2";
        assertNotEquals(FileParserUtils.getNumOfElements(firstLine), 3);
    }




}
