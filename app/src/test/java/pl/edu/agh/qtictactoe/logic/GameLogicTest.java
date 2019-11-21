package pl.edu.agh.qtictactoe.logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;

import static org.junit.Assert.assertTrue;


public class GameLogicTest {

    GameState gameState = new GameState();

    @Before
    public void init() {
        List<Move> movesX = new ArrayList<>(4);
        movesX.add(new Move(1, 1, 2));
        movesX.add(new Move(3, 1, 3));
        movesX.add(new Move(5, 5, 8));
        movesX.add(new Move(7, 3, 6));
        gameState.setMovesX(movesX);

        List<Move> movesO = new ArrayList<>(4);
        movesO.add(new Move(2, 1, 4));
        movesO.add(new Move(4, 6, 9));
        movesO.add(new Move(6, 2, 7));
        movesO.add(new Move(8, 4, 9));
        gameState.setMovesO(movesO);
    }

    @Test
    public void testIsQuantumLoop() {
        GameLogic logic = new GameLogic(gameState);
        Move lastMove = new Move(7, 4, 9);
        boolean isQuantumLoop = logic.isQuantumLoop(3, lastMove, 7);
        assertTrue("WHAT TO WRITE HERE?", isQuantumLoop);
    }
}
