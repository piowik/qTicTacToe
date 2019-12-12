package pl.edu.agh.qtictactoe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GameState implements Serializable {

    private int lasInsertedSign;
    private List<Integer> selectedX = new ArrayList<>();
    private List<Integer> selectedO = new ArrayList<>();
    private List<Move> movesX = new ArrayList<>();
    private List<Move> movesO = new ArrayList<>();
    private boolean hasConflict;
    private boolean isFinished;
    private boolean isXTurn;

    public void removeMove(Move moveToRemove, boolean isXMove) {
        if (isXMove) {
            movesX.remove(moveToRemove);
        } else {
            movesO.remove(moveToRemove);
        }
    }

}
