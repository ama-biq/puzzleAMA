package json;

import com.google.gson.Gson;
import impl.PuzzleElementDefinition;

import java.beans.PersistenceDelegate;
import java.util.ArrayList;
import java.util.List;

public class Puzzle {
    private boolean Rotate;
    private List<Piece>Pieces;

    public Puzzle(boolean rotate, List<Piece> pieces) {
        Rotate = rotate;
        Pieces = pieces;
    }

    public static void main(String[] args) {
        List<Piece> listElements = new ArrayList<>();
        PuzzleElementDefinition ped1 = new PuzzleElementDefinition(1,1,0,1);
        PuzzleElementDefinition ped2 = new PuzzleElementDefinition(-1,1,0,1);
        listElements.add(new Piece(1,ped1));
        listElements.add(new Piece(2,ped2));
        Puzzle puzzleJson = new Puzzle(false, listElements);

        Gson gson = new Gson();
        String jsonString = gson.toJson(puzzleJson);
        System.out.println("Object -> String: " + jsonString);
    }
}
