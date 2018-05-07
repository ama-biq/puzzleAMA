package impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The class perform validation that solved puzzle has been solved correctly.
 */

public class PuzzleValidator {

    /**
     * The  validatePuzzleSolution  checks the next validation on solution map:
     * 1. The same element exists only once.
     * 2. All edges are straight.
     * 3. The left and right sides of adjacent elements are fitting.
     * 4. The top and bottom sides of elements are fitting.
     *
     * @return true if all validations passed successfully.
     */
    Solver solver = new Solver();
    private Map<Integer, List<PuzzleElementDefinition>> solutionMapTest;

     boolean validatePuzzleSolution() {
        boolean isValid = true;
        solutionMapTest = solver.getSolution();
        if(solutionMapTest.isEmpty()){return false;}
        Set<Integer> setOfIds = new HashSet<>();


        isValid = isTopAndButtomEdgesAreStraight();
        isValid = isLeftAndRightEdgesAreStraight(isValid);
        int maxRow = solutionMapTest.size();
        int maxColumn = solutionMapTest.get(1).size();
        for (int row = 0; row < maxRow; row++) {
            PuzzleElementDefinition prevPiece = null;
            for (int column = 0; column < maxColumn; column++) {
                PuzzleElementDefinition currentPiece = getPieceInSolutionMap(row, column);
                if (!setOfIds.add(currentPiece.getId())) {
                    System.out.println(currentPiece.getId() + " Already exists.");
                    return false;
                }
                if (prevPiece != null) {
                    if (!sumOfRightSideOfPrevPieceAndLeftSideOfCurrentPieceIsZero(prevPiece, currentPiece)) {
                        return false;
                    }
                }
                if (row + 1 < maxRow) {
                    PuzzleElementDefinition pieceOnTheNextRow = getPieceInSolutionMap(row + 1, column);
                    if (!sumOfCurrentPieceBottomAndPieceBellowToSideIsZero(currentPiece, pieceOnTheNextRow)) {
                        return false;
                    }
                }
                prevPiece = currentPiece;
            }
        }
        return isValid;
    }

    private boolean isTopAndButtomEdgesAreStraight() {
        int maxRow = solutionMapTest.size();
        return  (isTopEdgeIsStraight(solutionMapTest.get(1)) &&
                isButtomEdgeIsStraight(solutionMapTest.get(maxRow))) ;
    }

    private boolean isLeftAndRightEdgesAreStraight(boolean isValid) {
        for (Map.Entry<Integer, List<PuzzleElementDefinition>> entry : solutionMapTest.entrySet()) {
            List<PuzzleElementDefinition> listSolution = entry.getValue();
            if (!isLeftEdgeStraight(listSolution)) {
                isValid = false;
            }
            if (!isRightEdgeIsStraight(listSolution)) {
                isValid = false;
            }
        }
        return isValid;
    }

    private boolean sumOfCurrentPieceBottomAndPieceBellowToSideIsZero(PuzzleElementDefinition currentPiece, PuzzleElementDefinition pieceOnTheNextRow) {
        return currentPiece.getBottom() + pieceOnTheNextRow.getUp() == 0;

    }

    private boolean sumOfRightSideOfPrevPieceAndLeftSideOfCurrentPieceIsZero(PuzzleElementDefinition prevPiece, PuzzleElementDefinition currentPiece) {
        return prevPiece.getRight() + currentPiece.getLeft() == 0;
    }

    private PuzzleElementDefinition getPieceInSolutionMap(int row, int column) {

        List<PuzzleElementDefinition> list = solutionMapTest.get(row + 1);
        return list.get(column);

    }

    private boolean isRightEdgeIsStraight(List<PuzzleElementDefinition> listSolution) {
        int sizeList = listSolution.size();
        return listSolution.get(sizeList - 1).getRight() == 0;
    }

    private boolean isLeftEdgeStraight(List<PuzzleElementDefinition> listSolution) {
        return listSolution.get(0).getLeft() == 0;
    }

    private boolean isButtomEdgeIsStraight(List<PuzzleElementDefinition> solutionList) {
        for (PuzzleElementDefinition element : solutionList) {
            if (element.getBottom() != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isTopEdgeIsStraight(List<PuzzleElementDefinition> solutionList) {
        for (PuzzleElementDefinition element : solutionList) {
            if (element.getUp() != 0) {
                return false;
            }
        }
        return true;
    }
}
