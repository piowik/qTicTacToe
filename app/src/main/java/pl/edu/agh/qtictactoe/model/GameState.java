package pl.edu.agh.qtictactoe.model;

import android.util.Pair;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    public List<Integer> selectedX;
    public List<Integer> selectedO;
    public List<Pair<Integer, Integer>> pairsX;
    public List<Pair<Integer, Integer>> pairsO;
    public boolean hasConflict;
    public boolean isFinished;
    public boolean isXTurn;
}
