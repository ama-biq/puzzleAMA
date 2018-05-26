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

    public String getSolutionMessage(){

        PuzzleSolution puzzleSolution = new PuzzleSolution();
        Gson gson = new Gson();
        String jsonString = gson.toJson(puzzleSolution);
        return jsonString;
    }
}
