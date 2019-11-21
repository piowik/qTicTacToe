package pl.edu.agh.qtictactoe.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class GameState implements Serializable {

    private int lasInsertedSign;
    private List<Integer> selectedX;
    private List<Integer> selectedO;
    private List<Move> movesX;
    private List<Move> movesO;
    private Integer roundNumber;
    private boolean hasConflict;
    private boolean isFinished;
    private boolean isXTurn;


}
