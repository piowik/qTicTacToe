package pl.edu.agh.qtictactoe;

import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;

public interface GameActivityInterface {
    boolean isSolvingLoop = false;

    void updateGame(GameState gameState);

    void yourTurn(int turnNumber);

    void solveLoop(Move move);

    void onWin(boolean isXWin);

    void startGame();

    void onMove(Move move);

    void onDraw();

    void onResolvedConflict(int[] x, int[] y);
}
