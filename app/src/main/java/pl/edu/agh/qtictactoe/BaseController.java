package pl.edu.agh.qtictactoe;

import pl.edu.agh.qtictactoe.logic.GameLogic;
import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;

public abstract class BaseController {
    GameActivityInterface gameActivityInterface;
    GameLogic gameLogic;
    GameState gameState;
    abstract void onLoopSolved(int position);
    abstract void onMove(Move move);

    public BaseController() {
        gameState = new GameState();
        gameLogic = new GameLogic(gameState);
    }

    void attach(GameActivityInterface gameActivityInterface) {
        this.gameActivityInterface = gameActivityInterface;
    }

    void detach() {
        this.gameActivityInterface = null;
    }
}
