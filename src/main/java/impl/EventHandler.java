package impl;

import java.util.*;

public class EventHandler {

    public static final String SUM_ZERO = "Cannot solve puzzle: sum of edges is not zero";
    public static final String WRONG_STRAIGHT_EDGES = "Cannot solve puzzle: wrong number of straight edges";
    public static final String MISSING_CORNER = "Cannot solve puzzle: missing corner element: ";
    public static final String NO_SOLUTION = "Cannot solve puzzle: it seems that there is no proper solution";


    private static Set<String> eventList = new HashSet<>();
    private static Map<Integer, List<PuzzleElementDefinition>> solutionMap = new HashMap<>();

    public static void addEventToList(String error) {
        eventList.add(error);
    }

    public static Set<String> getEventList() {
        return eventList;
    }

    public static void emptyEventList(){
        eventList.clear();
    }
}
