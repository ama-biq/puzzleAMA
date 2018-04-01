package file;

import impl.PuzzleElementDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileParserUtils {

    public static List<PuzzleElementDefinition> fileToPEDArray (File file) throws Exception {

        List<PuzzleElementDefinition> pedArray = new ArrayList<>();
        StringBuilder sb = FileUtils.readFile(file);
        String[] lines = sb.toString().split("\\n");
        for (String line : lines) {






        }











        return pedArray;
    }

//    public static void main(String[] args) {
//        String test = ",";
//        System.out.println(!test.trim().startsWith("#"));
//    }



    public static boolean isLineReadyForParse(String line) {
        return (!(line.trim().startsWith("#")) && !line.trim().isEmpty());
    }

    // TODO in case of error should return error
    public static int getNumOfElements(String firstLine) throws Exception {

        String split[] = firstLine.trim().split("=");
        if (split.length == 2) {
            if (split[0].trim().equals("NumOfElements")) {
                try {
                    return Integer.parseInt(split[1].trim());
                } catch (Exception e) {
                    throw  new Exception();
                }
            }else{
                throw new Exception();
            }
        }
        throw new Exception();
    }

    public static PuzzleElementDefinition getPuzzleElementDefinition(String line) throws Exception {
        int arr[] = new int[5];
        int j = 0;
        String split[] = line.trim().split("\\s+");
        if(split.length == 5) {
            for (String str : split) {
                try {
                    arr[j] = Integer.parseInt(str);
                    j++;
                }catch (Exception e){
                    throw new Exception();
                }
            }
        }else{
            throw new Exception();
        }
        PuzzleElementDefinition puzzleElementDefinition = new PuzzleElementDefinition(arr[0], arr[1], arr[2], arr[3], arr[4]);
        boolean isValid = verifyPuzzleElementDefinition(puzzleElementDefinition);

        if(isValid) {
            return puzzleElementDefinition;
        }
        throw new Exception();
    }


    public static boolean verifyPuzzleElementDefinition(PuzzleElementDefinition puzzleElementDefinition) throws Exception {

        throw new Exception();
    }

    public static boolean verifyPuzzleIDs(List<PuzzleElementDefinition> puzzleElementDefinition, int numOfElements) throws Exception {

        throw new Exception();
    }
}
