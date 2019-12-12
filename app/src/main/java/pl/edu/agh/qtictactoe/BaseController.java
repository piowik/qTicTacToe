package pl.edu.agh.qtictactoe;

import pl.edu.agh.qtictactoe.model.Move;

public abstract class BaseController {
    GameActivityInterface gameActivityInterface;

    abstract void onLoopSolved(int position);

    abstract void onMove(Move move);

    public BaseController() {
    }

    void attach(GameActivityInterface gameActivityInterface) {
        this.gameActivityInterface = gameActivityInterface;
    }

    void detach() {
        this.gameActivityInterface = null;
    }
}
