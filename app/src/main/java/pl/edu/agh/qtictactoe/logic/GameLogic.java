package pl.edu.agh.qtictactoe.logic;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;

import static java.util.Objects.isNull;

@Data
public class GameLogic {

    private GameState actualGameState;
    private Set<Move> checkedMoves;

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
}
