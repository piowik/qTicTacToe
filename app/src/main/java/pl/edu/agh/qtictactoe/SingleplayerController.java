package pl.edu.agh.qtictactoe;

import android.util.Log;

import pl.edu.agh.qtictactoe.logic.GameLogic;
import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;
import pl.edu.agh.qtictactoe.model.Winner;

public class SingleplayerController extends BaseController {
    private int moveCounter;
    private Move lastMove;
    private GameLogic gameLogic = new GameLogic(new GameState());

    @Override
    public void onLoopSolved(int position) {
        gameLogic.resolveQuantumLoop(lastMove, position);

        gameActivityInterface.updateGame(gameLogic.getActualGameState());

        Winner winner = gameLogic.whoWins();
        if (winner.equals(Winner.DRAW)) {
            gameActivityInterface.onDraw();
        } else if (winner.equals(Winner.NOBODY)) {
            gameActivityInterface.yourTurn(moveCounter);
        } else if (winner.equals(Winner.X_WINS)) {
            gameActivityInterface.onWin(true, true);
        } else if (winner.equals(Winner.O_WINS)) {
            gameActivityInterface.onWin(true, false);
        }
    }

    @Override
    public void onMove(Move move) {
        if (moveCounter % 2 == 0) { // O
            gameLogic.getActualGameState().getMovesO().add(move);
        } else { // X
            gameLogic.getActualGameState().getMovesX().add(move);
        }
        if (moveCounter > 0) { // it's not the first move, check for quantum loop
            Log.i("Controller", "isQuantum: " + gameLogic.isQuantumLoop(move.getCell1(), move, move.getNumber()));
            if (gameLogic.isQuantumLoop(move.getCell1(), move, move.getNumber())) {
                lastMove = move;
                moveCounter++;
                gameActivityInterface.solveLoop(move);
                return;
            }
        }
        moveCounter++;
        gameActivityInterface.yourTurn(moveCounter);
    }
}
