package impl;


import file.FileParserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Solver {

    public List<String> errorsList = new ArrayList<>();

    public List<String> getErrorsList() {
        return errorsList;
    }


    public File solveThePuzzle(File inputFile) throws Exception {
        File outputFile = new File("src\\test\\resources\\OutPutFile.txt");
        List<PuzzleElementDefinition>listAfterParser = FileParserUtils.fileToPEDArray(inputFile);
        //TODO to handle throwed exception
        isEnoughCornerElementsForOneRow(listAfterParser);
        isSumOfAllEdgesEqual(listAfterParser);

        return outputFile;

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
        for(PuzzleElementDefinition puzzleElement : puzzleElements){
            sum = puzzleElement.getLeft() +
                    puzzleElement.getUp() +
                    puzzleElement.getRight() +
                    puzzleElement.getBottom();
        }
        if(sum != 0){
            addErrorMessageToErrorList("Cannot solve puzzle: sum of all edges is not zero");
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
            if (!isTLExists && element.isTLExistsOnSeveralRowsPazzle()) {
                isTLExists = true;
            } else if (!isTRExists && element.isTRExistsOnSeveralRowsPazzle()) {
                isTRExists = true;
            } else if (!isBLExists && element.isBLExistsOnSeveralRowsPazzle()) {
                isBLExists = true;
            } else if (!isBRExists && element.isBRExistsOnSeveralRowsPazzle()) {
                isBRExists = true;
            }
        }
        if (!isTLExists) {
            addErrorMessageToErrorList("Cannot solve puzzle: missing corner element for one row solution: TL");
        }
        if (!isTRExists) {
            addErrorMessageToErrorList("Cannot solve puzzle: missing corner element for one row solution: TR");
        }
        if (!isBLExists) {
            addErrorMessageToErrorList("Cannot solve puzzle: missing corner element for one row solution: BL");
        }
        if (!isBRExists) {
            addErrorMessageToErrorList("Cannot solve puzzle: missing corner element for one row solution: BR");
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
            if (!isLeftCornerExists && element.isLeftCornerExistsOnOneRowPazzle()) {
                isLeftCornerExists = true;
            } else if (!isRightCornerExists && element.isRightCornerExistsOnOneRowPazzle()) {
                isRightCornerExists = true;
            }
            if ((isOneOfElementsSquare && isLeftCornerExists) || (isOneOfElementsSquare && isRightCornerExists)) {
                return true;
            }
        }

        if (!isLeftCornerExists) {
            addErrorMessageToErrorList("Cannot solve puzzle: missing corner element for one row solution: Left Corner");
            return false;
        } else if (!isRightCornerExists) {
            addErrorMessageToErrorList("Cannot solve puzzle: missing corner element for one row solution: Right Corner");
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
                if (!isTopCornerExists && element.isTopCornerExistsOnOneColumnPazzle()) {
                    isTopCornerExists = true;
                } else if (!isBottomCornerExists && element.isBottomCornerExistsOnOneColumnPazzle()) {
                    isBottomCornerExists = true;
                }
                if ((isOneOfElementsSquare && isTopCornerExists) || (isOneOfElementsSquare && isBottomCornerExists)) {
                    return true;
                }
            }
            if (!isTopCornerExists) {
                addErrorMessageToErrorList("Cannot solve puzzle: missing corner element for one row solution: Top Corner");
            }
             if (!isBottomCornerExists) {
                addErrorMessageToErrorList("Cannot solve puzzle: missing corner element for one row solution: Bottom Corner");
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

    public void addErrorMessageToErrorList(String errorMessage) {
        errorsList.add(errorMessage);
    }

}

