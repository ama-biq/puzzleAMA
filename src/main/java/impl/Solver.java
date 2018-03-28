package impl;


import java.util.List;

public class Solver {

    private boolean isOneRow = true;

    public boolean isSumOfAllEdgesIsZero(PuzzleElementDefinition puzzleElementDefinition){
        int sum =
                puzzleElementDefinition.getLeft()+
                        puzzleElementDefinition.getUp()+
                        puzzleElementDefinition.getRight()+
                        puzzleElementDefinition.getBottom();
        return sum==0 ? true : false;
    }

    public boolean isEnoughStraitEdges(PuzzleElementDefinition puzzleElementDefinition){
        return   (isAllElementDefinitionEqualsToZero( puzzleElementDefinition));

    }


    public boolean isEnoughCornerElements(List<PuzzleElementDefinition> listOfPuzzleElementDefinitions) {
        boolean isLeftCornerExists = false;
        boolean isRightCornerExists = false;
        boolean isSquareExists = false;

        if(listOfPuzzleElementDefinitions.size()==1) {
            return isAllElementDefinitionEqualsToZero(listOfPuzzleElementDefinitions.get(0));
        }
        if(listOfPuzzleElementDefinitions.size() > 1 && isOneRow){
            for (PuzzleElementDefinition element : listOfPuzzleElementDefinitions){
                isSquareExists = isAllElementDefinitionEqualsToZero(element);
                if( !isLeftCornerExists && element.isLeftCornerOneLinePazzle()){
                    isLeftCornerExists=true;
                    //if(isSquareExists){return true;}
                }else if(!isRightCornerExists && element.isRightCornerOneLinePazzle()){
                    isRightCornerExists=true;
                   // if(isSquareExists){return true;}
                }
            }
            return isLeftCornerExists && isRightCornerExists;
        }
        return true;
    }

    private boolean isAllElementDefinitionEqualsToZero(PuzzleElementDefinition puzzleElementDefinition) {
        return puzzleElementDefinition.getLeft() == 0&& puzzleElementDefinition.getUp() == 0 &&
                puzzleElementDefinition.getRight() == 0 && puzzleElementDefinition.getBottom() == 0;
    }

}

