package pl.edu.agh.qtictactoe.model;

import android.util.Pair;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {

    private List<Integer> selectedX;
    private List<Integer> selectedO;
    private List<Pair<Integer, Integer>> pairsX;
    private List<Pair<Integer, Integer>> pairsO;
    private Integer roundNumber;
    private boolean hasConflict;
    private boolean isFinished;
    private boolean isXTurn;


}
