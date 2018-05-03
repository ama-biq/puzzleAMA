package file;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CmdPuzzleParserTest {

    @Test
    public void passProper7Args() {
        String[] args = {"-input", "inputValue", "-output", "outputValue", "-rotate", "-threads", "3"};
        CmdPuzzleParser referenceParser = new CmdPuzzleParser();
        referenceParser.setFileInputPath("inputValue");
        referenceParser.setFileOutputPath("outputValue");
        referenceParser.setRotate(true);
        referenceParser.setThreadAmount(3);
        CmdPuzzleParser testParser = new CmdPuzzleParser(args);
        assertEquals(referenceParser,testParser);
    }

    @Test
    public void passProper6ArgsWithoutThreads() {
        String[] args = {"-input", "inputValue", "-output", "outputValue", "-rotate"};
        CmdPuzzleParser referenceParser = new CmdPuzzleParser();
        referenceParser.setFileInputPath("inputValue");
        referenceParser.setFileOutputPath("outputValue");
        referenceParser.setRotate(true);
        CmdPuzzleParser testParser = new CmdPuzzleParser(args);
        assertEquals(referenceParser,testParser);
    }

    @Test
    public void passProper6ArgsWithoutRotate() {
        String[] args = {"-input", "inputValue", "-output", "outputValue", "-threads", "3"};
        CmdPuzzleParser referenceParser = new CmdPuzzleParser();
        referenceParser.setFileInputPath("inputValue");
        referenceParser.setFileOutputPath("outputValue");
        referenceParser.setThreadAmount(3);
        CmdPuzzleParser testParser = new CmdPuzzleParser(args);
        assertEquals(referenceParser,testParser);
    }

    @Test
    public void passProperOnly4Args() {
        String[] args = {"-input", "inputValue", "-output", "outputValue"};
        CmdPuzzleParser referenceParser = new CmdPuzzleParser();
        referenceParser.setFileInputPath("inputValue");
        referenceParser.setFileOutputPath("outputValue");
        CmdPuzzleParser testParser = new CmdPuzzleParser(args);
        assertEquals(referenceParser,testParser);
    }

    @Test
    public void pass7ArgsNotInOrder() {
        String[] args = {"-output", "outputValue","-input", "inputValue", "-threads", "3", "-rotate"};
        CmdPuzzleParser referenceParser = new CmdPuzzleParser();
        referenceParser.setFileInputPath("inputValue");
        referenceParser.setFileOutputPath("outputValue");
        referenceParser.setRotate(true);
        referenceParser.setThreadAmount(3);
        CmdPuzzleParser testParser = new CmdPuzzleParser(args);
        assertEquals(referenceParser,testParser);
    }

    @Test
    public void failOnlyOneArgument() {
        String[] args = {"-output", "outputValue"};
        CmdPuzzleParser testParser = new CmdPuzzleParser(args);
       // assertTrue(testParser.isParseFailed() == true);
    }
//
//    @Test
//    public void failNoArguments() {
//        String[] args = {""};
//        CmdPuzzleParser testParser = new CmdPuzzleParser(args);
////        assertTrue(testParser.isParseFailed() == true);
//    }

}
