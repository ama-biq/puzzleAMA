package impl;


import file.FileParserUtils;

import java.io.File;
import java.util.*;

import static impl.EventHandler.addEventToList;

public class Solver {

    //    private List<PuzzleElementDefinition> testedList;
    private Map<Integer, List<PuzzleElementDefinition>> solverMap = new HashMap<>();
    private int fakeNumber = Integer.MAX_VALUE;
    private int maxRow;
    private int maxColumn;

    public List<Integer> getSolutionList() {
        return solutionList;
    }

    private List<Integer> solutionList = new ArrayList<>();

    public void solveThePuzzle(File inputFile) throws Exception {
        List<PuzzleElementDefinition> listAfterParser = FileParserUtils.fileToPEDArray(inputFile);
        //TODO to handle throwed exception
//        isEnoughCornerElementsForOneRow(listAfterParser);
//        isSumOfAllEdgesEqual(listAfterParser);
        if (isEnoughCornerElementsForOneRow(listAfterParser) && isSumOfAllEdgesEqual(listAfterParser))
            addEventToList("The pre-checks passed successfully.");

    }

    public boolean isSumOfAllEdgesIsZero(PuzzleElementDefinition puzzleElementDefinition) {
        //TODO check when to use
        int sum = puzzleElementDefinition.getLeft() +
                puzzleElementDefinition.getUp() +
                puzzleElementDefinition.getRight() +
                puzzleElementDefinition.getBottom();

        return sum == 0;
    }

    public boolean isSumOfAllEdgesIsZero(List<PuzzleElementDefinition> puzzleElements) {
        int sum = 0;
        for (PuzzleElementDefinition puzzleElement : puzzleElements) {
            sum = puzzleElement.getLeft() +
                    puzzleElement.getUp() +
                    puzzleElement.getRight() +
                    puzzleElement.getBottom();
        }
        if (sum != 0) {
            addEventToList(EventHandler.SUM_ZERO);
            return false;
        }
        return true;
    }

    public boolean isEnoughStraitEdges(PuzzleElementDefinition puzzleElementDefinition) {
        return (isAllElementDefinitionEqualsToZero(puzzleElementDefinition));
    }

