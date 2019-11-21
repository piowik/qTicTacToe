package pl.edu.agh.qtictactoe.logic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GameLogicTest {

    @Test
    public void testIsQuantumLoopAssertTrue1() {
        GameState gameState = new GameState();
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

        GameLogic logic = new GameLogic(gameState);
        Move lastMove = new Move(7, 4, 9);
        boolean isQuantumLoop = logic.isQuantumLoop(3, lastMove, 7);
        assertTrue("WHAT TO WRITE HERE?", isQuantumLoop);
    }

    @Test
    public void testIsQuantumLoopAssertTrue2() {
        GameState gameState = new GameState();
        List<Move> movesX = new ArrayList<>(4);
        movesX.add(new Move(1, 1, 2));

        gameState.setMovesX(movesX);

        List<Move> movesO = new ArrayList<>(4);
        movesO.add(new Move(2, 1, 2));


        gameState.setMovesO(movesO);

        GameLogic logic = new GameLogic(gameState);
        Move lastMove = new Move(2, 1, 2);
        boolean isQuantumLoop = logic.isQuantumLoop(1, lastMove, 2);
        assertTrue("WHAT TO WRITE HERE?", isQuantumLoop);
    }

    @Test
    public void testIsQuantumLoopAssertFalse() {
        GameState gameState = new GameState();
        List<Move> movesX = new ArrayList<>(1);
        movesX.add(new Move(1, 1, 2));
        gameState.setMovesX(movesX);
        List<Move> movesO = new ArrayList<>(0);
        gameState.setMovesO(movesO);

        GameLogic logic = new GameLogic(gameState);
        Move lastMove = new Move(1, 1, 2);
        boolean isQuantumLoop = logic.isQuantumLoop(1, lastMove, 2);
        assertFalse("WHAT TO WRITE HERE?", isQuantumLoop);
    }
}
