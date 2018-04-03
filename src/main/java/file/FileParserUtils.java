package file;


import impl.EventHandler;
import impl.PuzzleElementDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileParserUtils {

    private static List<PuzzleElementDefinition> pedArray = new ArrayList<>();
    private static int numOfElements;
    private static boolean isParsedDataIntact;

    public static List<PuzzleElementDefinition> fileToPEDArray(File file) throws Exception {

        StringBuilder sb = FileUtils.readFile(file);
        String[] lines = sb.toString().split("\\n");
        for (String line : lines) {

            if (isLineReadyForParse(line)) {
                if (numOfElements == 0) {
                    numOfElements = getNumOfElements(line);
                } else {
                    pedArray.add(getPuzzleElementDefinition(line));
                }
            }
        }

        if (verifyPuzzleIDs(pedArray, numOfElements)) {

            return pedArray;
        }

        return pedArray;// Todo findout what to return in such case...
    }


    public static boolean isLineReadyForParse(String line) {
        return (!(line.trim().startsWith("#")) && !line.trim().isEmpty());
    }

    // TODO in case of error should return error
    public static int getNumOfElements(String firstLine) throws Exception {
        int amountOfEqualSigns = firstLine.length() - firstLine.replace("=", "").length();
        String split[] = firstLine.trim().split("=");

        if (split.length == 2 && amountOfEqualSigns == 1) {
            if (split[0].trim().equals("NumOfElements")) {
                try {
                    return Integer.parseInt(split[1].trim());
                } catch (Exception e) {
                    throw new Exception();
                }
            } else {
                throw new Exception();
            }
        }
        throw new Exception();
    }

    public static PuzzleElementDefinition getPuzzleElementDefinition(String line) throws Exception {
        int[] arr = new int[5];
        int j = 0;
        String split[] = line.trim().split("\\s+");
        if (split.length == 5) {
            for (String str : split) {
                try {
                    arr[j] = Integer.parseInt(str);
                    j++;
                } catch (Exception e) {
                    throw new Exception();
                }
            }
        } else {
            throw new Exception();
        }
        PuzzleElementDefinition puzzleElementDefinition = new PuzzleElementDefinition(arr[0], arr[1], arr[2], arr[3], arr[4]);
        boolean isValid = verifyPuzzleElementDefinition(puzzleElementDefinition);

        if (isValid) {

            return puzzleElementDefinition;
        }
        throw new Exception();
    }


    public static boolean verifyPuzzleElementDefinition(PuzzleElementDefinition puzzleElementDefinition) throws Exception {

        if (puzzleElementDefinition.getLeft() >= -1 && puzzleElementDefinition.getLeft() <= 1 &&
                puzzleElementDefinition.getUp() >= -1 && puzzleElementDefinition.getUp() <= 1 &&
                puzzleElementDefinition.getRight() >= -1 && puzzleElementDefinition.getRight() <= 1 &&
                puzzleElementDefinition.getBottom() >= -1 && puzzleElementDefinition.getBottom() <= 1) {

            return true;

        }

        EventHandler.addEventToList("Puzzle ID " + puzzleElementDefinition.getId() + " has wrong data: <complete line from file including ID>");

        return false;
    }

    public static boolean verifyPuzzleIDs(List<PuzzleElementDefinition> puzzleElementDefinition, int numOfElements) throws Exception {

        throw new Exception();
    }
}
