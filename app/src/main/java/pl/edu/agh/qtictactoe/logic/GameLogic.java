package pl.edu.agh.qtictactoe.logic;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;
import pl.edu.agh.qtictactoe.model.Winner;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Data
public class GameLogic {

    private GameState actualGameState;
    private Set<Move> checkedMoves = new HashSet<>();

    private static final List<List<Integer>> WINNING_COMBINATIONS = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6),
            Arrays.asList(7, 8, 9),
            Arrays.asList(1, 4, 7),
            Arrays.asList(2, 5, 8),
            Arrays.asList(3, 6, 9),
            Arrays.asList(1, 5, 9),
            Arrays.asList(3, 5, 7)
    );

    GameLogic(GameState gameState) {
        actualGameState = gameState;
    }

    public boolean isQuantumLoop(int cellIndex, Move actualMove, int lastMove) {
        List<Move> moves = getMovesFromCell(cellIndex, actualMove);
        if (listContainsMove(moves, lastMove)) {
            return true;
        }

        List<Pair<Move, Integer>> cellsToCheck = getLinkedCells(moves, cellIndex);

        if (isNull(cellsToCheck) || cellsToCheck.isEmpty()) {
            return false;
        } else {
            for (Pair<Move, Integer> cellToCheck : cellsToCheck) {
                if (isQuantumLoop(cellToCheck.second, cellToCheck.first, lastMove)) {
                    return true;
                }
            }
        }

        return false;
    }

    private List<Pair<Move, Integer>> getLinkedCells(List<Move> moves, int index) {
        List<Pair<Move, Integer>> linkedCells = new ArrayList<>();

        for (Move move : moves) {
            Integer linkedCell = getLinkedCellFromPair(move, index);
            linkedCells.add(new Pair<>(move, linkedCell));
        }

        return linkedCells;
    }

    private boolean listContainsMove(List<Move> moves, int moveNumber) {
        return moves.stream().anyMatch(e -> e.getNumber() == moveNumber);
    }

    private Integer getLinkedCellFromPair(Move move, int index) {
        return move.getCell1() == index ? move.getCell2() : move.getCell1();
    }

    private List<Move> getMovesFromCell(int cellIndex, Move actualMove) {
        List<Move> moves = actualGameState.getMovesO().stream()
                .filter(e -> !e.equals(actualMove))
                .filter(e -> e.getCell1() == cellIndex || e.getCell2() == cellIndex)
                .collect(Collectors.toList());

        moves.addAll(actualGameState.getMovesX().stream()
                .filter(e -> !e.equals(actualMove))
                .filter(e -> e.getCell1() == cellIndex || e.getCell2() == cellIndex)
                .collect(Collectors.toList()));

        return moves;
    }

    public void resolveQuantumLoop(Move lastMove, int selectedCellIndex) {
        if (lastMove.getCell2() != selectedCellIndex && lastMove.getCell1() != selectedCellIndex) {
//            Log.e("Bad state", "There is no move in cell nr: " + selectedCellIndex);
            return;
        }
        if (lastMove.getNumber() % 2 == 0) {
            if (actualGameState.getSelectedO().contains(selectedCellIndex)) {
                return;
            }
            actualGameState.getSelectedO().add(selectedCellIndex);
            actualGameState.removeMove(lastMove, false);
        } else {
            if (actualGameState.getSelectedX().contains(selectedCellIndex)) {
                return;
            }
            actualGameState.getSelectedX().add(selectedCellIndex);
            actualGameState.removeMove(lastMove, true);
        }
        checkedMoves.add(lastMove);

        List<Move> moves = getMovesFromCell(selectedCellIndex, lastMove);
        List<Pair<Move, Integer>> cellsToCheck = getLinkedCells(moves, selectedCellIndex);
        if (nonNull(cellsToCheck) || !cellsToCheck.isEmpty()) {
            for (Pair<Move, Integer> cellToCheck : cellsToCheck) {
                resolveQuantumLoop(cellToCheck.first, cellToCheck.second);
            }
        }
    }

    public Winner whoWins() {
        Winner winner = Winner.NOBODY;
        boolean oWins = false;
        boolean xWins = false;
        if (actualGameState.getSelectedO().size() >= 3) {
            List<Integer> cells = actualGameState.getSelectedO();
            Collections.sort(cells);
            oWins = isWin(cells);
        }
        if (actualGameState.getSelectedX().size() >= 3) {
            List<Integer> cells = actualGameState.getSelectedX();
            Collections.sort(cells);
            xWins = isWin(cells);
        }
        if (xWins && oWins) {
            winner = Winner.DRAW;
        } else if (xWins) {
            winner = Winner.X_WINS;
        } else if (oWins) {
            winner = Winner.O_WINS;
        }

        return winner;
    }

    private boolean isWin(List<Integer> cells) {
        return WINNING_COMBINATIONS.stream().anyMatch(cells::containsAll);
    }
}
