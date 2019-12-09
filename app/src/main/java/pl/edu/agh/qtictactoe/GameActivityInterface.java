package pl.edu.agh.qtictactoe;

import pl.edu.agh.qtictactoe.model.Move;

public interface GameActivityInterface {
    void updateGame();

    void yourTurn(int turnNumber);

    void solveLoop(Move move);

    void onWin(boolean hasWon);

    void startGame();
}
