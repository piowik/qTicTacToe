package pl.edu.agh.qtictactoe;

import android.util.Log;

import pl.edu.agh.qtictactoe.logic.GameLogic;
import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;

public class SingleplayerController extends BaseController {
    private int moveCounter;
    private Move lastMove;
    GameLogic gameLogic;
    GameState gameState;

    @Override
    public void onLoopSolved(int position) {
        gameLogic.resolveQuantumLoop(lastMove, position);
        gameActivityInterface.updateGame();
        gameActivityInterface.yourTurn(moveCounter);
    }

    @Override
    public void onMove(Move move) {
        if (moveCounter % 2 == 0) { // O
            gameState.getMovesO().add(move);
        } else { // X
            gameState.getMovesX().add(move);
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
