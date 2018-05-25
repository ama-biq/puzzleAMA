package file;


import impl.EventHandler;
import impl.PuzzleElementDefinition;

import java.io.File;
import java.util.*;

public class FilePuzzleParser {

    private static final String NUM_ELEMENTS = "NumElements";
    private List<PuzzleElementDefinition> pedArray = new ArrayList<>();
    private int numOfElements;
    private static final int fake = Integer.MIN_VALUE;


    FilePuzzleParser(int numOfElements) {
        this.numOfElements = numOfElements;
    }

    public FilePuzzleParser() {
    }

    public List<PuzzleElementDefinition> getPedArray() {
        return pedArray;
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
        if (verifyPuzzleIDs(this.pedArray, this.numOfElements) && EventHandler.getEventList().isEmpty()) {
            return this.pedArray;
        }
        this.pedArray.clear();
        return this.pedArray;
    }


    boolean isLineReadyForParse(String line) {
        return (!(line.trim().startsWith("#")) && !line.trim().isEmpty());
    }

    int getNumOfElements(String firstLine) {
        int amountOfEqualSigns = firstLine.length() - firstLine.replace("=", "").length();
        String[] split = firstLine.trim().split("=");

        if (split.length == 2 && amountOfEqualSigns == 1) {
            if (split[0].trim().equals(NUM_ELEMENTS)) {
                try {
                    return Integer.parseInt(split[1].trim());
                } catch (NumberFormatException e) {
                    EventHandler.addEventToList(EventHandler.BAD_FORMAT_FOR_NUM_ELEMENTS + firstLine);
                }
            } else {
                EventHandler.addEventToList(EventHandler.BAD_FORMAT_FOR_NUM_ELEMENTS + firstLine);
            }
        }
        EventHandler.addEventToList(EventHandler.BAD_FORMAT_FOR_NUM_ELEMENTS + firstLine);

        return fake;
    }

    PuzzleElementDefinition createPuzzleElementDefinition(String line) {
        boolean shouldCreatePED = true;
        int[] arr = new int[5];
        String[] split = line.trim().split("\\s+");
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
            if (shouldCreatePED) {
                return new PuzzleElementDefinition(arr[0], arr[1], arr[2], arr[3], arr[4]);
            }
        }
        EventHandler.addEventToList(EventHandler.BAD_FORMAT_PUZZLE_PIECES + line);
        return null;
    }

    private List<Integer> cantHaveTheFollowingID(TreeSet<Integer> sortedSet, int numOfElements) {
        Integer counter = 1;
        ArrayList<Integer> retList = new ArrayList<>();
        for (Integer element : sortedSet) {
            if (element > numOfElements) {
                retList.add(element);
            }
            counter++;
        }
        return retList;

    }

    boolean verifyPuzzleIDs(List<PuzzleElementDefinition> puzzleElementDefinition, int numOfElements) {
        boolean flag = true;
        Set<Integer> validSet = new HashSet<>();
        for (PuzzleElementDefinition element : puzzleElementDefinition) {
            validSet.add(element.getId());
        }
        TreeSet<Integer> sortedSet = new TreeSet<>(validSet);

        List<Integer> greatedThanNumOfElementIDList = cantHaveTheFollowingID(sortedSet, numOfElements);
        if (!greatedThanNumOfElementIDList.isEmpty()) {
            EventHandler.addEventToList("Puzzle of size " + numOfElements + " cannot have the following IDs: " + greatedThanNumOfElementIDList.toString().replace("[", "").replace("]", ""));
        }

        if (sortedSet.first() != Integer.valueOf(1) ||
                sortedSet.last() != sortedSet.size() ||
                sortedSet.size() != numOfElements) {
            List<String> missingElement = whichElementIdMissing(sortedSet, numOfElements);
            EventHandler.addEventToList("Missing puzzle element(s) with the following IDs: " + missingElement.toString().replace("[", "").replace("]", ""));
            flag = false;
        }

        return flag;
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
            EventHandler.addEventToList(EventHandler.BAD_FORMAT_PUZZLE_PIECES + line);
        }
        return fake;
    }

    private int validRangeEdge(String edge, int id, String line) {
        try {
            int parsedEdge = Integer.parseInt(edge);
            if (parsedEdge >= -1 && parsedEdge <= 1) {
                return parsedEdge;
            } else {
                EventHandler.addEventToList("Puzzle ID " + id + " has wrong data: " + line);
            }
        } catch (Exception e) {
            EventHandler.addEventToList("Bad format for puzzle piece line: " + line);
        }
        return fake;
    }


}
