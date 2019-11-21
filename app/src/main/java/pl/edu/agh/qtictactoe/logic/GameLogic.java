package pl.edu.agh.qtictactoe.logic;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Data
public class GameLogic {

    private GameState actualGameState;
    private Set<Move> checkedMoves = new HashSet<>();

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

    private void printList(List<Integer> list, String x) {
        System.out.println(x + " List:");
        list.forEach(e -> System.out.print(e + ":"));
        System.out.println();
    }
}
