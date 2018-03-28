package file;

import org.junit.jupiter.api.Test;

import java.io.File;

public class FileParserTest {


    @Test
    public void test() throws Exception {
        File file = new File("src\\test\\resources\\validPuzzle2Peaces.txt");
        System.out.println(file.getAbsoluteFile());
        FileUtils.readFile(file);
    }

}
