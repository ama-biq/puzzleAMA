package file;


import impl.EventHandler;
import impl.PuzzleElementDefinition;

import java.io.File;
import java.util.*;

public class FileParserUtils {

    private List<PuzzleElementDefinition> pedArray = new ArrayList<>();
//    private List<Integer> wrongElementsIds = new ArrayList<>();
    private List<Integer> wrongElementsFormat = new ArrayList<>();
    private List<Integer> mi = new ArrayList<>();
    private int numOfElements;
    private final int fake = Integer.MIN_VALUE;


    public FileParserUtils(int numOfElements) {
        this.numOfElements = numOfElements;
    }

    public FileParserUtils() {
    }


    public List<PuzzleElementDefinition> fileToPEDArray(File file) throws Exception {

        StringBuilder sb = FileUtils.readFile(file);
        String[] lines = sb.toString().split("\\n");
        for (String line : lines) {

            if (isLineReadyForParse(line)) {
                if (this.numOfElements == 0) {
                    this.numOfElements = getNumOfElements(line);
                } else {
                    PuzzleElementDefinition createdPED = createPuzzleElementDefinition(line);
                    if (createdPED != null) {
                        this.pedArray.add(createdPED);
                    }
                }
            }
        }


        if (verifyPuzzleIDs(this.pedArray, this.numOfElements) && wrongElementsFormat.isEmpty() && EventHandler.getEventList().isEmpty()) {

            return this.pedArray;
        }
        this.pedArray.clear();

        return this.pedArray;
    }


    public boolean isLineReadyForParse(String line) {
        return (!(line.trim().startsWith("#")) && !line.trim().isEmpty());
    }

    // TODO in case of error should return error
    public int getNumOfElements(String firstLine) throws Exception {
        int amountOfEqualSigns = firstLine.length() - firstLine.replace("=", "").length();
        String split[] = firstLine.trim().split("=");

        if (split.length == 2 && amountOfEqualSigns == 1) {
            if (split[0].trim().equals("NumOfElements")) {
                try {
                    return Integer.parseInt(split[1].trim());
                } catch (Exception e) {
                    EventHandler.addEventToList("Bad format for NumOfElements declaration line: " + firstLine);
                }
            } else {
                EventHandler.addEventToList("Bad format for NumOfElements declaration line: " + firstLine);
            }
        }
        EventHandler.addEventToList("Bad format for NumOfElements declaration line: " + firstLine);

        return fake;
    }

    public PuzzleElementDefinition createPuzzleElementDefinition(String line) throws Exception {
        boolean shouldCreatePED = true;
        int[] arr = new int[5];
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


    ArrayList<String> whichElementIdMissing(Set<Integer> sortedSet, int numOfElements) {
        Integer counter = 1;
        ArrayList<String> missingiDElementsList = new ArrayList<>();
        while (counter <= numOfElements) {
            if (!sortedSet.contains(counter)) {
                missingiDElementsList.add(Integer.toString(counter));
            }
            counter++;
        }
        return missingiDElementsList;
    }


    private int parsedId(String id, String line) {

        try {

            return Integer.parseInt(id);

        } catch (Exception e) {
            EventHandler.addEventToList("Bad format for puzzle piece line: " + line);
        }


        return fake;

    }

    private int validRangeEdge(String edge, int id, String line) {
        try {
            int parsedEdge = Integer.parseInt(edge);
            if (parsedEdge >= -1 && parsedEdge <= 1) {
                return parsedEdge;
            } else {
                this.wrongElementsFormat.add(id);
            }
        } catch (Exception e) {
            EventHandler.addEventToList("Bad format for puzzle piece line: " + line);
        }
        return fake;
    }


    public List<Integer> getWrongElementsFormat() {
        return wrongElementsFormat;
    }


}
