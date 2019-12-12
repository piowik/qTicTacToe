package pl.edu.agh.qtictactoe.logic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;
import pl.edu.agh.qtictactoe.model.Winner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
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

    @Test
    public void testResolveQuantumLoop() {
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
        Move lastMove = new Move(8, 4, 9);
        logic.resolveQuantumLoop(lastMove, 4);

        List<Integer> expectedSelectedXList = Arrays.asList(2, 3, 6);
        List<Integer> expectedSelectedOList = Arrays.asList(1, 4, 7, 9);
        List<Integer> actualSelectedXList = gameState.getSelectedX();
        List<Integer> actualSelectedOList = gameState.getSelectedO();
        Collections.sort(actualSelectedXList);
        Collections.sort(actualSelectedOList);

        assertThat(actualSelectedXList, is(expectedSelectedXList));
        assertThat(actualSelectedOList, is(expectedSelectedOList));

        Move expectedRemainigMove = new Move(5, 5, 8);
        Move actualRemainigMove = gameState.getMovesX().get(0);

        assertEquals(expectedRemainigMove.getNumber(), actualRemainigMove.getNumber());
        assertEquals(expectedRemainigMove.getCell1(), actualRemainigMove.getCell1());
        assertEquals(expectedRemainigMove.getCell2(), actualRemainigMove.getCell2());
    }

    private GameLogic setUpGameLogicForCheckingWinner(List<Integer> x, List<Integer> o) {
        GameState gameState = new GameState();
        gameState.setSelectedX(x);
        gameState.setSelectedO(o);
        return new GameLogic(gameState);
    }

    @Test
    public void testWhoWinsDraw() {
        List<Integer> xList = Arrays.asList(1, 2, 3);
        List<Integer> oList = Arrays.asList(4, 5, 6);
        GameLogic logic = setUpGameLogicForCheckingWinner(xList, oList);
        assertEquals("Draw", Winner.DRAW, logic.whoWins());
    }

    @Test
    public void testWhoWinsNobody() {
        List<Integer> xList = Arrays.asList(1, 2);
        List<Integer> oList = Arrays.asList(3, 4);
        GameLogic logic = setUpGameLogicForCheckingWinner(xList, oList);
        assertEquals("Nobody", Winner.NOBODY, logic.whoWins());
    }

    @Test
    public void testWhoWinsHorizontally() {
        List<Integer> xList = Arrays.asList(1, 2, 3);
        List<Integer> oList = Arrays.asList(3, 4);
        GameLogic logic = setUpGameLogicForCheckingWinner(xList, oList);
        assertEquals("X wins", Winner.X_WINS, logic.whoWins());
    }

    @Test
    public void testWhoWinsVertically() {
        List<Integer> xList = Arrays.asList(2, 5, 8);
        List<Integer> oList = Arrays.asList(3, 4, 9);
        GameLogic logic = setUpGameLogicForCheckingWinner(xList, oList);
        assertEquals("X wins", Winner.X_WINS, logic.whoWins());
    }

    @Test
    public void testWhoWinsCross1() {
        List<Integer> xList = Arrays.asList(1, 5, 9, 8);
        List<Integer> oList = Arrays.asList(3, 4, 2);
        GameLogic logic = setUpGameLogicForCheckingWinner(xList, oList);
        assertEquals("X wins", Winner.X_WINS, logic.whoWins());
    }

    @Test
    public void testWhoWinsCross2() {
        List<Integer> xList = Arrays.asList(3, 1, 5, 9);
        List<Integer> oList = Arrays.asList(3, 4, 7);
        GameLogic logic = setUpGameLogicForCheckingWinner(xList, oList);
        assertEquals("X wins", Winner.X_WINS, logic.whoWins());
    }

}
