package pl.edu.agh.qtictactoe;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.agh.qtictactoe.adapter.GameAdapter;
import pl.edu.agh.qtictactoe.callback.SquareClickInterface;
import pl.edu.agh.qtictactoe.logic.GameLogic;
import pl.edu.agh.qtictactoe.model.GameSquare;
import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;
import pl.edu.agh.qtictactoe.model.Winner;

public class GameActivity extends AppCompatActivity implements SquareClickInterface {
    private GameAdapter gameAdapter;
    private List<GameSquare> gameSquares;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int moveCounter;
    private int movesLeft;
    private ArrayList<Integer> selectedCells = new ArrayList<>();
    private GameState gameState;
    private GameLogic gameLogic;
    private boolean isSolvingLoop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);
        init();
    }

    @Override
    public void onSquareClicked(int position) {
        Log.i("GameActivity", "onSquareClicked: " + position);
        selectedSquare(position);
    }

    private void init() {
        initUI();
        initGame();
    }

    private void initUI() {
        gameSquares = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            GameSquare gameSquare = new GameSquare();
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                list.add(0);
            }
            gameSquare.setDataset(list);
            gameSquares.add(gameSquare);
        }
        gameAdapter = new GameAdapter(gameSquares, this);
        recyclerView.setAdapter(gameAdapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initGame() {
        gameState = new GameState();
        gameLogic = new GameLogic(gameState);
        moveCounter = 1;
        movesLeft = 2;
        isSolvingLoop = false;
    }

    private void selectedSquare(int position) {
        if (moveCounter == 9) // crashes when moveCounter == 9 (win/draw condition will be met until then, so just a sanity check)
            return;
        int cellNumber = position + 1;
        if (isSolvingLoop) {
            // TODO: solving loop
            isSolvingLoop = false;
        } else {
            if (selectedCells.contains(cellNumber)) // already selected by player this round
                return;
            selectedCells.add(cellNumber);
            List<Integer> dataSet = gameSquares.get(position).getDataset();
            dataSet.set(moveCounter - 1, moveCounter);
            gameAdapter.notifyItemChanged(position);
            movesLeft--;
            if (movesLeft == 0) {
                Move move = new Move(moveCounter, selectedCells.get(0), selectedCells.get(1));
                if (moveCounter % 2 == 0) { // O
                    gameState.getMovesO().add(move);
                } else { // X
                    gameState.getMovesX().add(move);
                }
                if (moveCounter > 1) { // it's not the first move, check for quantum loop
                    Log.i("GameActivity", "isQuantum: " + gameLogic.isQuantumLoop(move.getCell1(), move, move.getNumber()));
                    if (gameLogic.isQuantumLoop(move.getCell1(), move, move.getNumber())) {
                        isSolvingLoop = true;
                    }
                }
                roundFinished();
            }
        }
    }

    private void roundFinished() {
        if (gameLogic.whoWins() != Winner.NOBODY) {
            Log.i("GameActivity", "Game finished, winner: " + gameLogic.whoWins().name());
            // TODO: win
        } else
            nextRound();
    }

    private void nextRound() {
        selectedCells.clear();
        movesLeft = 2;
        moveCounter++;
        // TODO: display some info O/X's turn?
    }


}
