package pl.edu.agh.qtictactoe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.agh.qtictactoe.adapter.GameAdapter;
import pl.edu.agh.qtictactoe.callback.SquareClickInterface;
import pl.edu.agh.qtictactoe.model.GameSquare;
import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;
import pl.edu.agh.qtictactoe.model.UnderlinedInteger;

import static pl.edu.agh.qtictactoe.model.GameSquare.SELECTED_0;
import static pl.edu.agh.qtictactoe.model.GameSquare.SELECTED_NONE;
import static pl.edu.agh.qtictactoe.model.GameSquare.SELECTED_X;
import static pl.edu.agh.qtictactoe.model.UnderlinedInteger.EMPTY;

public class GameActivity extends AppCompatActivity implements SquareClickInterface, GameActivityInterface {
    private GameAdapter gameAdapter;
    private List<GameSquare> gameSquares;
    private BaseController controller = null;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int moveCounter;
    private int movesLeft;
    private ArrayList<Integer> selectedCells = new ArrayList<>();
    private boolean isSolvingLoop;
    private Move loopMove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);
        init();

        if (getIntent().getExtras() != null) {
            String hostIp = getIntent().getExtras().getString("ip", null);
            if (hostIp != null)
                controller = new MultiplayerController(hostIp);
        }
        if (controller == null)
            controller = new SingleplayerController();

        startGame();
        controller.attach(this);
    }

    @Override
    public void onSquareClicked(int position) {
        Log.i("GameActivity", "onSquareClicked: " + position);
        if (gameSquares.get(position).getSelection() != SELECTED_NONE)
            return;
        selectedSquare(position);
    }

    private void init() {
        initUI();
        initGame();
    }

    @Override
    public void startGame() {
        findViewById(R.id.waitingText).setVisibility(View.INVISIBLE);
        //TODO setClickable
    }

    private void initUI() {
        gameSquares = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            GameSquare gameSquare = new GameSquare();
            List<UnderlinedInteger> list = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                list.add(new UnderlinedInteger(EMPTY));
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
        movesLeft = 2;
        isSolvingLoop = false;
    }

    private void selectedSquare(int position) {
        if (moveCounter == 10) // crashes when moveCounter == 10 (win/draw condition will be met until then, so just a sanity check)
            return;
        if (isSolvingLoop) {
            if (position != loopMove.getCell1() && position != loopMove.getCell2()) {
                Toast.makeText(this, "Invalid loop solution", Toast.LENGTH_SHORT).show();
                return;
            }
            controller.onLoopSolved(position);
        } else {
            if (selectedCells.contains(position)) // already selected by player this round
                return;
            selectedCells.add(position);
            List<UnderlinedInteger> dataSet = gameSquares.get(position).getDataset();
            dataSet.set(moveCounter, new UnderlinedInteger(moveCounter, false, false));
            gameAdapter.notifyItemChanged(position);
            movesLeft--;
            if (movesLeft == 0) {
                Move move = new Move(moveCounter, selectedCells.get(0), selectedCells.get(1));
                recyclerView.setEnabled(false);
                recyclerView.setClickable(false);
                controller.onMove(move);
            }
        }
    }


    @Override
    public void updateGame(GameState gameState) {
        List<Integer> xList = gameState.getSelectedX();
        List<Integer> oList = gameState.getSelectedO();
        for (Integer cell : xList) {
            gameSquares.get(cell).setSelection(SELECTED_X);
            gameAdapter.notifyItemChanged(cell);
        }
        for (Integer cell : oList) {
            gameSquares.get(cell).setSelection(SELECTED_0);
            gameAdapter.notifyItemChanged(cell);
        }

    }

    @Override
    public void yourTurn(int turnNumber) {
        moveCounter = turnNumber;
        selectedCells.clear();
        movesLeft = 2;
        recyclerView.setEnabled(true);
        recyclerView.setClickable(true);
        isSolvingLoop = false;
        // TODO: display some info O/X's turn?
    }

    @Override
    public void solveLoop(Move move) {
        moveCounter = move.getNumber();
        GameSquare gameSquare1 = gameSquares.get(move.getCell1());
        GameSquare gameSquare2 = gameSquares.get(move.getCell2());
        gameSquare1.getDataset().get(moveCounter).setUnderlined(true);
        gameSquare2.getDataset().get(moveCounter).setUnderlined(true);
        gameAdapter.notifyItemChanged(move.getCell1());
        gameAdapter.notifyItemChanged(move.getCell2());
        loopMove = move;
        isSolvingLoop = true;
    }

    @Override
    public void onWin(boolean hasWon, boolean isXWin) {
        if (hasWon) {
            showWinner(isXWin);
        }
//        Toast.makeText(this, hasWon ? "Won" : "Lost", Toast.LENGTH_SHORT).show();
//        finish();
    }

    @Override
    public void onMove(Move move) {
//        Toast.makeText(this, "Moved", Toast.LENGTH_SHORT).show();
        //TODO
    }

    @Override
    public void onDraw() {
        TextView textView = findViewById(R.id.winner);
        textView.setText("DRAW");
        textView.setVisibility(View.VISIBLE);
//        finish();
    }

    @Override
    public void onResolvedConflict(int[] x, int[] y) {
//        Toast.makeText(this, "Resolved", Toast.LENGTH_SHORT).show();
    }

    private void showWinner(boolean isXWin) {
        TextView textView = findViewById(R.id.winner);
        String winnerText = isXWin ? "X WIN GAME" : "O WIN GAME";
        textView.setText(winnerText);
        textView.setVisibility(View.VISIBLE);
    }

}
