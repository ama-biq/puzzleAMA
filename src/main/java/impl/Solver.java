package impl;


import file.FilePuzzleParser;
import file.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static impl.EventHandler.addEventToList;

public class Solver {

    private int fakeNumber = Integer.MAX_VALUE;
    private int maxRow;
    private int maxColumn;
    //
    private Map<Position, List<PuzzleElementDefinition>> poolMap = new HashMap<>();
    private Map<Integer, List<PuzzleElementDefinition>> solutionMap = new HashMap<>();
    private boolean lastColumn;
    private int maxRotationNumInFirstRow;

    List<Integer> getSolutionList() {
        return solutionList;
    }

    private List<Integer> solutionList = new ArrayList<>();

    public boolean isSumOfAllEdgesIsZero(PuzzleElementDefinition puzzleElementDefinition) {
        //TODO check when to use
        int sum = puzzleElementDefinition.getLeft() +
                puzzleElementDefinition.getUp() +
                puzzleElementDefinition.getRight() +
                puzzleElementDefinition.getBottom();

        return sum == 0;
    }


    boolean isEnoughStraitEdges(PuzzleElementDefinition puzzleElementDefinition) {
        return (isAllElementDefinitionEqualsToZero(puzzleElementDefinition));
    }

    boolean isEnoughCornerElementsForSeveralRows(List<PuzzleElementDefinition> listOfPuzzleElementDefinitions) {
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

    boolean isEnoughCornerElementsForOneRow(List<PuzzleElementDefinition> listOfPuzzleElementDefinitions) {
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


    boolean isEnoughCornerElementsForOneColumn(List<PuzzleElementDefinition> listOfPuzzleElementDefinitions) {
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

    protected boolean isSumOfAllEdgesEqual(List<PuzzleElementDefinition> listOfPuzzleElementDefinitions) {
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

    synchronized boolean solve(List<PuzzleElementDefinition> validIdList, int solutionRowNumber) {
        return solve(validIdList, solutionRowNumber, new AtomicBoolean(false));
    }
    synchronized boolean solve(List<PuzzleElementDefinition> validIdList, int solutionRowNumber, AtomicBoolean flag) {

        maxRow = solutionRowNumber;
        maxColumn = validIdList.size() / solutionRowNumber;
        lastColumn = false;
        int startIndex = 0;
        poolMap.clear();
        if (solvePuzzle(validIdList, startIndex, solutionMap)) {
            solutionMapToSolutionList(solutionMap);
            System.out.println("solutionMap" + solutionMap.toString());
            flag.compareAndSet(false, true);
            return true;
        }
        System.out.println("solutionMap False" + solutionMap.toString());
        solutionMap.clear();
        EventHandler.addEventToList(EventHandler.NO_SOLUTION);
        return false;
    }

    synchronized private boolean solvePuzzle(List<PuzzleElementDefinition> freePuzzleElements, int index, Map<Integer, List<PuzzleElementDefinition>> solutionMap) {
        while ((!freePuzzleElements.isEmpty() || !isPuzzleFull(solutionMap)) && index >= 0) {
            Position position = new Position(getCurrentRowByIndex(index), getCurrentColumnByIndex(index));
            PuzzleElementDefinition curElement;
            if (poolMap.get(position) == null) {
                PuzzleElementDefinition template = getTemplateByIndex(index, solutionMap);
                buildPositionElementMapByTemplate(template, freePuzzleElements, position);//find all elements that match to template
            }
            if (poolMap.get(position).isEmpty()) {
                // no available elements for this position
                --index;
                if (!solutionMap.isEmpty()) {
                    PuzzleElementDefinition lastElement = getLastElementFromSolutionMap(solutionMap);
                    freePuzzleElements = shiftElementToEndOfList(freePuzzleElements, lastElement);
                    deleteLastElementFromSolution(solutionMap);
                    removeEmptyListFromPool(position);
                } else {
                    return false;
                }
            } else {
                curElement = poolMap.get(position).get(0);
                addElementToSolutionMap(curElement, index, solutionMap);
                freePuzzleElements.remove(curElement);
                removeElementFromPool(position);
                ++index;
            }
        }
        return isPuzzleSolved(solutionMap);
    }

    private boolean isPuzzleSolved(Map<Integer, List<PuzzleElementDefinition>> solutionMap) {
        return isPuzzleFull(solutionMap);
    }

    // the method should be invoked in case of all positions of puzzle for current cycle is checked
    private void removeEmptyListFromPool(Position position) {
        List<PuzzleElementDefinition> list = poolMap.get(position);
        if (list.isEmpty()) {
            poolMap.remove(position);
        }
    }

    //find all elements that match to template and add them in to poolMap
    private void buildPositionElementMapByTemplate(PuzzleElementDefinition templateElement, List<PuzzleElementDefinition> currentElementList, Position position) {
        int left = templateElement.getLeft();
        int right = templateElement.getRight();
        int up = templateElement.getUp();
        int bottom = templateElement.getBottom();
        List<PuzzleElementDefinition> list = new ArrayList<>();

        for (PuzzleElementDefinition currentElement : currentElementList) {
            if (checkEdgeMatch(left, currentElement.getLeft()) &&
                    checkEdgeMatch(right, currentElement.getRight()) &&
                    checkEdgeMatch(up, currentElement.getUp()) &&
                    checkEdgeMatch(bottom, currentElement.getBottom())) {
                list.add(currentElement);
            }
        }
        poolMap.put(position, list);
    }

    private void removeElementFromPool(Position position) {
        List<PuzzleElementDefinition> list = poolMap.get(position);
        list.remove(0);
        poolMap.put(position, list);
    }

    private PuzzleElementDefinition getTemplateByIndex(int index, Map<Integer, List<PuzzleElementDefinition>> solverMap) {

        int curRow = getCurrentRowByIndex(index);
        int curColumn = getCurrentColumnByIndex(index);
        if (curRow == 1 && curColumn == 0) {//first left element in first row
            return leftFirstCornerTemplate();
        } else if (curColumn == 0) {//first left element middle or last row
            return leftCornerTemplate(curRow, curColumn, solverMap);
        }
        PuzzleElementDefinition lastElement = getLastElementFromSolutionMap(solverMap);
        return templateBuilder(lastElement, solverMap, curRow, curColumn);
    }

    private PuzzleElementDefinition getLastElementFromSolutionMap(Map<Integer, List<PuzzleElementDefinition>> solutionMap) {
        List<PuzzleElementDefinition> list = solutionMap.get(solutionMap.size());
        return list.get(list.size() - 1);
    }

    private void deleteLastElementFromSolution(Map<Integer, List<PuzzleElementDefinition>> solutionMap) {
        List<PuzzleElementDefinition> list = solutionMap.get(solutionMap.size());
        if (list.size() == 1) {
            solutionMap.remove(solutionMap.size());
        } else {
            list.remove(list.size() - 1);
        }
    }

    private boolean isPuzzleFull(Map<Integer, List<PuzzleElementDefinition>> solutionMap) {

        int count = 0;
        if (!solutionMap.isEmpty()) {
            for (Map.Entry<Integer, List<PuzzleElementDefinition>> entry : solutionMap.entrySet()) {
                if (entry.getValue() != null) {
                    count += entry.getValue().size();
                }
            }
            if (maxColumn * maxRow == count) {
                return true;
            }
        }
        return false;
    }

    private PuzzleElementDefinition leftCornerTemplate(int curRow, int curColumn, Map<Integer, List<PuzzleElementDefinition>> solverMap) {
        int filledRows = solverMap.size();
        if (curRow == maxRow && maxColumn == 1) {// last row 1 column puzzle
            return new PuzzleElementDefinition(0, calcMiddleTop(curRow, curColumn, solverMap), 0, 0);
        }
        if (maxRow - curRow > 0) {// middle row
            if (maxColumn == 1) { //middle row 1 column puzzle
                return new PuzzleElementDefinition(0, calcMiddleTop(curRow, curColumn, solverMap), 0, fakeNumber);
            }
            if (curRow - filledRows == 1) {//starting fill new middle row
                return new PuzzleElementDefinition(0, calcMiddleTop(curRow, 0, solverMap), fakeNumber, fakeNumber);
            }
        }
        //if(curRow == maxRow){ last row more then 1 column
        return new PuzzleElementDefinition(0, calcMiddleTop(curRow, 0, solverMap), fakeNumber, 0);
    }

    private PuzzleElementDefinition leftFirstCornerTemplate() {
        if (maxRow == 1 && maxColumn == 1) {
            return new PuzzleElementDefinition(0, 0, 0, 0);
        } else if (maxRow == 1 && maxColumn > 1) {
            return new PuzzleElementDefinition(0, 0, fakeNumber, 0);
        } else if (maxRow > 1 && maxColumn > 1) {
            return new PuzzleElementDefinition(0, 0, fakeNumber, fakeNumber);
        }
        //if(maxRow > 1 && maxColumn == 1){
        return new PuzzleElementDefinition(0, 0, 0, fakeNumber);
    }

    private int getCurrentRowByIndex(int index) {
        return index / maxColumn + 1;
    }

    private int getCurrentColumnByIndex(int index) {
        return index % maxColumn;
    }

    private void addElementToSolutionMap(PuzzleElementDefinition element, int index, Map<Integer, List<PuzzleElementDefinition>> solutionMap) {

        int curRow = getCurrentRowByIndex(index);
        List<PuzzleElementDefinition> elementList = solutionMap.get(curRow);
        if (elementList == null) {
            elementList = new ArrayList<>();
        }
        elementList.add(element);
        solutionMap.put(curRow, elementList);
    }


    private void solutionMapToSolutionList(Map<Integer, List<PuzzleElementDefinition>> solverMap) {
        for (Map.Entry<Integer, List<PuzzleElementDefinition>> entry : solverMap.entrySet()) {
            List<PuzzleElementDefinition> listToPrint = entry.getValue();
            for (PuzzleElementDefinition element : listToPrint) {
                solutionList.add(element.getId());
            }
        }
    }

    public void addSolutionToFile() {
        EventHandler.addEventToList(solutionList.toString());
    }

    private boolean checkEdgeMatch(int templateEdge, int currentElementEdge) {
        return templateEdge == fakeNumber || currentElementEdge == templateEdge;
    }

    private List<PuzzleElementDefinition> shiftElementToEndOfList(List<PuzzleElementDefinition> validIdList, PuzzleElementDefinition currentElement) {
        validIdList.remove(currentElement);
        validIdList.add(currentElement);
        return validIdList;
    }

    private PuzzleElementDefinition templateBuilder(PuzzleElementDefinition curElement, Map<Integer, List<PuzzleElementDefinition>> solverMap, int curRow, int curColumn) {
        if ((maxColumn - curColumn == 1)) { //last column
            if (maxRow - curRow > 0 && curRow == 1) { //first row
                return new PuzzleElementDefinition(calcLeft(curElement), 0, 0, fakeNumber);
            } else if (maxRow - curRow > 0) { //middle row
                return new PuzzleElementDefinition(calcLeft(curElement), calcMiddleTop(curRow, curColumn, solverMap), 0, fakeNumber);
            } else if (maxRow - curRow == 0 && maxRow == 1) { //puzzle one row
                return new PuzzleElementDefinition(calcLeft(curElement), 0, 0, 0);
            } else if (maxRow - curRow == 0) { //last row
                return new PuzzleElementDefinition(calcLeft(curElement), calcMiddleTop(curRow, curColumn, solverMap), 0, 0);
            }
        }

        if ((maxColumn - curColumn > 1)) { //middle column
            if (maxRow - curRow == 0 && maxRow == 1) { //puzzle one row
                return new PuzzleElementDefinition(calcLeft(curElement), 0, fakeNumber, 0);
            }
            if (maxRow - curRow == 0) { //last row
                return new PuzzleElementDefinition(calcLeft(curElement), calcMiddleTop(curRow, curColumn, solverMap), fakeNumber, 0);
            }
            if (maxRow - curRow > 0 && curRow == 1) { //first row
                return new PuzzleElementDefinition(calcLeft(curElement), 0, fakeNumber, fakeNumber);
            }
            if (maxRow - curRow > 0) { //middle row
                return new PuzzleElementDefinition(calcLeft(curElement), calcMiddleTop(curRow, curColumn, solverMap), fakeNumber, fakeNumber);
            }
        }
        if (maxColumn - curColumn == 0) { //puzzle one column
            lastColumn = true;
            //should exit row
        }
        return new PuzzleElementDefinition(0, 0, 0, 0);// check return statement
    }

    private int calcLeft(PuzzleElementDefinition element) {
        return element.getRight() * (-1);
    }

    private int calcMiddleLeft(int curRow, Map<Integer, List<PuzzleElementDefinition>> solverMap) {
        List<PuzzleElementDefinition> list = solverMap.get(curRow - 1);
        int curColumn = list.size();
        PuzzleElementDefinition element = list.get(curColumn - 1);
        return element.getLeft() * (-1);
    }

    private int calcMiddleTop(int curRow, int curColumn, Map<Integer, List<PuzzleElementDefinition>> solverMap) {
        List<PuzzleElementDefinition> list = solverMap.get(curRow - 1);
        PuzzleElementDefinition element = list.get(curColumn);
        return element.getBottom() * (-1);
    }

    // the method return puzzle available heights(rows) for all possible rectangles
    List<Integer> getSolverRows(List<PuzzleElementDefinition> puzzleElements) {
        List<Integer> retVal = new ArrayList<>();
        int numOfElements = puzzleElements.size();
        if (numOfElements == 1) {
            retVal.add(1);
            return retVal;
        }
        if (isPrime(numOfElements)) {
            retVal.add(1);
            retVal.add(numOfElements);
            return retVal;
        } else {
            return calculateRows(numOfElements);
        }
    }

    private List<Integer> calculateRows(int numOfElements) {
        List<Integer> retVal = new ArrayList<>();
        for (int i = 1; i <= numOfElements; i++) {
            if (numOfElements % i == 0) {
                retVal.add(i);
            }
        }
        return retVal;
    }

    private boolean isPrime(int number) {

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

    boolean isSumOfParallelEdgesZero(List<PuzzleElementDefinition> puzzleElements) {
        int sumVerticalEdges = 0;
        int sumHorizontalEdges = 0;
        for (PuzzleElementDefinition puzzleElement : puzzleElements) {
            sumHorizontalEdges += puzzleElement.getUp() + puzzleElement.getBottom();
            sumVerticalEdges += puzzleElement.getLeft() + puzzleElement.getRight();
        }
        if (sumHorizontalEdges != 0 || sumVerticalEdges != 0) {
            EventHandler.addEventToList(EventHandler.SUM_ZERO);
        }
        return (sumHorizontalEdges == 0 && sumVerticalEdges == 0);
    }

    //the method should solve/fill errors in case corners are missing
    boolean isMissingCornerElements(int wide, List<PuzzleElementDefinition> puzzleElements) {

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
            //TODO 2x2 and more should be developed
            //return isMissingCornersInMap(cornerElements);
            return false;
        }
    }

    private boolean isMissingColumnPuzzleCorners(Map<CornerNamesEnum, List<PuzzleElementDefinition>> cornerElements) {
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
        //TODO not work perfect
        return false;
    }

    private boolean isMissingRowPuzzleCorners(Map<CornerNamesEnum, List<PuzzleElementDefinition>> cornerElements) {
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
        //TODO not work perfect
        return false;
    }

    private boolean isMissingCornersInMap(Map<CornerNamesEnum, List<PuzzleElementDefinition>> cornerElements) {
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

    private void addMissingCornerErrorsIfExist(Map<CornerNamesEnum, List<PuzzleElementDefinition>> cornerElements) {
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

    Map<CornerNamesEnum, List<PuzzleElementDefinition>> getCornerElements(int wide, List<PuzzleElementDefinition> puzzleElements) {

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

    private void fillAvailableCornersMap
            (Map<CornerNamesEnum, List<PuzzleElementDefinition>> availableCorners, CornerNamesEnum
                    corner, PuzzleElementDefinition puzzleElement) {
        List<PuzzleElementDefinition> puzzleElements = availableCorners.get(corner);
        puzzleElements.add(puzzleElement);
        availableCorners.putIfAbsent(corner, puzzleElements);
    }

    private boolean isBottomRight(PuzzleElementDefinition puzzleElement) {
        return (puzzleElement.getRight() == 0 && puzzleElement.getBottom() == 0);
    }

    private boolean isBottomLeft(PuzzleElementDefinition puzzleElement) {
        return (puzzleElement.getLeft() == 0 && puzzleElement.getBottom() == 0);
    }


    private boolean isTopRight(PuzzleElementDefinition puzzleElement) {
        return (puzzleElement.getRight() == 0 && puzzleElement.getUp() == 0);
    }

    private boolean isTopLeft(PuzzleElementDefinition puzzleElement) {
        return (puzzleElement.getLeft() == 0 && puzzleElement.getUp() == 0);
    }

    private boolean isSquare(PuzzleElementDefinition puzzleElement) {
        return (puzzleElement.getLeft() == 0 &&
                puzzleElement.getUp() == 0 &&
                puzzleElement.getRight() == 0 &&
                puzzleElement.getBottom() == 0);
    }

    List<PuzzleElementDefinition> checkTheInputFile(File inputFile) throws Exception {
        FilePuzzleParser filePuzzleParser = new FilePuzzleParser();
        return filePuzzleParser.fileToPEDArray(inputFile);

    }

    void writeErrorsToTheOutPutFile() throws IOException {
        FileUtils.writeFile();
    }

    void writeSolutionToTheOutPutFile() throws IOException {
        FileUtils.writeSolutionToFile(solutionMap);
    }

    public Map<Integer, List<PuzzleElementDefinition>> getSolution() {
        return solutionMap;
    }
}

