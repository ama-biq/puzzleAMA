package file;


import impl.PuzzleElementDefinition;

import java.io.File;
import java.util.*;

import static impl.EventHandler.addEventToList;
import static impl.EventHandler.getEventList;

public class FileParserUtils {

    private static List<PuzzleElementDefinition> pedArray = new ArrayList<>();
    private static int numOfElements;

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


        if (verifyPuzzleIDs(this.pedArray, this.numOfElements) && wrongElementsFormat.isEmpty() && EventHandler.getEventList().isEmpty()) {

            return this.pedArray;
        }
        this.pedArray.clear();

        return this.pedArray;
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
                    addEventToList("Bad format for NumOfElements declaration line: " + firstLine);
                }
            } else {
                addEventToList("Bad format for NumOfElements declaration line: " + firstLine);
            }
        }
        addEventToList("Bad format for NumOfElements declaration line: " + firstLine);

        return 0;
    }

    public static PuzzleElementDefinition createPuzzleElementDefinition(String line) throws Exception {
        int elementID = 0;
        boolean shouldCreatePED = true;
        int[] arr = new int[5];
        int j = 0;
        String split[] = line.trim().split("\\s+");
        if (split.length == 5) {
            int id = 0;
            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    id = parsedId(split[i], line);
                    if (id == fake) {
                        shouldCreatePED = false;
                        break;
                    } else {
                        arr[i] = id;
                    }
                } else {
                    int edge = validRangeEdge(split[i], id, line);
                    if (edge == fake) {
                        shouldCreatePED = false;
                        break;
                    } else {
                        arr[i] = edge;
                    }
                }
            }
            if (shouldCreatePED == true) {
                return new PuzzleElementDefinition(arr[0], arr[1], arr[2], arr[3], arr[4]);
            }
        }
        int id = parsedId(split[0], line);
        if (id != fake) {
            this.wrongElementsFormat.add(id);
        }
        return null;
    }

    public boolean verifyPuzzleIDs(List<PuzzleElementDefinition> puzzleElementDefinition, int numOfElements) throws Exception {
        Set<Integer> validSet = new HashSet<>();
//        try {
        for (PuzzleElementDefinition element : puzzleElementDefinition) {
            validSet.add(element.getId());
//            }
//        } catch (InputMismatchException e) {
            //todo write error message to the file - <moshe: The parseInt of the id is being checked at createPuzzleElementDefinition method>
        }
        TreeSet<Integer> sortedSet = new TreeSet<>(validSet);
        return (puzzleElementDefinition.size() == sortedSet.size() &&
                sortedSet.first() == Integer.valueOf(1) &&
                sortedSet.last() == sortedSet.size());
        // throw new Exception();
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
