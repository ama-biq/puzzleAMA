package json;

import com.google.gson.Gson;
import impl.PuzzleElementDefinition;

import java.util.ArrayList;
import java.util.List;

public class Puzzle {
    private boolean Rotate;
    private List<PuzzleElementDefinition>Pieces;

    public Puzzle(boolean rotate, List<PuzzleElementDefinition> pieces) {
        Rotate = rotate;
        Pieces = pieces;
    }

    public static void main(String[] args) {
        List<PuzzleElementDefinition> listElements = new ArrayList<>();
        listElements.add(new PuzzleElementDefinition(1,1,0,-1,1));
        listElements.add(new PuzzleElementDefinition(2,1,1,1,1));
        Puzzle puzzleJson = new Puzzle(true, listElements);

        Gson gson = new Gson();
        String jsonString = gson.toJson(puzzleJson);
        System.out.println("Object -> String: " + jsonString);
    }
}