    public boolean isEnoughCornerElementsForSeveralRows(List<PuzzleElementDefinition> listOfPuzzleElementDefinitions) {
        boolean isTLExists = false;
        boolean isTRExists = false;
        boolean isBLExists = false;
        boolean isBRExists = false;


        for (PuzzleElementDefinition element : listOfPuzzleElementDefinitions) {
            if (!isTLExists && element.isTLExistsOnSeveralRowsPuzzle()) {
                isTLExists = true;
            } else if (!isTRExists && element.isTRExistsOnSeveralRowsPuzzle()) {
                isTRExists = true;
            } else if (!isBLExists && element.isBLExistsOnSeveralRowsPuzzle()) {
                isBLExists = true;
            } else if (!isBRExists && element.isBRExistsOnSeveralRowsPazzle()) {
                isBRExists = true;
            }
        }
        if (!isTLExists) {
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.TL.getValue());
        }
        if (!isTRExists) {
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.TR.getValue());
        }
        if (!isBLExists) {
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.BL.getValue());
        }
        if (!isBRExists) {
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.BR.getValue());
        }
        return (isTLExists && isTRExists && isBLExists && isBRExists);
    }

    public boolean isEnoughCornerElementsForOneRow(List<PuzzleElementDefinition> listOfPuzzleElementDefinitions) {
        boolean isLeftCornerExists = false;
        boolean isRightCornerExists = false;
        boolean isOneOfElementsSquare = false;

        if (listOfPuzzleElementDefinitions.size() == 1) {
            return isAllElementDefinitionEqualsToZero(listOfPuzzleElementDefinitions.get(0));
        }
        if (isTwoElementsAreSquare(listOfPuzzleElementDefinitions)) {
            return true;
        }

        for (PuzzleElementDefinition element : listOfPuzzleElementDefinitions) {
            if (!isOneOfElementsSquare) {
                isOneOfElementsSquare = isAllElementDefinitionEqualsToZero(element);
            }
            if (!isLeftCornerExists && element.isLeftCornerExistsOnOneRowPuzzle()) {
                isLeftCornerExists = true;
            } else if (!isRightCornerExists && element.isRightCornerExistsOnOneRowPuzzle()) {
                isRightCornerExists = true;
            }
            if ((isOneOfElementsSquare && isLeftCornerExists) || (isOneOfElementsSquare && isRightCornerExists)) {
                return true;
            }
        }

        if (!isLeftCornerExists) {
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.TL.getValue());
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.BL.getValue());
            return false;
        } else if (!isRightCornerExists) {
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.BR.getValue());
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.TR.getValue());
            return false;
        } else {
            return true;
        }
    }


    private boolean isTwoElementsAreSquare(List<PuzzleElementDefinition> listOfPuzzleElementDefinitions) {
        int counter = 0;
        for (PuzzleElementDefinition elementDefinition : listOfPuzzleElementDefinitions) {
            if (isAllElementDefinitionEqualsToZero(elementDefinition)) {
                counter++;
            }
            if (counter == 2) {
                return true;
            }
        }
        return false;
    }


    public boolean isEnoughCornerElementsForOneColumn(List<PuzzleElementDefinition> listOfPuzzleElementDefinitions) {
        boolean isTopCornerExists = false;
        boolean isBottomCornerExists = false;
        boolean isOneOfElementsSquare = false;

        if (listOfPuzzleElementDefinitions.size() == 1) {
            return isAllElementDefinitionEqualsToZero(listOfPuzzleElementDefinitions.get(0));
        }

        if (isTwoElementsAreSquare(listOfPuzzleElementDefinitions)) {
            return true;
        }

        for (PuzzleElementDefinition element : listOfPuzzleElementDefinitions) {
            if (!isOneOfElementsSquare) {
                isOneOfElementsSquare = isAllElementDefinitionEqualsToZero(element);
            }
            if (!isTopCornerExists && element.isTopCornerExistsOnOneColumnPuzzle()) {
                isTopCornerExists = true;
            } else if (!isBottomCornerExists && element.isBottomCornerExistsOnOneColumnPuzzle()) {
                isBottomCornerExists = true;
            }
            if ((isOneOfElementsSquare && isTopCornerExists) || (isOneOfElementsSquare && isBottomCornerExists)) {
                return true;
            }
        }
        if (!isTopCornerExists) {
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.TL.getValue());
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.TR.getValue());
        }
        if (!isBottomCornerExists) {
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.BL.getValue());
            addEventToList(EventHandler.MISSING_CORNER + CornerNamesEnum.BR.getValue());
        }
        return (isTopCornerExists && isBottomCornerExists);
    }

    private boolean isAllElementDefinitionEqualsToZero(PuzzleElementDefinition puzzleElementDefinition) {
        return puzzleElementDefinition.getLeft() == 0 && puzzleElementDefinition.getUp() == 0 &&
                puzzleElementDefinition.getRight() == 0 && puzzleElementDefinition.getBottom() == 0;
    }

    public boolean isSumOfAllEdgesEqual(List<PuzzleElementDefinition> listOfPuzzleElementDefinitions) {
        int leftSum = 0;
        int upSum = 0;
        int rightSum = 0;
        int bottomSum = 0;

        for (PuzzleElementDefinition element : listOfPuzzleElementDefinitions) {
            leftSum += element.getLeft();
            upSum += element.getUp();
            rightSum += element.getRight();
            bottomSum += element.getBottom();
        }
        return (leftSum == 0 && rightSum == 0 && upSum == 0 && bottomSum == 0);

    }

    public boolean checkIdValidity(List<PuzzleElementDefinition> listToValid) {
        Set<Integer> validSet = new HashSet<>();
        try {
            for (PuzzleElementDefinition element : listToValid) {
                validSet.add(element.getId());
            }
        } catch (InputMismatchException e) {
            //todo write error message to the file
        }
        TreeSet<Integer> sortedSet = new TreeSet<>(validSet);
        return (listToValid.size() == sortedSet.size() &&
                sortedSet.last() == sortedSet.size());
    }

    public void resolveTheOneRowPuzzle(List<PuzzleElementDefinition> validIdList, PuzzleElementDefinition templateElement) {

        List<Integer> availableRows = getSolverRows(validIdList);
        for (Integer row : availableRows) {
            maxRow = row;
            maxColumn = validIdList.size() / row;
            int curRow = 1;
            List<PuzzleElementDefinition> testedList = copyList(validIdList);
            solveRow(testedList, templateElement, calcFactorial(maxColumn), curRow);
            if (!solverMap.isEmpty()) {
                int finalRowMapSize = solverMap.get(curRow).size();
                if (finalRowMapSize == maxColumn && curRow != maxRow) {// correct filled not last line
                    solveRow(testedList, templateElement, calcFactorial(maxColumn), ++curRow);
                } else if (finalRowMapSize == maxColumn && curRow == maxRow) {// correct filled last line and then stop flow, got solve result
                    solutionMapToSolutionList();
                    break;
                } else {
                    EventHandler.addEventToList(EventHandler.NO_SOLUTION);
                }
            } else {
                EventHandler.addEventToList(EventHandler.NO_SOLUTION);
            }
        }
    }

    public void solveRow(List<PuzzleElementDefinition> validIdList, PuzzleElementDefinition templateElement, int colNum, int curRow) {
        while (!validIdList.isEmpty() && colNum != 0) {
            for (int i = 0; i < validIdList.size() && colNum != 0; i++) {
                if (!isMatch(validIdList.get(i), templateElement)) {
                    validIdList = shiftElementToEndOfList(validIdList, validIdList.get(i));
                    solveRow(validIdList, templateElement, --colNum, curRow);
                } else {
                    addPEDToMap(validIdList.get(i), curRow);
                    PuzzleElementDefinition template = templateBuilder(validIdList.get(i));
                    validIdList.remove(validIdList.get(i));
                    if (!validIdList.isEmpty()) {
                        solveRow(validIdList, template, --colNum, curRow);
                    }
                }
            }
        }
    }

    private void addPEDToMap(PuzzleElementDefinition elementDefinition, int curRow) {

        List<PuzzleElementDefinition> elementList = solverMap.get(curRow);
        if (elementList == null) {
            elementList = new ArrayList<>();
        }
        elementList.add(elementDefinition);
        solverMap.put(curRow, elementList);

    }

    public void solutionMapToSolutionList() {
        for(Map.Entry<Integer, List<PuzzleElementDefinition>> entry : solverMap.entrySet()){
            List<PuzzleElementDefinition> listToPrint = entry.getValue();
            for (PuzzleElementDefinition element : listToPrint) {
                solutionList.add(element.getId());
            }
        }
    }

    private boolean isMatch(PuzzleElementDefinition currentElement, PuzzleElementDefinition templateElement) {
        int left = templateElement.getLeft();
        if ((left <= 1 || left >= -1) && left != fakeNumber) {
            if (!checkEdgeMatch(left, currentElement.getLeft())) {
                return false;
            }
        }
        int right = templateElement.getRight();
        if ((right <= 1 || right >= -1) && right != fakeNumber) {
            if (!checkEdgeMatch(right, currentElement.getRight())) {
                return false;
            }
        }
        int up = templateElement.getUp();
        if ((up <= 1 || up >= -1) && up != fakeNumber) {
            if (!checkEdgeMatch(up, currentElement.getUp())) {
                return false;
            }
        }
        int bottom = templateElement.getBottom();
        if ((bottom <= 1 || bottom >= -1) && bottom != fakeNumber) {
            if (!checkEdgeMatch(bottom, currentElement.getBottom())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkEdgeMatch(int currentElementEdge, int temlateEdge) {
        return currentElementEdge == temlateEdge;
    }

    private PuzzleElementDefinition nextElementBuilder(PuzzleElementDefinition element) {
        Integer leftEdge = element.getRight() * (-1);
        return new PuzzleElementDefinition(leftEdge, 0, fakeNumber, 0);
    }

    private List<PuzzleElementDefinition> copyList(List<PuzzleElementDefinition> validIdList) {
        List<PuzzleElementDefinition> retList = new ArrayList<>();
        for (PuzzleElementDefinition element : validIdList) {
            retList.add(element);
        }
        return retList;
    }

    private List<PuzzleElementDefinition> shiftElementToEndOfList(List<PuzzleElementDefinition> validIdList, PuzzleElementDefinition currentElement) {
        validIdList.remove(currentElement);
        validIdList.add(currentElement);
        return validIdList;
    }

    private static int calcFactorial(int i) {
        if (i == 1) {
            return 1;
        } else {
            return i * calcFactorial(i - 1);
        }
    }

    private PuzzleElementDefinition templateBuilder(PuzzleElementDefinition curElement) {
        int curRow = solverMap.size();
        if ((maxColumn - solverMap.size() == 1)) { //last column
            if (maxRow - curRow > 0 && curRow == 1) { //first row
                return new PuzzleElementDefinition(calcLeft(curElement), 0, 0, fakeNumber);
            } else if (maxRow - curRow > 0) { //middle row
                return new PuzzleElementDefinition(calcLeft(curElement), calcTop(curRow, maxColumn), 0, fakeNumber);
            } else if (maxRow - curRow == 0 && curRow == maxRow && maxRow == 1) { //puzzle one row
                return new PuzzleElementDefinition(calcLeft(curElement), 0, 0, 0);
            } else if (maxRow - curRow == 0) { //last row
                return new PuzzleElementDefinition(calcLeft(curElement), calcTop(curRow, maxColumn), 0, 0);
            }
        }

        if ((maxColumn - solverMap.size() > 1)) { //middle column
            if (maxRow - curRow > 0 && curRow == 1) { //first row
                return new PuzzleElementDefinition(calcLeft(curElement), 0, fakeNumber, fakeNumber);
            } else if (maxRow - curRow > 0) { //middle row
                return new PuzzleElementDefinition(calcLeft(curElement), calcTop(curRow, maxColumn), fakeNumber, fakeNumber);
            } else if (maxRow - curRow == 0 && curRow == maxRow && maxRow == 1) { //puzzle one row
                return new PuzzleElementDefinition(calcLeft(curElement), 0, fakeNumber, 0);
            } else if (maxRow - curRow == 0) { //last row
                return new PuzzleElementDefinition(calcLeft(curElement), calcTop(curRow, maxColumn), fakeNumber, 0);
            }
        }

        if ((maxColumn - solverMap.size() == 0 && curRow > 1)) { //puzzle one column
            //should exit row
            if (maxRow - curRow > 0) { //middle row
                return new PuzzleElementDefinition(0, calcTop(curRow, maxColumn), 0, fakeNumber);
            } else if (maxRow - curRow == 0) { //last row
                return new PuzzleElementDefinition(0, calcTop(curRow, maxColumn), 0, 0);
            }
        }
        return new PuzzleElementDefinition(0, 0, 0, 0);// check return statement
    }

    private int calcTop(int curRow, int curColumn) {
        List<PuzzleElementDefinition> list = solverMap.get(curRow - 1);
        PuzzleElementDefinition element = list.get(curColumn);
        return element.getBottom() * (-1);
    }

    private int calcLeft(PuzzleElementDefinition element) {
        return element.getRight() * (-1);
    }


    public static List<Integer> getSolverRows(List<PuzzleElementDefinition> puzzleElements) {
        List<Integer> retVal = new ArrayList<>();
        int numOfElements = puzzleElements.size();

        if (isPrime(numOfElements)) {
            retVal.add(1);
            retVal.add(numOfElements);
            return retVal;
        } else {
            return calculateRows(numOfElements);
        }
    }

    private static List<Integer> calculateRows(int numOfElements) {
        List<Integer> retVal = new ArrayList<>();
        for (int i = 1; i <= numOfElements; i++) {
            if (numOfElements % i == 0) {
                retVal.add(i);
            }
        }
        return retVal;
    }

    private static boolean isPrime(int number) {

        if (number < 4) {
            return true;
        }
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSumOfEdgesZero(List<PuzzleElementDefinition> puzzleElements) {
        int sum = 0;
        for (PuzzleElementDefinition puzzleElement : puzzleElements) {
            sum += (puzzleElement.getLeft() + puzzleElement.getUp() + puzzleElement.getRight() + puzzleElement.getBottom());
        }
        if (sum != 0) {
            EventHandler.addEventToList(EventHandler.SUM_ZERO);
        }
        return sum == 0;
    }

    //the method should solve/fill errors in case corners are missing
    public static boolean isMissingCornerElements(int wide, List<PuzzleElementDefinition> puzzleElements) {

        int numOfElements = puzzleElements.size();
        int height = numOfElements / wide;
        Map<CornerNamesEnum, Boolean> missingCorners = new HashMap<>();
        Map<CornerNamesEnum, List<PuzzleElementDefinition>> cornerElements = getCornerElements(wide, puzzleElements);

        if (numOfElements == 1) {
            if (!cornerElements.get(CornerNamesEnum.SQ).isEmpty()) {
                return false;
            } else {
                addMissingCornerErrorsIfExist(cornerElements);
                return true;
            }
        }
        if (wide == 1) {
            return isMissingColumnPuzzleCorners(cornerElements);
        } else if (height == 1) {
            return isMissingRowPuzzleCorners(cornerElements);
        } else {
            //TODO 2x2 and more
            return isMissingCornersInMap(cornerElements);
        }
    }

    private static boolean isMissingColumnPuzzleCorners(Map<CornerNamesEnum, List<PuzzleElementDefinition>> cornerElements) {
        int topCornersElement = cornerElements.get(CornerNamesEnum.TLTR).size();
        int bottomCornersElement = cornerElements.get(CornerNamesEnum.BLBR).size();
        int squareElements = cornerElements.get(CornerNamesEnum.SQ).size();
        if (squareElements >= 2 ||
                (topCornersElement != 0 && bottomCornersElement != 0) ||
                (topCornersElement != 0 && squareElements > 0) ||
                (bottomCornersElement != 0 && squareElements > 0)
                ) {
            return false;
        }
        if (squareElements == 1) {
            PuzzleElementDefinition puzzleElement = cornerElements.get(CornerNamesEnum.SQ).get(0);
            fillAvailableCornersMap(cornerElements, CornerNamesEnum.BL, puzzleElement);
            fillAvailableCornersMap(cornerElements, CornerNamesEnum.BR, puzzleElement);
        }
        addMissingCornerErrorsIfExist(cornerElements);
        return true;
    }

    private static boolean isMissingRowPuzzleCorners(Map<CornerNamesEnum, List<PuzzleElementDefinition>> cornerElements) {
        int leftCornersElement = cornerElements.get(CornerNamesEnum.TLBL).size();
        int rightCornersElement = cornerElements.get(CornerNamesEnum.TRBR).size();
        int squareElements = cornerElements.get(CornerNamesEnum.SQ).size();
        if (squareElements >= 2 ||
                (leftCornersElement != 0 && rightCornersElement != 0) ||
                (leftCornersElement != 0 && squareElements > 0) ||
                (rightCornersElement != 0 && squareElements > 0)
                ) {
            return false;
        }
        if (squareElements == 1) {
            PuzzleElementDefinition puzzleElement = cornerElements.get(CornerNamesEnum.SQ).get(0);
            fillAvailableCornersMap(cornerElements, CornerNamesEnum.TR, puzzleElement);
            fillAvailableCornersMap(cornerElements, CornerNamesEnum.BR, puzzleElement);
        }
        addMissingCornerErrorsIfExist(cornerElements);
        return true;
    }

    private static boolean isMissingCornersInMap(Map<CornerNamesEnum, List<PuzzleElementDefinition>> cornerElements) {
        int topLeftCornersElement = cornerElements.get(CornerNamesEnum.TL).size();
        int topRightCornersElement = cornerElements.get(CornerNamesEnum.TR).size();
        int bottomLeftCornersElement = cornerElements.get(CornerNamesEnum.BL).size();
        int bottomRightCornersElement = cornerElements.get(CornerNamesEnum.BR).size();
        int squareElements = cornerElements.get(CornerNamesEnum.SQ).size();
        if (squareElements >= 4 ||
                (topLeftCornersElement != 0 && topRightCornersElement != 0 && bottomLeftCornersElement != 0 && bottomRightCornersElement != 0) ||
                ((topLeftCornersElement != 0 || topRightCornersElement != 0 || bottomLeftCornersElement != 0 || bottomRightCornersElement != 0) && squareElements == 3)
                ) {
            return false;
        }


        if (squareElements == 2) {
            if (topLeftCornersElement == 0) {

            }
        }
        if (squareElements == 1) {
            PuzzleElementDefinition puzzleElement = cornerElements.get(CornerNamesEnum.SQ).get(0);
            fillAvailableCornersMap(cornerElements, CornerNamesEnum.TR, puzzleElement);
            fillAvailableCornersMap(cornerElements, CornerNamesEnum.BR, puzzleElement);
        }
        addMissingCornerErrorsIfExist(cornerElements);
        return true;
    }

    private static void addMissingCornerErrorsIfExist(Map<CornerNamesEnum, List<PuzzleElementDefinition>> cornerElements) {
        for (Map.Entry<CornerNamesEnum, List<PuzzleElementDefinition>> entry : cornerElements.entrySet()) {
            if (entry.getValue().isEmpty() && (entry.getKey().getValue().equals("TL") ||
                    entry.getKey().getValue().equals("TR") ||
                    entry.getKey().getValue().equals("BL") ||
                    entry.getKey().getValue().equals("BR"))
                    ) {
                EventHandler.addEventToList(EventHandler.MISSING_CORNER + entry.getKey().getValue());
            }
        }
    }

    private static Map<CornerNamesEnum, List<PuzzleElementDefinition>> getCornerElements(int wide, List<PuzzleElementDefinition> puzzleElements) {

        int height = puzzleElements.size() / wide;
        Map<CornerNamesEnum, List<PuzzleElementDefinition>> availableCorners = new HashMap<>();
        availableCorners.put(CornerNamesEnum.TL, new ArrayList<>());
        availableCorners.put(CornerNamesEnum.TR, new ArrayList<>());
        availableCorners.put(CornerNamesEnum.BL, new ArrayList<>());
        availableCorners.put(CornerNamesEnum.BR, new ArrayList<>());
        availableCorners.put(CornerNamesEnum.SQ, new ArrayList<>());
        availableCorners.put(CornerNamesEnum.TLTR, new ArrayList<>());
        availableCorners.put(CornerNamesEnum.BLBR, new ArrayList<>());
        availableCorners.put(CornerNamesEnum.TLBL, new ArrayList<>());
        availableCorners.put(CornerNamesEnum.TRBR, new ArrayList<>());
        for (PuzzleElementDefinition puzzleElement : puzzleElements) {
            if (isSquare(puzzleElement)) {
                fillAvailableCornersMap(availableCorners, CornerNamesEnum.SQ, puzzleElement);
            } else if (isTopLeft(puzzleElement) && isTopRight(puzzleElement)) {
                fillAvailableCornersMap(availableCorners, CornerNamesEnum.TLTR, puzzleElement);
                if (wide == 1 || height == 1) {
                    fillAvailableCornersMap(availableCorners, CornerNamesEnum.TL, puzzleElement);
                    fillAvailableCornersMap(availableCorners, CornerNamesEnum.TR, puzzleElement);
                }
            } else if (isBottomLeft(puzzleElement) && isBottomRight(puzzleElement)) {
                fillAvailableCornersMap(availableCorners, CornerNamesEnum.BLBR, puzzleElement);
                if (wide == 1 || height == 1) {
                    fillAvailableCornersMap(availableCorners, CornerNamesEnum.BL, puzzleElement);
                    fillAvailableCornersMap(availableCorners, CornerNamesEnum.BR, puzzleElement);
                }
            } else if (isTopLeft(puzzleElement) && isBottomLeft(puzzleElement)) {
                fillAvailableCornersMap(availableCorners, CornerNamesEnum.TLBL, puzzleElement);
                if (wide == 1 || height == 1) {
                    fillAvailableCornersMap(availableCorners, CornerNamesEnum.TL, puzzleElement);
                    fillAvailableCornersMap(availableCorners, CornerNamesEnum.BL, puzzleElement);
                }
            } else if (isTopRight(puzzleElement) && isBottomRight(puzzleElement)) {
                fillAvailableCornersMap(availableCorners, CornerNamesEnum.TRBR, puzzleElement);
                if (wide == 1 || height == 1) {
                    fillAvailableCornersMap(availableCorners, CornerNamesEnum.TR, puzzleElement);
                    fillAvailableCornersMap(availableCorners, CornerNamesEnum.BR, puzzleElement);
                }
            } else if (isTopLeft(puzzleElement)) {
                fillAvailableCornersMap(availableCorners, CornerNamesEnum.TL, puzzleElement);
            } else if (isTopRight(puzzleElement)) {
                fillAvailableCornersMap(availableCorners, CornerNamesEnum.TR, puzzleElement);
            } else if (isBottomLeft(puzzleElement)) {
                fillAvailableCornersMap(availableCorners, CornerNamesEnum.BL, puzzleElement);
            } else if (isBottomRight(puzzleElement)) {
                fillAvailableCornersMap(availableCorners, CornerNamesEnum.BR, puzzleElement);
            }
        }
        return availableCorners;
    }

    private static void fillAvailableCornersMap
            (Map<CornerNamesEnum, List<PuzzleElementDefinition>> availableCorners, CornerNamesEnum
                    corner, PuzzleElementDefinition puzzleElement) {
        List<PuzzleElementDefinition> puzzleElements = availableCorners.get(corner);
        puzzleElements.add(puzzleElement);
        availableCorners.putIfAbsent(corner, puzzleElements);
    }

    private static boolean isBottomRight(PuzzleElementDefinition puzzleElement) {
        return (puzzleElement.getRight() == 0 && puzzleElement.getBottom() == 0);
    }

    private static boolean isBottomLeft(PuzzleElementDefinition puzzleElement) {
        return (puzzleElement.getLeft() == 0 && puzzleElement.getBottom() == 0);
    }


    private static boolean isTopRight(PuzzleElementDefinition puzzleElement) {
        return (puzzleElement.getRight() == 0 && puzzleElement.getUp() == 0);
    }

    private static boolean isTopLeft(PuzzleElementDefinition puzzleElement) {
        return (puzzleElement.getLeft() == 0 && puzzleElement.getUp() == 0);
    }

    private static boolean isSquare(PuzzleElementDefinition puzzleElement) {
        return (puzzleElement.getLeft() == 0 &&
                puzzleElement.getUp() == 0 &&
                puzzleElement.getRight() == 0 &&
                puzzleElement.getBottom() == 0);
    }
}

