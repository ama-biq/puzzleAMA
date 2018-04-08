package file;


import impl.PuzzleElementDefinition;

import java.io.File;
import java.util.*;

import static impl.EventHandler.addEventToList;

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
                    pedArray.add(createPuzzleElementDefinition(line));
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

    public static PuzzleElementDefinition createPuzzleElementDefinition(String line) throws Exception {
        int[] arr = new int[5];
        int j = 0;
        String split[] = line.trim().split("\\s+");
        if (split.length == 5) {
            for (String str : split) {
                try {
                    arr[j] = Integer.parseInt(str);
                    j++;
                } catch (Exception e) {
                    addEventToList("Puzzle ID " + arr[0] + " has wrong data.");//TODO  <complete line from file including ID>
//                    throw new Exception();
                }
            }
        } else {
            addEventToList("Puzzle ID " + arr[0] + " has wrong data.");//TODO  <complete line from file including ID>
//            throw new Exception();
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

        addEventToList("Puzzle ID " + puzzleElementDefinition.getId() + " has wrong data");//TODO  <complete line from file including ID>

        return false;
    }

    public static boolean verifyPuzzleIDs(List<PuzzleElementDefinition> puzzleElementDefinition, int numOfElements) throws Exception {
        boolean isIdsAreValid = true;
        Set<Integer> setToValid = new HashSet<>();
        try{
            for (PuzzleElementDefinition element : puzzleElementDefinition){
                setToValid.add(element.getId());
            }
        }catch (InputMismatchException e){
            //todo write error message to the file
        }
        List<String> missingElementIDlist = whichElementIdMissing(setToValid,numOfElements);
        if(missingElementIDlist.size() > 0){
            isIdsAreValid = false;
            addEventToList("Missing puzzle element(s) with the following IDs: " + missingElementIDlist.toString());
            System.out.println("Missing puzzle element(s) with the following IDs: " + missingElementIDlist.toString());
            //todo write error message to the file
         //  FileUtils.writeFile(missingElementIDlist);
            //"Missing puzzle element(s) with the following IDs:..."
        }
        ArrayList<String> wrongElementIdList = whichElementIdIsWrong(setToValid,numOfElements);
        if(wrongElementIdList.size() > 0){
            isIdsAreValid = false;
            //todo write error message to the file
            //"Missing puzzle element(s) with the following IDs:..."
            addEventToList("Puzzle of size "+puzzleElementDefinition.size()+ " cannot have the following IDs: " + wrongElementIdList.toString());
            System.out.println("Puzzle of size "+puzzleElementDefinition.size()+ " cannot have the following IDs:  " + wrongElementIdList.toString());
        }
        return isIdsAreValid;

    }

    static ArrayList<String> whichElementIdIsWrong(Set<Integer> sortedSet, int numOfElements) {
        ArrayList<String> wrongElementsIdList = new ArrayList<>();
        Iterator<Integer> iterator = sortedSet.iterator();
        while (iterator.hasNext()){
            Integer value = iterator.next();
            if( value > numOfElements || value <1){
                wrongElementsIdList.add(Integer.toString(value));
            }
        }
        return  wrongElementsIdList;
    }


    static ArrayList<String> whichElementIdMissing(Set<Integer> sortedSet, int numOfElements) {
        Integer counter = 1;
        ArrayList<String>missingiDElementsList = new ArrayList<>();
        while (counter <= numOfElements){
            if(! sortedSet.contains(counter)){
                missingiDElementsList.add(Integer.toString(counter));
            }
            counter++;
        }
        return missingiDElementsList;
    }
}
