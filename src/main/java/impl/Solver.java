package impl;


import file.FileParserUtils;

import java.io.File;
import java.util.*;

import static impl.EventHandler.addEventToList;
public class Solver {

    private List<PuzzleElementDefinition> testedList;
    private Map<Integer, ArrayList<PuzzleElementDefinition>> solutionMap = new HashMap<>();

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

    public void resolveTheOneRowPuzzle(List<PuzzleElementDefinition>validIdList, PuzzleElementDefinition templateElement){
        testedList=copyList(validIdList);
        solveRow(testedList,  templateElement, calcFactorial(6));
        int finalMapSize = solutionMap.get(1).size();
        if(finalMapSize == validIdList.size()){
            solutionMapToSolutionList();
        }else{
            System.out.println(EventHandler.NO_SOLUTION);
        }

    }


    // public List<Integer>solveRow(List<PuzzleElementDefinition>validIdList, PuzzleElementDefinition templateElement){
    public void solveRow(List<PuzzleElementDefinition>validIdList, PuzzleElementDefinition templateElement, int colNum){
        while (!validIdList.isEmpty() && colNum != 0) {
            for (int i = 0; i < validIdList.size() && colNum != 0; i++) {
                if (!isMatch(validIdList.get(i), templateElement)) {
                    validIdList = shiftElementToEndOfList(validIdList, validIdList.get(i));
                    solveRow(validIdList, templateElement, --colNum);
                } else {
                    addPEDToMap(validIdList.get(i));
                    PuzzleElementDefinition template = nextElementBuilder(validIdList.get(i));
                    validIdList.remove(validIdList.get(i));
                    if (!validIdList.isEmpty()) {
                        solveRow(validIdList, template, --colNum);
                    }
                }
            }
        }
    }

    private void addPEDToMap(PuzzleElementDefinition elementDefinition) {

        ArrayList<PuzzleElementDefinition>elementList = solutionMap.get(1);
        if(elementList == null) {
            elementList = new ArrayList<>();
        }
            elementList.add(elementDefinition);
            solutionMap.put(1, elementList);

    }

    public void solutionMapToSolutionList(){
        ArrayList<PuzzleElementDefinition> listToPrint = solutionMap.get(1);
        for (PuzzleElementDefinition element : listToPrint){
            solutionList.add(element.getId());
        }
    }

    private boolean isMatch(PuzzleElementDefinition currentElement, PuzzleElementDefinition templateElement) {
        int left = templateElement.getLeft();
        if((left <= 1 || left >=-1) && left != Integer.MAX_VALUE){
            if(!checkEdgeMatch(left, currentElement.getLeft())) {
                return false;
            }
        }
        int right = templateElement.getRight();
        if((right <= 1 || right >=-1)&& right != Integer.MAX_VALUE){
            if(!checkEdgeMatch(right, currentElement.getRight())){
                return false;
            }
        }
        int up = templateElement.getUp();
        if((up <= 1 || up >=-1)&& up != Integer.MAX_VALUE){
            if(!checkEdgeMatch(up, currentElement.getUp())){
                return false;
            }
        }
        int bottom = templateElement.getBottom();
        if((bottom <= 1 || bottom >=-1)&& bottom != Integer.MAX_VALUE){
            if(!checkEdgeMatch(bottom, currentElement.getBottom())){
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
        return new PuzzleElementDefinition(leftEdge,0,Integer.MAX_VALUE,0);
    }

    private List<PuzzleElementDefinition> copyList(List<PuzzleElementDefinition> validIdList) {
        List<PuzzleElementDefinition> retList=new ArrayList<>();
        for(PuzzleElementDefinition element: validIdList){
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

}

