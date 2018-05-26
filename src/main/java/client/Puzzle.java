package client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class Puzzle {
    private boolean Rotate;
    private List<Piece> Pieces;

    public Puzzle(boolean rotate, List<Piece> pieces) {
        Rotate = rotate;
        this.Pieces = pieces;
    }

    public Puzzle() {

    }

    @Override
    public String toString() {
        return "Puzzle{" +
                "Rotate=" + Rotate +
                ", Pieces=" + Pieces +
                '}';
    }

    public static void main(String[] args) {
        new Puzzle().createJson();
    }
    public String createJson(){
        List<Piece> listElements = new ArrayList<>();
        listElements.add(new Piece(1,1,1,0,1));
        listElements.add(new Piece(2,-1,1,0,1));
        Puzzle puzzleJson = new Puzzle(false, listElements);

        Gson gson = new Gson();
        String jsonString = gson.toJson(puzzleJson);
      //  System.out.println("Object -> String: " + jsonString);

        Puzzle puzzleFromJson = gson.fromJson(jsonString, Puzzle.class);
        // System.out.println("Original object to string: " + puzzleFromJson.toString());

        JsonElement jsonElement = gson.toJsonTree(puzzleJson);
      // System.out.println("json to string!!! "+jsonElement.toString());
        return jsonString;
    }
}
