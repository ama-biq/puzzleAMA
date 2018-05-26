package impl;

import client.Piece;
import client.Puzzle;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class PuzzleSolution {
    private boolean SolutionExists;

    public PuzzleSolution(boolean solutionExists) {
        SolutionExists = solutionExists;
    }

    public PuzzleSolution() {

    }

    public static void main(String[] args) {
        new PuzzleSolution().getSolutionMessage();
    }
    public String getSolutionMessage(){
//        List<Piece> listElements = new ArrayList<>();
//        listElements.add(new Piece(1,1,1,0,1));
//        listElements.add(new Piece(2,-1,1,0,1));

        PuzzleSolution puzzleSolution = new PuzzleSolution();
        Gson gson = new Gson();
        String jsonString = gson.toJson(puzzleSolution);
       //  System.out.println("Object -> String: " + jsonString);

        PuzzleSolution puzzleFromJson = gson.fromJson(jsonString, PuzzleSolution.class);
        // System.out.println("Original object to string: " + puzzleFromJson.toString());

       // JsonElement jsonElement = gson.toJsonTree(puzzleJson);
        //  System.out.println("json to string!!! "+jsonElement.toString());
        return jsonString;
    }
}
