package client;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Puzzle {
    private boolean Rotate;
    private List<Piece> Pieces;

    public Puzzle(boolean rotate, List<Piece> pieces) {
        Rotate = rotate;
        this.Pieces = pieces;
    }

    public static void main(String[] args) {
        List<Piece> listElements = new ArrayList<>();
        listElements.add(new Piece(1,1,1,0,1));
        listElements.add(new Piece(2,-1,1,0,1));
        Puzzle puzzleJson = new Puzzle(false, listElements);

        Gson gson = new Gson();
        String jsonString = gson.toJson(puzzleJson);
        System.out.println("Object -> String: " + jsonString);
    }
}
