package impl;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

    public static final String SUM_ZERO = "Cannot solve puzzle: sum of edges is not zero";
    public static final String WRONG_STRAIGHT_EDGES = "Cannot solve puzzle: wrong number of straight edges";
    public static final String MISSING_CORNER = "Cannot solve puzzle: missing corner element: ";
    public static final String NO_SOLUTION = "Cannot solve puzzle: it seems that there is no proper solution";


    public static List<String> errorList = new ArrayList<>();

    public static void addErrorToList(String error){
        errorList.add(error);
    }


}
