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


/**
 * The class design to solve puzzle with/without rotation and supports multi thread
 */
public class Solver {

    private AtomicBoolean solved;
    private int fakeNumber = Integer.MAX_VALUE;
    private static final String SEPARATOR = "_";
    private int maxRow;
    private int maxColumn;
    private Map<Position, List<PuzzleElementDefinition>> candidatePiecePool = new HashMap<>();
    private Map<Integer, List<PuzzleElementDefinition>> solutionMap = new HashMap<>();
    //static solutionMapTest for test use only
    private static Map<Integer, List<PuzzleElementDefinition>> solutionMapTest = new HashMap<>();


    private Map<String, List<PuzzleElementDefinition>> indexedPool = new HashMap<>();
    private List<Integer> usedIds = new ArrayList<>();

    Solver(AtomicBoolean solved) {
        this.solved = solved;
    }

    Solver() {
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

    /**
     * Perform index to the puzzle elements
     * If there is solution for puzzle write solution from  solution map to solution list
     * If there is no solution write the solution to output file
     *
     * @param puzzlePieces
     * @param solutionRowNumber
     * @param rotate
     * @param file
     * @return
     */
    boolean solve(List<PuzzleElementDefinition> puzzlePieces, int solutionRowNumber, boolean rotate, File file) {

        maxRow = solutionRowNumber;
        maxColumn = puzzlePieces.size() / solutionRowNumber;
        int startIndex = 0;
        candidatePiecePool.clear();
        indexPuzzlePieces(puzzlePieces, rotate);
        if (solvePuzzle(startIndex, solutionMap, rotate)) {
            solved.compareAndSet(false, true);
            solutionMapTest = solutionMap;
            try {
                writeSolutionToTheOutPutFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        solutionMap.clear();
        EventHandler.addEventToList(EventHandler.NO_SOLUTION);
        try {
            if (!solved.get()) {
                writeErrorsToTheOutPutFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean solvePuzzle(int index, Map<Integer, List<PuzzleElementDefinition>> solutionMap, boolean rotate) {
        while (!isPuzzleFull(solutionMap) && index >= 0 && !solved.get()) {
            Position position = new Position(getCurrentRowByIndex(index), getCurrentColumnByIndex(index));
            List<PuzzleElementDefinition> list = candidatePiecePool.get(position);
            if (list == null) {
                PuzzleElementDefinition template = getTemplateByIndex(index, solutionMap);
                buildCandidateElements(template, position);
            }
            if (candidatePiecePool.get(position).isEmpty()) {
                // no available elements for this position
                --index;
                if (!solutionMap.isEmpty()) {
                    handleNotMatchedPuzzlePiece(solutionMap, rotate, position);
                } else {
                    return false;
                }
            } else {
                handleMatchedPuzzlePiece(index, solutionMap, rotate, position);
                ++index;
            }
        }
        return isPuzzleSolved(solutionMap);
    }

    private void handleMatchedPuzzlePiece(int index, Map<Integer, List<PuzzleElementDefinition>> solutionMap, boolean rotate, Position position) {
        PuzzleElementDefinition curElement;
        curElement = candidatePiecePool.get(position).get(0);
        usedIds.add(curElement.getId());
        addElementToSolutionMap(curElement, index, solutionMap);
        removePieceFromIndexedMap(curElement, rotate);
        removeElementFromCandidatePool(position);
    }

    private void handleNotMatchedPuzzlePiece(Map<Integer, List<PuzzleElementDefinition>> solutionMap, boolean rotate, Position position) {
        PuzzleElementDefinition lastElement = getLastElementFromSolutionMap(solutionMap);
        deleteLastElementFromSolution(solutionMap);
        setPuzzlePieceToIndexedMap(lastElement, rotate);
        removeEmptyListFromPool(position);
        usedIds.remove(lastElement.getId());
    }

    private boolean isPuzzleSolved(Map<Integer, List<PuzzleElementDefinition>> solutionMap) {
        return isPuzzleFull(solutionMap);
    }

    // the method should be invoked in case of all positions of puzzle for current cycle is checked
    private void removeEmptyListFromPool(Position position) {
        List<PuzzleElementDefinition> list = candidatePiecePool.get(position);
        if (list.isEmpty()) {
            candidatePiecePool.remove(position);
        }
    }

    private void removeElementFromCandidatePool(Position position) {
        List<PuzzleElementDefinition> list = candidatePiecePool.get(position);
        list.remove(0);
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
        return getTotalElementsCount(solutionMap) == calcPuzzleSize();
    }

    private int calcPuzzleSize() {
        return maxColumn * maxRow;
    }

    private int getTotalElementsCount(Map<Integer, List<PuzzleElementDefinition>> solutionMap) {
        return solutionMap.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    private PuzzleElementDefinition leftCornerTemplate(int curRow, int curColumn, Map<Integer, List<PuzzleElementDefinition>> solverMap) {
        int filledRows = solverMap.size();
        if (curRow == maxRow && maxColumn == 1) {// last row 1 column puzzle
            return new PuzzleElementDefinition(0, calcMiddleTop(curRow, curColumn, solverMap), 0, 0);
        }
        if (isMiddleRow(curRow)) {
            if (maxColumn == 1) { //middle row 1 column puzzle
                return new PuzzleElementDefinition(0, calcMiddleTop(curRow, curColumn, solverMap), 0, fakeNumber);
            }
            if (curRow - filledRows == 1) {//starting fill new middle row
                return new PuzzleElementDefinition(0, calcMiddleTop(curRow, 0, solverMap), fakeNumber, fakeNumber);
            }
        }
        //last row more then 1 column
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
        //maxRow > 1 && maxColumn == 1
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

    private PuzzleElementDefinition templateBuilder(PuzzleElementDefinition curElement, Map<Integer, List<PuzzleElementDefinition>> solverMap, int curRow, int curColumn) {
        if (isLastColumn(curColumn)) {
            if (isFirstRow(curRow)) {
                return new PuzzleElementDefinition(calcLeft(curElement), 0, 0, fakeNumber);
            } else if (isMiddleRow(curRow)) {
                return new PuzzleElementDefinition(calcLeft(curElement), calcMiddleTop(curRow, curColumn, solverMap), 0, fakeNumber);
            } else if (isOneRowPuzzle(curRow)) {
                return new PuzzleElementDefinition(calcLeft(curElement), 0, 0, 0);
            } else if (isLastRow(curRow)) {
                return new PuzzleElementDefinition(calcLeft(curElement), calcMiddleTop(curRow, curColumn, solverMap), 0, 0);
            }
        }

        if (isMiddleColumn(curColumn)) {
            if (isOneRowPuzzle(curRow)) {
                return new PuzzleElementDefinition(calcLeft(curElement), 0, fakeNumber, 0);
            }
            if (isLastRow(curRow)) {
                return new PuzzleElementDefinition(calcLeft(curElement), calcMiddleTop(curRow, curColumn, solverMap), fakeNumber, 0);
            }
            if (isFirstRow(curRow)) {
                return new PuzzleElementDefinition(calcLeft(curElement), 0, fakeNumber, fakeNumber);
            }
            if (isMiddleRow(curRow)) {
                return new PuzzleElementDefinition(calcMiddleLeft(curRow, solverMap), calcMiddleTop(curRow, curColumn, solverMap), fakeNumber, fakeNumber);
            }
        }
        return new PuzzleElementDefinition(0, 0, 0, 0);// check return statement
    }

    private boolean isMiddleColumn(int curColumn) {
        return maxColumn - curColumn > 1;
    }

    private boolean isLastColumn(int curColumn) {
        return maxColumn - curColumn == 1;
    }

    private boolean isLastRow(int curRow) {
        return maxRow - curRow == 0;
    }

    private boolean isMiddleRow(int curRow) {
        return maxRow - curRow > 0;
    }

    private boolean isOneRowPuzzle(int curRow) {
        return maxRow - curRow == 0 && maxRow == 1;
    }

    private boolean isFirstRow(int curRow) {
        return isMiddleRow(curRow) && curRow == 1;
    }

    private int calcLeft(PuzzleElementDefinition element) {
        return element.getRight() * (-1);
    }

    private int calcMiddleLeft(int curRow, Map<Integer, List<PuzzleElementDefinition>> solverMap) {
        List<PuzzleElementDefinition> list = solverMap.get(curRow);
        int curColumn = list.size();
        PuzzleElementDefinition element = list.get(curColumn - 1);
        return element.getRight() * (-1);
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

    boolean validateStraightEdges(List<PuzzleElementDefinition> puzzleElements, int row, boolean rotate) {
//TODO rotate not supported yet
        if (row == 1) {
            return isStraightEdgesOneRow(puzzleElements);
        }
        if (row == puzzleElements.size()) {
            return isStraightEdgesOneColumn(puzzleElements);
        }
        //TODO no validation for other board
        return true;
    }

    private boolean isStraightEdgesOneColumn(List<PuzzleElementDefinition> puzzleElements) {
        for (PuzzleElementDefinition piece : puzzleElements) {
            if (piece.getLeft() != 0 || piece.getRight() != 0) {
                return false;
            }
        }
        //TODO missing validation for straight edges on sides(top and bottom)
        return true;
    }

    private boolean isStraightEdgesOneRow(List<PuzzleElementDefinition> puzzleElements) {
        for (PuzzleElementDefinition piece : puzzleElements) {
            if (piece.getUp() != 0 || piece.getBottom() != 0) {
                return false;
            }
        }
        //TODO missing validation for straight edges on sides(left and right)
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

    List<PuzzleElementDefinition> checkTheInputFile(File inputFile) throws Exception {
        FilePuzzleParser filePuzzleParser = new FilePuzzleParser();
        return filePuzzleParser.fileToPEDArray(inputFile);

    }

    void writeErrorsToTheOutPutFile() throws IOException {
        FileUtils.writeFile();
    }

    private void writeSolutionToTheOutPutFile(File file) throws IOException {
        FileUtils.writeSolutionToFile(solutionMap, file);
    }

    private PuzzleElementDefinition rotate(PuzzleElementDefinition element, int angle) {
        for (int i = 0; i < angle / 90; i++) {
            element = rotate90(element);
        }
        return element;
    }

    private PuzzleElementDefinition rotate90(PuzzleElementDefinition element) {

        int up = element.getLeft();
        int right = element.getUp();
        int bottom = element.getRight();
        int left = element.getBottom();
        int rotation = element.getRotationAngle() + 90;
        if (rotation == 360) {
            rotation = 0;
        }
        element.setLeft(left);
        element.setUp(up);
        element.setRight(right);
        element.setBottom(bottom);
        element.setRotationAngle(rotation);
        return new PuzzleElementDefinition(element.getId(), left, up, right, bottom, rotation);

    }

    private void buildCandidateElements(PuzzleElementDefinition template, Position position) {

        List<PuzzleElementDefinition> list = new ArrayList<>();
        String matcher = buildMatcher(template);

        for (Map.Entry<String, List<PuzzleElementDefinition>> entry : indexedPool.entrySet()) {
            if (entry.getKey().matches(matcher)) {
                List<PuzzleElementDefinition> tempList = entry.getValue();
                if (tempList != null) {
                    for (PuzzleElementDefinition p : tempList) {
                        if (!usedIds.contains(p.getId())) {
                            list.add(p);
                            break;
                        }
                    }
                }
            }
        }
        candidatePiecePool.put(position, list);
    }

    private String buildMatcher(PuzzleElementDefinition template) {
        return getMatcher(template.getLeft()) + SEPARATOR +
                getMatcher(template.getUp()) + SEPARATOR +
                getMatcher(template.getRight()) + SEPARATOR +
                getMatcher(template.getBottom());

    }

    private String getMatcher(int num) {
        return num == fakeNumber ? "(-?)[01]" : "" + num;
    }

    private void indexPuzzlePieces(List<PuzzleElementDefinition> puzzle, boolean rotate) {
        puzzle.forEach(piece -> setPuzzlePieceToIndexedMap(piece, rotate));
    }

    /**
     * If the rotate true all piece permutation inserted to the map after 90 degrees rotation.
     *
     * @param piece
     * @param rotate
     */
    private void setPuzzlePieceToIndexedMap(PuzzleElementDefinition piece, boolean rotate) {
        if (rotate) {
            if (isAllEdgesEquals(piece)) {
                setPuzzlePieceToIndexedMap(piece);
            } else if (isParallelEdgesEquals(piece) && !isAllEdgesEquals(piece)) {
                setPuzzlePieceToIndexedMap(piece);
                piece = getRotatedPuzzleElement(piece);
                setPuzzlePieceToIndexedMap(piece);
            } else {
                for (int i = 0; i < 4; i++) {
                    PuzzleElementDefinition newPiece = rotate90(piece);
                    setPuzzlePieceToIndexedMap(newPiece);
                }
            }
        } else {
            setPuzzlePieceToIndexedMap(piece);
        }
    }

    private boolean isParallelEdgesEquals(PuzzleElementDefinition piece) {
        return (piece.getBottom() == piece.getUp() && piece.getLeft() == piece.getRight());
    }

    private boolean isAllEdgesEquals(PuzzleElementDefinition piece) {
        return (piece.getLeft() == piece.getUp() && piece.getBottom() == piece.getRight() && piece.getLeft() == piece.getRight());
    }

    //add one piece to indexedPool
    private void setPuzzlePieceToIndexedMap(PuzzleElementDefinition piece) {
        String key = createPieceKey(piece);
        List<PuzzleElementDefinition> list = indexedPool.get(key);
        if (list == null) {
            list = new ArrayList<>();
        }
        PuzzleElementDefinition newPiece = new PuzzleElementDefinition(piece);
        list.add(newPiece);
        indexedPool.put(key, list);
    }

    private void removePieceFromIndexedMap(PuzzleElementDefinition piece, boolean rotate) {
        if (rotate) {
            if (isAllEdgesEquals(piece)) {
                removePieceFromIndexedMap(piece);
            } else if (isParallelEdgesEquals(piece) && !isAllEdgesEquals(piece)) {
                removePieceFromIndexedMap(piece);
                piece = getRotatedPuzzleElement(piece);
                removePieceFromIndexedMap(piece);
            } else {
                for (int i = 0; i < 4; i++) {
                    PuzzleElementDefinition newPiece = rotate90(piece);
                    removePieceFromIndexedMap(newPiece);
                }
            }
        } else {
            removePieceFromIndexedMap(piece);
        }
    }

    private PuzzleElementDefinition getRotatedPuzzleElement(PuzzleElementDefinition piece) {
        PuzzleElementDefinition newPiece = piece;
        if (piece.getRotationAngle() == 0) {
            newPiece = rotate90(piece);
        }
        if (piece.getRotationAngle() == 90) {
            newPiece = rotate(piece, 270);
        }
        return newPiece;
    }

    //remove one piece from indexedPool
    private void removePieceFromIndexedMap(PuzzleElementDefinition piece) {
        String key = createPieceKey(piece);
        List<PuzzleElementDefinition> list = indexedPool.get(key);
        list.remove(piece);
        if (!list.isEmpty()) {
            indexedPool.put(key, list);
        } else {
            indexedPool.remove(key);
        }
    }

    private String createPieceKey(PuzzleElementDefinition p) {
        return (p.getLeft()) + SEPARATOR +
                (p.getUp()) + SEPARATOR +
                (p.getRight()) + SEPARATOR +
                (p.getBottom());
    }

    Map<Integer, List<PuzzleElementDefinition>> getSolution() {
        return solutionMapTest;
    }
}

